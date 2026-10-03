package telegram;

import dao.BookingDAO;
import dao.TelegramUserDAO;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BookingNotificationScheduler {

        private final BookingDAO bookingDAO = new BookingDAO();

        private final TelegramUserDAO telegramUserDAO = new TelegramUserDAO();

        private final TelegramService telegramService = new TelegramService();

        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2, runnable -> {

                Thread thread = new Thread(runnable);

                thread.setName(
                                "CampusSlot-Booking-Notification");

                thread.setDaemon(true);

                return thread;
        });

        // START SCHEDULER

        public void start() {

                System.out.println(
                                "Booking notification scheduler started...");
        }

        // SCHEDULE A NEW BOOKING

        public void scheduleBooking(
                        int resourceId,
                        int periodId,
                        String staffName,
                        LocalDate bookingDate,
                        String purpose,
                        String smartboardName,
                        int floor,
                        String periodName) {

                // Only today's bookings need notification scheduling.

                if (!bookingDate.equals(LocalDate.now())) {

                        System.out.println(
                                        "Notification not scheduled because "
                                                        + "the booking is not for today.");

                        return;
                }

                LocalTime startTime = getPeriodStartTime(periodId);

                if (startTime == null) {

                        System.out.println(
                                        "Could not determine period start time "
                                                        + "for period ID: "
                                                        + periodId);

                        return;
                }

                LocalDateTime bookingStart = LocalDateTime.of(
                                bookingDate,
                                startTime);

                LocalDateTime reminderTime = bookingStart.minusMinutes(15);

                LocalDateTime now = LocalDateTime.now();

                System.out.println(
                                "New booking received by scheduler:"
                                                + " Staff=" + staffName
                                                + " | Smartboard=" + smartboardName
                                                + " | Floor=" + floor
                                                + " | Period=" + periodName
                                                + " | Start=" + bookingStart);

                // 15-MINUTE NOTIFICATION

                if (reminderTime.isAfter(now)) {

                        scheduleNotification(
                                        resourceId,
                                        periodId,
                                        staffName,
                                        bookingDate,
                                        purpose,
                                        smartboardName,
                                        floor,
                                        periodName,
                                        reminderTime,
                                        false);

                        System.out.println(
                                        "15-minute notification scheduled for: "
                                                        + reminderTime);

                } else {

                        System.out.println(
                                        "15-minute notification skipped "
                                                        + "because its time has passed.");
                }

                // EXACT-TIME NOTIFICATION

                if (bookingStart.isAfter(now)) {

                        scheduleNotification(
                                        resourceId,
                                        periodId,
                                        staffName,
                                        bookingDate,
                                        purpose,
                                        smartboardName,
                                        floor,
                                        periodName,
                                        bookingStart,
                                        true);

                        System.out.println(
                                        "Exact-time notification scheduled for: "
                                                        + bookingStart);

                } else {

                        System.out.println(
                                        "Exact-time notification skipped "
                                                        + "because booking time has passed.");
                }
        }

        // CREATE SCHEDULED TASK

        private void scheduleNotification(
                        int resourceId,
                        int periodId,
                        String staffName,
                        LocalDate bookingDate,
                        String purpose,
                        String smartboardName,
                        int floor,
                        String periodName,
                        LocalDateTime notificationTime,
                        boolean exactStart) {

                long delayMillis = Duration.between(
                                LocalDateTime.now(),
                                notificationTime).toMillis();

                if (delayMillis <= 0) {
                        return;
                }

                scheduler.schedule(
                                () -> sendNotification(
                                                resourceId,
                                                periodId,
                                                staffName,
                                                bookingDate,
                                                purpose,
                                                smartboardName,
                                                floor,
                                                periodName,
                                                exactStart),
                                delayMillis,
                                TimeUnit.MILLISECONDS);
        }

        // SEND NOTIFICATION

        private void sendNotification(
                        int resourceId,
                        int periodId,
                        String staffName,
                        LocalDate bookingDate,
                        String purpose,
                        String smartboardName,
                        int floor,
                        String periodName,
                        boolean exactStart) {

                /*
                 * FINAL VALIDATION
                 *
                 * Check whether the slot is still booked.
                 *
                 * If the booking was cancelled before the
                 * notification time, no notification is sent.
                 */

                boolean bookingStillExists = bookingDAO.isSlotBooked(
                                resourceId,
                                periodId,
                                bookingDate);

                if (!bookingStillExists) {

                        System.out.println(
                                        "Notification cancelled because "
                                                        + "the booking no longer exists.");

                        return;
                }

                // CLEAR WAITLIST WHEN THE BOOKING STARTS

                if (exactStart) {

                        boolean removed = bookingDAO.removeFromWaitlist(
                                        resourceId,
                                        periodId,
                                        bookingDate);

                        if (removed) {

                                System.out.println(
                                                "Waiting list cleared because "
                                                                + "the booking has started.");

                        } else {

                                System.out.println(
                                                "No waiting staff found for this slot.");
                        }
                }

                // FIND TELEGRAM CHAT ID

                Long chatId = telegramUserDAO.getChatIdByStaffName(
                                staffName);

                if (chatId == null) {

                        System.out.println(
                                        "No Telegram Chat ID found for: "
                                                        + staffName);

                        return;
                }

                String message;

                // EXACT-TIME MESSAGE

                if (exactStart) {

                        message = "🔔 CAMPUSSLOT REMINDER\n\n"
                                        + "Your smartboard booking starts NOW! 🖥️\n\n"
                                        + "👤 Staff: "
                                        + staffName
                                        + "\n"
                                        + "🏢 Floor: "
                                        + floor
                                        + "\n"
                                        + "🖥️ Smartboard: "
                                        + smartboardName
                                        + "\n"
                                        + "📅 Date: "
                                        + bookingDate
                                        + "\n"
                                        + "⏰ Period: "
                                        + periodName
                                        + "\n"
                                        + "📝 Purpose: "
                                        + purpose
                                        + "\n\n"
                                        + "Please proceed to your smartboard.";

                } else {

                        // 15-MINUTE MESSAGE

                        message = "⏰ CAMPUSSLOT REMINDER\n\n"
                                        + "Your smartboard booking starts "
                                        + "in 15 minutes! 🖥️\n\n"
                                        + "👤 Staff: "
                                        + staffName
                                        + "\n"
                                        + "🏢 Floor: "
                                        + floor
                                        + "\n"
                                        + "🖥️ Smartboard: "
                                        + smartboardName
                                        + "\n"
                                        + "📅 Date: "
                                        + bookingDate
                                        + "\n"
                                        + "⏰ Period: "
                                        + periodName
                                        + "\n"
                                        + "📝 Purpose: "
                                        + purpose;
                }

                // SEND TELEGRAM MESSAGE

                boolean sent = telegramService.sendMessage(
                                chatId,
                                message);

                if (sent) {

                        System.out.println(
                                        "Booking notification sent successfully "
                                                        + "to: "
                                                        + staffName);

                } else {

                        System.out.println(
                                        "Failed to send booking notification "
                                                        + "to: "
                                                        + staffName);
                }
        }

        // INSTANT TEST

        public void testBookingNotification(
                        int resourceId,
                        int periodId,
                        String staffName,
                        LocalDate bookingDate,
                        String purpose,
                        String smartboardName,
                        int floor,
                        String periodName) {

                System.out.println(
                                "===== INSTANT NOTIFICATION TEST STARTED =====");

                // TEST 1: simulate 15-minute reminder after 10 seconds

                scheduler.schedule(
                                () -> sendNotification(
                                                resourceId,
                                                periodId,
                                                staffName,
                                                bookingDate,
                                                purpose,
                                                smartboardName,
                                                floor,
                                                periodName,
                                                false),
                                10,
                                TimeUnit.SECONDS);

                System.out.println(
                                "Test 15-minute notification scheduled in 10 seconds.");

                // TEST 2: simulate exact start after 20 seconds

                scheduler.schedule(
                                () -> sendNotification(
                                                resourceId,
                                                periodId,
                                                staffName,
                                                bookingDate,
                                                purpose,
                                                smartboardName,
                                                floor,
                                                periodName,
                                                true),
                                20,
                                TimeUnit.SECONDS);

                System.out.println(
                                "Test exact-start notification scheduled in 20 seconds.");
        }

        // PERIOD START TIMES

        private LocalTime getPeriodStartTime(int periodId) {

                return switch (periodId) {

                        case 1 -> LocalTime.of(8, 50);
                        case 2 -> LocalTime.of(9, 40);
                        case 3 -> LocalTime.of(10, 45);
                        case 4 -> LocalTime.of(11, 35);
                        case 5 -> LocalTime.of(13, 10);
                        case 6 -> LocalTime.of(14, 0);
                        case 7 -> LocalTime.of(15, 0);
                        case 8 -> LocalTime.of(15, 50);

                        default -> null;
                };
        }

        // STOP

        public void stop() {

                scheduler.shutdownNow();

                System.out.println(
                                "Booking notification scheduler stopped.");
        }
}