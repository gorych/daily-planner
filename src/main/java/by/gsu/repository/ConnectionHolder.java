package by.gsu.repository;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.NotYetConnectedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@UtilityClass
public class ConnectionHolder {

    private static final String URL = "jdbc:sqlite:src\\main\\resources\\db\\daily-planner.db";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            connection = DriverManager.getConnection(URL);
            return connection;
        } catch (SQLException e) {
            log.error("Error creating connection", e);
            throw new NotYetConnectedException();
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Error closing connection", e);
            }
        }
    }

}