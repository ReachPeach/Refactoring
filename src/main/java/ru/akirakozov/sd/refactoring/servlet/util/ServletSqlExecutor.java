package ru.akirakozov.sd.refactoring.servlet.util;

import ru.akirakozov.sd.refactoring.database.DatabaseQueriesExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServletSqlExecutor {
    public static void executeQuery(final String url, final String query, final OnExecuteAction action) {
        try (DatabaseQueriesExecutor sqlExecutor = new DatabaseQueriesExecutor(url)) {
            ResultSet resultSet = sqlExecutor.executeQuery(query);
            action.onExecute(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeUpdate(final String url, final String update) {
        try (DatabaseQueriesExecutor databaseQueryExecutor = new DatabaseQueriesExecutor(url)) {
            databaseQueryExecutor.executeUpdate(update);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public interface OnExecuteAction {
        void onExecute(ResultSet argument) throws SQLException;
    }
}


