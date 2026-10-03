package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TelegramUserDAO {

    // Save or update a staff member's Telegram Chat ID
    public boolean saveTelegramUser(String staffName, long chatId) {

        String sql = """
                INSERT INTO telegram_users (staff_name, chat_id)
                VALUES (?, ?)
                ON CONFLICT (staff_name)
                DO UPDATE SET chat_id = EXCLUDED.chat_id
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, staffName);
            statement.setLong(2, chatId);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Get Telegram Chat ID using Staff Name
    public Long getChatIdByStaffName(String staffName) {

        String sql = """
                SELECT chat_id
                FROM telegram_users
                WHERE LOWER(TRIM(staff_name)) = LOWER(TRIM(?))
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, staffName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("chat_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // Delete Telegram registration if needed
    public boolean deleteTelegramUser(String staffName) {

        String sql = """
                DELETE FROM telegram_users
                WHERE LOWER(TRIM(staff_name)) = LOWER(TRIM(?))
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, staffName);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}