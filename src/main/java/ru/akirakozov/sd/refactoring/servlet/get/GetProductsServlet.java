package ru.akirakozov.sd.refactoring.servlet.get;

import ru.akirakozov.sd.refactoring.database.DatabaseQueriesExecutor;
import ru.akirakozov.sd.refactoring.html.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private final String dataBaseUrl;

    public GetProductsServlet(String dataBaseUrl) {
        this.dataBaseUrl = dataBaseUrl;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder(response);

        try (DatabaseQueriesExecutor sqlExecutor = new DatabaseQueriesExecutor(dataBaseUrl)) {
            ResultSet rs = sqlExecutor.executeQuery("SELECT * FROM PRODUCT");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                responseBuilder.addLineBreak(name + "\t" + price);
            }

            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        responseBuilder.buildHtml();
    }
}
