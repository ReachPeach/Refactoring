package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.database.DatabaseQueriesExecutor;
import ru.akirakozov.sd.refactoring.html.ResponseBuilder;
import ru.akirakozov.sd.refactoring.servlet.query.handler.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final String dataBaseUrl;

    public QueryServlet(final String dataBaseUrl) {
        this.dataBaseUrl = dataBaseUrl;
    }

    private void executeCommand(QueryCommandHandler commandExecute, ResponseBuilder responseBuilder) throws IOException {
        try (DatabaseQueriesExecutor sqlExecutor = new DatabaseQueriesExecutor(dataBaseUrl)) {
            ResultSet rs = sqlExecutor.executeQuery(commandExecute.getSqlResponse());
            responseBuilder.addLine(commandExecute.getQueryResultTitle());

            commandExecute.handleOutput(rs, responseBuilder);

            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        responseBuilder.buildHtml();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        ResponseBuilder responseBuilder = new ResponseBuilder(response);

        switch (command) {
            case "max": {
                executeCommand(new MaxQueryCommandHandler(), responseBuilder);
                break;
            }
            case "min": {
                executeCommand(new MinQueryCommandHandler(), responseBuilder);
                break;
            }
            case "sum": {
                executeCommand(new SumQueryCommandHandler(), responseBuilder);
                break;
            }
            case "count": {
                executeCommand(new CountQueryCommandHandler(), responseBuilder);
                break;
            }
            default: {
                responseBuilder.addLine("Unknown command: " + command);
                responseBuilder.buildText();
            }
        }
    }
}
