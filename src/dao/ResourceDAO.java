package dao;

import database.DBConnection;
import model.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResourceDAO {

    public List<Resource> getAllResources() {

        List<Resource> resources = new ArrayList<>();

        String sql = "SELECT * FROM resources ORDER BY floor, resource_id";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Resource resource = new Resource(

                        resultSet.getInt("resource_id"),

                        resultSet.getString("resource_name"),

                        resultSet.getString("resource_type"),

                        resultSet.getInt("floor"),

                        resultSet.getString("status"));

                resources.add(resource);
            }

        } catch (Exception e) {

            System.out.println("Error loading resources!");
            e.printStackTrace();
        }

        return resources;
    }

    public boolean addResource(
            String name,
            String type,
            int floor,
            String status) {

        String sql = """
                INSERT INTO resources
                (resource_name, resource_type, floor, status)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, type);
            statement.setInt(3, floor);
            statement.setString(4, status);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (Exception e) {

            System.out.println("Error adding resource!");
            e.printStackTrace();

            return false;
        }
    }

    public boolean deleteResource(int resourceId) {

        String deleteWaitlist = """
                DELETE FROM waitlist
                WHERE resource_id = ?
                """;

        String deleteBookings = """
                DELETE FROM bookings
                WHERE resource_id = ?
                """;

        String deleteResource = """
                DELETE FROM resources
                WHERE resource_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection()) {

            // START TRANSACTION

            connection.setAutoCommit(false);

            try {

                // DELETE WAITLIST ENTRIES

                try (
                        PreparedStatement statement = connection.prepareStatement(
                                deleteWaitlist)) {

                    statement.setInt(1, resourceId);

                    statement.executeUpdate();
                }

                // DELETE BOOKING ENTRIES

                try (
                        PreparedStatement statement = connection.prepareStatement(
                                deleteBookings)) {

                    statement.setInt(1, resourceId);

                    statement.executeUpdate();
                }

                // DELETE SMARTBOARD

                try (
                        PreparedStatement statement = connection.prepareStatement(
                                deleteResource)) {

                    statement.setInt(1, resourceId);

                    int rowsDeleted = statement.executeUpdate();

                    connection.commit();

                    return rowsDeleted > 0;
                }

            } catch (Exception e) {

                // IF ANYTHING FAILS, UNDO EVERYTHING

                connection.rollback();

                System.out.println(
                        "Error deleting smartboard!");

                e.printStackTrace();

                return false;

            } finally {

                connection.setAutoCommit(true);
            }

        } catch (Exception e) {

            System.out.println(
                    "Database connection error!");

            e.printStackTrace();

            return false;
        }
    }
}