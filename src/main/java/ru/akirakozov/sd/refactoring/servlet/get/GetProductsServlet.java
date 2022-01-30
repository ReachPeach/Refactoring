package ru.akirakozov.sd.refactoring.servlet.get;

import ru.akirakozov.sd.refactoring.html.ResponseBuilder;
import ru.akirakozov.sd.refactoring.servlet.util.ServletSqlExecutor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.akirakozov.sd.refactoring.servlet.util.ServletSqlExecutor.executeQuery;

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

        final String sql = "SELECT * FROM PRODUCT";

        ServletSqlExecutor.OnExecuteAction action = rs -> {
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                responseBuilder.addLineBreak(name + "\t" + price);
            }
            responseBuilder.buildHtml();
        };

        executeQuery(dataBaseUrl, sql, action);

    }
}
