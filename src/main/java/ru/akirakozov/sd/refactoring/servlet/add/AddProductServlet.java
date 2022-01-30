package ru.akirakozov.sd.refactoring.servlet.add;

import ru.akirakozov.sd.refactoring.database.DatabaseQueriesExecutor;
import ru.akirakozov.sd.refactoring.html.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {
    private final String dataBaseUrl;

    public AddProductServlet(final String dataBaseUrl) {
        this.dataBaseUrl = dataBaseUrl;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        ResponseBuilder responseBuilder = new ResponseBuilder(response);

        try (DatabaseQueriesExecutor sqlExecutor = new DatabaseQueriesExecutor(dataBaseUrl)) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
            sqlExecutor.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        responseBuilder.addLine("OK");
        responseBuilder.buildText();
    }
}
