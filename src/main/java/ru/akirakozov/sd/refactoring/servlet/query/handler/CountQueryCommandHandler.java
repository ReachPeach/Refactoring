package ru.akirakozov.sd.refactoring.servlet.query.handler;

import javax.servlet.http.HttpServletResponse;
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
    public void handleOutput(ResultSet rs, HttpServletResponse response) throws IOException, SQLException {
        if (rs.next()) {
            response.getWriter().println(rs.getInt(1));
        }
    }
}
