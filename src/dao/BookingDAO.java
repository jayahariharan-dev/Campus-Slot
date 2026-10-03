package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.CancellationResult;

public class BookingDAO {

        // GET SMARTBOARDS FOR A SPECIFIC FLOOR

        public List<String> getSmartboardsByFloor(int floor) {

                List<String> smartboards = new ArrayList<>();

                String sql = """
                                SELECT resource_name
                                FROM resources
                                WHERE floor = ?
                                AND resource_type = ?
                                ORDER BY resource_id
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setInt(1, floor);

                        statement.setString(
                                        2,
                                        "Smartboard");

                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {

                                smartboards.add(
                                                resultSet.getString(
                                                                "resource_name"));
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Error loading smartboards!");

                        e.printStackTrace();
                }

                return smartboards;
        }

        // GET RESOURCE ID USING SMARTBOARD NAME + FLOOR

        public int getResourceIdByNameAndFloor(
                        String resourceName,
                        int floor) {

                String sql = """
                                SELECT resource_id
                                FROM resources
                                WHERE resource_name = ?
                                AND floor = ?
                                AND resource_type = ?
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setString(
                                        1,
                                        resourceName);

                        statement.setInt(
                                        2,
                                        floor);

                        statement.setString(
                                        3,
                                        "Smartboard");

                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {

                                return resultSet.getInt(
                                                "resource_id");
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Error finding smartboard!");

                        e.printStackTrace();
                }

                return -1;
        }

        // GET PERIOD ID

        public int getPeriodIdByName(
                        String periodName) {

                String sql = """
                                SELECT period_id
                                FROM periods
                                WHERE period_name = ?
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setString(
                                        1,
                                        periodName);

                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {

                                return resultSet.getInt(
                                                "period_id");
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Error finding period!");

                        e.printStackTrace();
                }

                return -1;
        }

        // CHECK WHETHER SLOT IS ALREADY BOOKED

        public boolean isSlotBooked(
                        int resourceId,
                        int periodId,
                        LocalDate bookingDate) {

                String sql = """
                                SELECT COUNT(*)
                                FROM bookings
                                WHERE resource_id = ?
                                AND period_id = ?
                                AND booking_date = ?
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setInt(
                                        1,
                                        resourceId);

                        statement.setInt(
                                        2,
                                        periodId);

                        statement.setObject(
                                        3,
                                        bookingDate);

                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {

                                return resultSet.getInt(1) > 0;
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Error checking booking!");

                        e.printStackTrace();
                }

                return false;
        }

        // CREATE BOOKING

        public boolean createBooking(
                        int resourceId,
                        int periodId,
                        String staffName,
                        LocalDate bookingDate,
                        String purpose) {

                String sql = """
                                INSERT INTO bookings
                                (
                                    resource_id,
                                    period_id,
                                    staff_name,
                                    booking_date,
                                    purpose
                                )
                                VALUES (?, ?, ?, ?, ?)
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setInt(
                                        1,
                                        resourceId);

                        statement.setInt(
                                        2,
                                        periodId);

                        statement.setString(
                                        3,
                                        staffName);

                        statement.setObject(
                                        4,
                                        bookingDate);

                        statement.setString(
                                        5,
                                        purpose);

                        int rowsInserted = statement.executeUpdate();

                        return rowsInserted > 0;

                } catch (Exception e) {

                        System.out.println(
                                        "Error creating booking!");

                        e.printStackTrace();

                        return false;
                }
        }

        // GET ALL BOOKINGS

        public List<Object[]> getAllBookings() {

                List<Object[]> bookings = new ArrayList<>();

                String sql = """
                                SELECT
                                    b.booking_id,
                                    r.floor,
                                    r.resource_name,
                                    b.booking_date,
                                    p.period_name,
                                    p.start_time,
                                    p.end_time,
                                    b.staff_name,
                                    b.purpose,
                                    b.status

                                FROM bookings b

                                JOIN resources r
                                    ON b.resource_id = r.resource_id

                                JOIN periods p
                                    ON b.period_id = p.period_id

                                ORDER BY
                                    b.booking_date,
                                    p.start_time
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql);

