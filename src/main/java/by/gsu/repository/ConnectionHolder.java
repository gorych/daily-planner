package by.gsu.repository;

import lombok.extern.slf4j.Slf4j;

import java.nio.channels.NotYetConnectedException;
import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class ConnectionHolder {

    private static final String URL = "jdbc:sqlite:src\\main\\resources\\db\\daily-planner.db";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try (Connection newConnection = DriverManager.getConnection(URL)) {
            connection = newConnection;
            return connection;
        } catch (Exception e) {
            log.error("Error creating connection", e);
            throw new NotYetConnectedException();
        }
    }

}