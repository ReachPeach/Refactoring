package ru.akirakozov.sd.refactoring.servlet.query.handler;

import ru.akirakozov.sd.refactoring.html.ResponseBuilder;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountQueryCommandHandler implements QueryCommandHandler {
    @Override
    public String getSqlResponse() {
        return "SELECT COUNT(*) FROM PRODUCT";
    }

    @Override
    public String getQueryResultTitle() {
        return "Number of products: ";
    }

    @Override
    public void handleOutput(ResultSet rs, ResponseBuilder responseBuilder) throws SQLException {
        if (rs.next()) {
            responseBuilder.addLine(String.valueOf(rs.getInt(1)));
        }
    }
}