                                ResultSet resultSet = statement.executeQuery()) {

                        while (resultSet.next()) {

                                Object[] booking = {

                                                resultSet.getInt("booking_id"),

                                                resultSet.getInt("floor"),

                                                resultSet.getString("resource_name"),

                                                resultSet.getDate("booking_date"),

                                                resultSet.getString("period_name"),

                                                resultSet.getTime("start_time"),

                                                resultSet.getTime("end_time"),

                                                resultSet.getString("staff_name"),

                                                resultSet.getString("purpose"),

                                                resultSet.getString("status")
                                };

                                bookings.add(booking);
                        }

                } catch (Exception e) {

                        System.out.println("Error loading bookings!");
                        e.printStackTrace();
                }

                return bookings;
        }

        // CANCEL BOOKING AND RETURN CANCELLATION DETAILS

        public CancellationResult cancelBooking(int bookingId) {

                int resourceId = -1;
                int periodId = -1;
                LocalDate bookingDate = null;

                List<String> waitingStaff = new ArrayList<>();

                // GET BOOKING DETAILS BEFORE DELETING

                String getBookingSql = """
                                SELECT resource_id, period_id, booking_date
                                FROM bookings
                                WHERE booking_id = ?
                                """;

                String deleteBookingSql = """
                                DELETE FROM bookings
                                WHERE booking_id = ?
                                """;

                try (
                                Connection connection = DBConnection.getConnection()) {

                        // STEP 1: GET BOOKING DETAILS

                        try (
                                        PreparedStatement statement = connection.prepareStatement(
                                                        getBookingSql);

                                        ResultSet resultSet = getBookingResult(
                                                        statement,
                                                        bookingId)) {

                                if (resultSet.next()) {

                                        resourceId = resultSet.getInt(
                                                        "resource_id");

                                        periodId = resultSet.getInt(
                                                        "period_id");

                                        bookingDate = resultSet.getDate(
                                                        "booking_date").toLocalDate();

                                } else {

                                        return new CancellationResult(
                                                        false,
                                                        -1,
                                                        -1,
                                                        null,
                                                        waitingStaff);
                                }
                        }

                        // STEP 2: DELETE BOOKING

                        try (
                                        PreparedStatement statement = connection.prepareStatement(
                                                        deleteBookingSql)) {

                                statement.setInt(
                                                1,
                                                bookingId);

                                int rowsDeleted = statement.executeUpdate();

                                if (rowsDeleted == 0) {

                                        return new CancellationResult(
                                                        false,
                                                        resourceId,
                                                        periodId,
                                                        bookingDate,
                                                        waitingStaff);
                                }
                        }

                        // STEP 3: GET STAFF WAITING FOR THIS SLOT

                        waitingStaff = getWaitingStaff(
                                        resourceId,
                                        periodId,
                                        bookingDate);

                        // SUCCESS

                        return new CancellationResult(
                                        true,
                                        resourceId,
                                        periodId,
                                        bookingDate,
                                        waitingStaff);

                } catch (Exception e) {

                        System.out.println(
                                        "Error cancelling booking!");

                        e.printStackTrace();

                        return new CancellationResult(
                                        false,
                                        resourceId,
                                        periodId,
                                        bookingDate,
                                        waitingStaff);
                }
        }

        // HELPER METHOD FOR GETTING BOOKING RESULT

        private ResultSet getBookingResult(
                        PreparedStatement statement,
                        int bookingId) throws Exception {

                statement.setInt(
                                1,
                                bookingId);

                return statement.executeQuery();
        }

        // ADD STAFF TO WAITLIST

        public boolean addToWaitlist(
                        int resourceId,
                        int periodId,
                        String staffName,
                        LocalDate bookingDate) {

                String sql = """
                                INSERT INTO waitlist
                                (
                                    resource_id,
                                    period_id,
                                    staff_name,
                                    booking_date
                                )
                                VALUES (?, ?, ?, ?)
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setInt(
                                        1,
                                        resourceId);

                        statement.setInt(
                                        2,
                                        periodId);

                        statement.setString(
                                        3,
                                        staffName);

                        statement.setObject(
                                        4,
                                        bookingDate);

                        int rowsInserted = statement.executeUpdate();

                        return rowsInserted > 0;

                } catch (Exception e) {

                        System.out.println(
                                        "Error adding to waitlist!");

                        e.printStackTrace();

                        return false;
                }
        }

        // CHECK IF STAFF IS ALREADY IN WAITLIST

        public boolean isAlreadyInWaitlist(
                        int resourceId,
                        int periodId,
                        String staffName,
                        LocalDate bookingDate) {

                String sql = """
                                SELECT COUNT(*)
                                FROM waitlist
                                WHERE resource_id = ?
                                AND period_id = ?
                                AND staff_name = ?
                                AND booking_date = ?
                                AND status = 'Waiting'
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setInt(1, resourceId);
                        statement.setInt(2, periodId);
                        statement.setString(3, staffName);
                        statement.setObject(4, bookingDate);

                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {

                                return resultSet.getInt(1) > 0;
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Error checking waitlist!");

                        e.printStackTrace();
                }

                return false;
        }

        // GET STAFF WAITING FOR A SLOT

        public List<String> getWaitingStaff(
                        int resourceId,
                        int periodId,
                        LocalDate bookingDate) {

                List<String> waitingStaff = new ArrayList<>();

                String sql = """
                                SELECT staff_name
                                FROM waitlist
                                WHERE resource_id = ?
                                AND period_id = ?
                                AND booking_date = ?
                                AND status = 'Waiting'
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setInt(1, resourceId);
                        statement.setInt(2, periodId);
                        statement.setObject(3, bookingDate);

                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {

                                waitingStaff.add(
                                                resultSet.getString("staff_name"));
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Error loading waiting staff!");

                        e.printStackTrace();
                }

                return waitingStaff;
        }

        // REMOVE STAFF FROM WAITLIST AFTER NOTIFICATION

        public boolean removeFromWaitlist(
                        int resourceId,
                        int periodId,
                        LocalDate bookingDate) {

                String sql = """
                                DELETE FROM waitlist
                                WHERE resource_id = ?
                                AND period_id = ?
                                AND booking_date = ?
                                AND status = 'Waiting'
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setInt(1, resourceId);
                        statement.setInt(2, periodId);
                        statement.setObject(3, bookingDate);

                        int rowsDeleted = statement.executeUpdate();

                        return rowsDeleted > 0;

                } catch (Exception e) {

                        System.out.println(
                                        "Error removing staff from waitlist!");

                        e.printStackTrace();

                        return false;
                }
        }

        public boolean removeIndividualFromWaitlist(
                        int resourceId,
                        int periodId,
                        String staffName,
                        LocalDate bookingDate) {

                String sql = """
                                DELETE FROM waitlist
                                WHERE resource_id = ?
                                AND period_id = ?
                                AND LOWER(staff_name) = LOWER(?)
                                AND booking_date = ?
                                AND status = 'Waiting'
                                """;

                try (
                                Connection connection = DBConnection.getConnection();
                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setInt(1, resourceId);
                        statement.setInt(2, periodId);
                        statement.setString(3, staffName);
                        statement.setObject(4, bookingDate);

                        int rowsDeleted = statement.executeUpdate();

                        return rowsDeleted > 0;

                } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                }
        }

        // GET TODAY'S BOOKINGS FOR TELEGRAM NOTIFICATIONS

        public List<Object[]> getTodayBookings() {

                List<Object[]> bookings = new ArrayList<>();

                String sql = """
                                SELECT
                                    b.booking_id,
                                    r.floor,
                                    r.resource_name,
                                    b.booking_date,
                                    p.period_name,
                                    p.start_time,
                                    p.end_time,
                                    b.staff_name,
                                    b.purpose
                                FROM bookings b
                                JOIN resources r
                                    ON b.resource_id = r.resource_id
                                JOIN periods p
                                    ON b.period_id = p.period_id
                                WHERE b.booking_date = CURRENT_DATE
                                ORDER BY p.start_time
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql);

                                ResultSet resultSet = statement.executeQuery()) {

                        while (resultSet.next()) {

                                Object[] booking = {

                                                resultSet.getInt("booking_id"),

                                                resultSet.getInt("floor"),

                                                resultSet.getString("resource_name"),

                                                resultSet.getDate("booking_date"),

                                                resultSet.getString("period_name"),

                                                resultSet.getTime("start_time"),

                                                resultSet.getTime("end_time"),

                                                resultSet.getString("staff_name"),

                                                resultSet.getString("purpose")
                                };

                                bookings.add(booking);
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Error loading today's bookings!");

                        e.printStackTrace();
                }

                return bookings;
        }

        // CHECK WHETHER BOOKING STILL EXISTS

        public boolean isBookingStillActive(int bookingId) {

                String sql = """
                                SELECT COUNT(*)
                                FROM bookings
                                WHERE booking_id = ?
                                """;

                try (
                                Connection connection = DBConnection.getConnection();

                                PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setInt(1, bookingId);

                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {

                                return resultSet.getInt(1) > 0;
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Error checking booking status!");

                        e.printStackTrace();
                }

                return false;
        }
}