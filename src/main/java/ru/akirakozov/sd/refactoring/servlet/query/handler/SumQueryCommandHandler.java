package ru.akirakozov.sd.refactoring.servlet.query.handler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SumQueryCommandHandler implements QueryCommandHandler {
    @Override
    public String getSqlResponse() {
        return "SELECT SUM(price) FROM PRODUCT";
    }

    @Override
    public String getQueryResultTitle() {
        return "Summary price: ";
    }

    @Override
    public void handleOutput(ResultSet rs, HttpServletResponse response) throws IOException, SQLException {
        if (rs.next()) {
            response.getWriter().println(rs.getInt(1));
        }
    }
}
