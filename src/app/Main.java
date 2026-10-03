package app;

import javax.swing.SwingUtilities;

import telegram.BookingNotificationScheduler;
import ui.BookingForm;
import ui.Dashboard;

public class Main {

    public static void main(String[] args) {

        BookingNotificationScheduler
                notificationScheduler =
                        new BookingNotificationScheduler();

        notificationScheduler.start();

        BookingForm.setNotificationScheduler(
                notificationScheduler
        );

        SwingUtilities.invokeLater(() -> {
            new Dashboard();
        });
    }
}