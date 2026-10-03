package telegram;

import dao.TelegramUserDAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelegramRegistrationService {

    private static final String BOT_TOKEN =
            System.getenv("CAMPUSSLOT_TELEGRAM_BOT_TOKEN");

    private final TelegramUserDAO telegramUserDAO =
            new TelegramUserDAO();

    private final TelegramService telegramService =
            new TelegramService();

    // Stores chat IDs that have sent /start
    // and are waiting to enter their staff name
    private final Set<Long> waitingForStaffName =
            ConcurrentHashMap.newKeySet();

    private volatile boolean running = false;

    private Thread telegramThread;

    private volatile HttpURLConnection currentConnection;

    private long lastUpdateId = 0;


    // =========================================================
    // START LISTENER
    // =========================================================

    public synchronized void start() {

        if (running) {
            System.out.println(
                    "Telegram registration listener is already running."
            );
            return;
        }

        if (BOT_TOKEN == null || BOT_TOKEN.isBlank()) {

            System.out.println(
                    "Telegram registration listener not started. "
                            + "Bot token is not configured."
            );

            return;
        }

        running = true;

        telegramThread = new Thread(() -> {

            System.out.println(
                    "Telegram registration listener started..."
            );

            while (running) {

                try {

                    getUpdates();

                } catch (Exception e) {

                    if (!running) {
                        break;
                    }

                    System.out.println(
                            "Error while checking Telegram updates:"
                    );

                    e.printStackTrace();

                    try {

                        Thread.sleep(3000);

                    } catch (InterruptedException interruptedException) {

                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

            System.out.println(
                    "Telegram registration listener stopped."
            );

        });

        telegramThread.setName(
                "CampusSlot-Telegram-Registration"
        );

        telegramThread.setDaemon(true);

        telegramThread.start();
    }


    // =========================================================
    // STOP LISTENER
    // =========================================================

    public synchronized void stop() {

        if (!running) {
            return;
        }

        System.out.println(
                "Stopping Telegram registration listener..."
        );

        running = false;

        // Interrupt the thread
        if (telegramThread != null) {
            telegramThread.interrupt();
        }

        // Disconnect an active long-polling connection
        if (currentConnection != null) {
            currentConnection.disconnect();
        }

        waitingForStaffName.clear();
    }


    // =========================================================
    // CHECK STATUS
    // =========================================================

    public boolean isRunning() {
        return running;
    }


    // =========================================================
    // GET TELEGRAM UPDATES
    // =========================================================

    private void getUpdates() throws Exception {

        if (!running) {
            return;
        }

        URI uri = URI.create(
                "https://api.telegram.org/bot"
                        + BOT_TOKEN
                        + "/getUpdates?offset="
                        + lastUpdateId
                        + "&timeout=20"
        );

        HttpURLConnection connection =
                (HttpURLConnection) uri.toURL().openConnection();

        currentConnection = connection;

        connection.setRequestMethod("GET");

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        connection.getInputStream()
                                )
                        )
        ) {

            StringBuilder response =
                    new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {

                response.append(line);
            }

            if (running) {
                processUpdates(response.toString());
            }

        } finally {

            connection.disconnect();

            currentConnection = null;
        }
    }


    // =========================================================
    // PROCESS TELEGRAM UPDATES
    // =========================================================

    private void processUpdates(String json) {

        Pattern pattern = Pattern.compile(
                "\"update_id\"\\s*:\\s*(\\d+)"
                        + ".*?"
                        + "\"chat\"\\s*:\\s*\\{"
                        + ".*?"
                        + "\"id\"\\s*:\\s*(-?\\d+)"
                        + ".*?"
                        + "\"text\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"",
                Pattern.DOTALL
        );

        Matcher matcher =
                pattern.matcher(json);

        while (matcher.find()) {

            if (!running) {
                return;
            }

            long updateId =
                    Long.parseLong(matcher.group(1));

            long chatId =
                    Long.parseLong(matcher.group(2));

            String message =
                    matcher.group(3)
                            .replace("\\n", "\n")
                            .replace("\\\"", "\"")
                            .trim();

            // Move offset forward so the same update
            // is not processed again
            lastUpdateId =
                    updateId + 1;

            handleMessage(
                    chatId,
                    message
            );
        }
    }


    // =========================================================
    // HANDLE TELEGRAM MESSAGE
    // =========================================================

    private void handleMessage(
            long chatId,
            String message
    ) {

        if (!running) {
            return;
        }

        // Staff starts registration
        if (message.startsWith("/start")) {

            waitingForStaffName.add(chatId);

            telegramService.sendMessage(
                    chatId,
                    "Welcome to CampusSlot! 🎓\n\n"
                            + "Please enter your staff name."
            );

            return;
        }


        // Staff enters their name
        if (waitingForStaffName.contains(chatId)) {

            String staffName =
                    message.trim();

            if (staffName.isEmpty()) {

                telegramService.sendMessage(
                        chatId,
                        "Staff name cannot be empty. "
                                + "Please enter your staff name."
                );

                return;
            }


            boolean saved =
                    telegramUserDAO.saveTelegramUser(
                            staffName,
                            chatId
                    );


            if (saved) {

                waitingForStaffName.remove(chatId);

                telegramService.sendMessage(
                        chatId,
                        "Registration successful! ✅\n\n"
                                + "You will now receive CampusSlot "
                                + "availability notifications."
                );

                System.out.println(
                        "Telegram user registered: "
                                + staffName
                                + " | Chat ID: "
                                + chatId
                );

            } else {

                telegramService.sendMessage(
                        chatId,
                        "Registration failed. "
                                + "Please send your name again."
                );
            }
        }
    }
}