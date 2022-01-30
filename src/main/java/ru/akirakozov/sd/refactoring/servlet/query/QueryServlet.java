package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.html.ResponseBuilder;
import ru.akirakozov.sd.refactoring.servlet.query.handler.QueryCommandHandler;
import ru.akirakozov.sd.refactoring.servlet.query.handler.QueryCommandHandlerFactory;
import ru.akirakozov.sd.refactoring.servlet.query.handler.UnknownQueryCommandHandler;
import ru.akirakozov.sd.refactoring.servlet.util.ServletSqlExecutor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.akirakozov.sd.refactoring.servlet.util.ServletSqlExecutor.executeQuery;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final String dataBaseUrl;

    public QueryServlet(final String dataBaseUrl) {
        this.dataBaseUrl = dataBaseUrl;
    }

    private void executeCommand(QueryCommandHandler commandHandler, ResponseBuilder responseBuilder) {

        ServletSqlExecutor.OnExecuteAction action = rs -> {
            responseBuilder.addLine(commandHandler.getQueryResultTitle());
            commandHandler.handleOutput(rs, responseBuilder);
            responseBuilder.buildHtml();
        };

        executeQuery(dataBaseUrl, commandHandler.getSqlQuery(), action);
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
