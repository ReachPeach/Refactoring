package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.database.DatabaseQueriesExecutor;
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

    private void executeCommand(QueryCommandHandler commandExecute, HttpServletResponse response) throws IOException {
        try (DatabaseQueriesExecutor sqlExecutor = new DatabaseQueriesExecutor(dataBaseUrl)) {
            ResultSet rs = sqlExecutor.executeQuery(commandExecute.getSqlResponse());
            response.getWriter().println("<html><body>");
            response.getWriter().println(commandExecute.getQueryResultTitle());

            commandExecute.handleOutput(rs, response);
            response.getWriter().println("</body></html>");

            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        switch (command) {
            case "max": {
                executeCommand(new MaxQueryCommandHandler(), response);
                break;
            }
            case "min": {
                executeCommand(new MinQueryCommandHandler(), response);
                break;
            }
            case "sum": {
                executeCommand(new SumQueryCommandHandler(), response);
                break;
            }
            case "count": {
                executeCommand(new CountQueryCommandHandler(), response);
                break;
            }
            default: {
                response.getWriter().println("Unknown command: " + command);
            }
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
