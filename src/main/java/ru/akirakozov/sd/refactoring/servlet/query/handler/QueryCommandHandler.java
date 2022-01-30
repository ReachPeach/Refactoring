package ru.akirakozov.sd.refactoring.servlet.query.handler;

import ru.akirakozov.sd.refactoring.html.ResponseBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryCommandHandler {
    String getSqlResponse();

    String getQueryResultTitle();

    void handleOutput(ResultSet rs, ResponseBuilder responseBuilder) throws SQLException;
}
