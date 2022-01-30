package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.database.DatabaseQueriesExecutor;
import ru.akirakozov.sd.refactoring.html.ResponseBuilder;
import ru.akirakozov.sd.refactoring.servlet.query.handler.QueryCommandHandler;
import ru.akirakozov.sd.refactoring.servlet.query.handler.QueryCommandHandlerFactory;
import ru.akirakozov.sd.refactoring.servlet.query.handler.UnknownQueryCommandHandler;

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

    private void executeCommand(QueryCommandHandler commandHandler, ResponseBuilder responseBuilder) throws IOException {
        try (DatabaseQueriesExecutor sqlExecutor = new DatabaseQueriesExecutor(dataBaseUrl)) {
            ResultSet rs = sqlExecutor.executeQuery(commandHandler.getSqlResponse());
            responseBuilder.addLine(commandHandler.getQueryResultTitle());

            commandHandler.handleOutput(rs, responseBuilder);

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

        QueryCommandHandler commandHandler = QueryCommandHandlerFactory.getQueryCommandHandlerByName(command);

        if (commandHandler instanceof UnknownQueryCommandHandler) {
            responseBuilder.addLine("Unknown command: " + command);
            responseBuilder.buildText();
        } else {
            executeCommand(commandHandler, responseBuilder);
        }
    }
}
