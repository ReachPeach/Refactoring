package ru.akirakozov.sd.refactoring.servlet.query.handler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaxQueryCommandHandler implements QueryCommandHandler {

    @Override
    public String getSqlResponse() {
        return "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
    }

    @Override
    public String getQueryResultTitle() {
        return "<h1>Product with max price: <h1>";
    }

    @Override
    public void handleOutput(ResultSet rs, HttpServletResponse response) throws IOException, SQLException {
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            response.getWriter().println(name + "\t" + price + "</br>");
        }
    }

}
