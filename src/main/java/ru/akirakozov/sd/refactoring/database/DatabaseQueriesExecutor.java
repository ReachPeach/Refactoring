package ru.akirakozov.sd.refactoring.database;

import java.sql.*;

public class DatabaseQueriesExecutor implements AutoCloseable {
    private final Statement statement;
    private final Connection connection;

    public DatabaseQueriesExecutor(final String dataBaseUrl) throws SQLException {
        this.connection = DriverManager.getConnection(dataBaseUrl);
        this.statement = connection.createStatement();
    }

    public void executeUpdate(final String update) throws SQLException {
        statement.executeUpdate(update);
    }

    public ResultSet executeQuery(final String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }
}