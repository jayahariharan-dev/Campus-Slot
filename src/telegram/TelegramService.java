package telegram;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TelegramService {

    private static final String BOT_TOKEN =
            System.getenv("CAMPUSSLOT_TELEGRAM_BOT_TOKEN");

    public boolean sendMessage(long chatId, String message) {

        if (BOT_TOKEN == null || BOT_TOKEN.isBlank()) {
            System.out.println("Telegram bot token is not configured.");
            return false;
        }

        try {

            URI uri = URI.create(
                    "https://api.telegram.org/bot"
                            + BOT_TOKEN
                            + "/sendMessage"
            );

            HttpURLConnection connection =
                    (HttpURLConnection) uri.toURL().openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8"
            );

            String parameters =
                    "chat_id=" + chatId
                            + "&text="
                            + URLEncoder.encode(
                                    message,
                                    StandardCharsets.UTF_8
                            );

            try (OutputStream outputStream =
                         connection.getOutputStream()) {

                byte[] input =
                        parameters.getBytes(StandardCharsets.UTF_8);

                outputStream.write(input);
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {

                System.out.println(
                        "Telegram message sent successfully to chat ID: "
                                + chatId
                );

                return true;

            } else {

                System.out.println(
                        "Telegram message failed. Response code: "
                                + responseCode
                );

                return false;
            }

        } catch (Exception e) {

            System.out.println(
                    "Error while sending Telegram message:"
            );

            e.printStackTrace();

            return false;
        }
    }
}