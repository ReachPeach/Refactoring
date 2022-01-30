package ru.akirakozov.sd.refactoring.servlet.query.handler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryCommandHandler {
    String getSqlResponse();

    String getQueryResultTitle();

    void handleOutput(ResultSet rs, HttpServletResponse response) throws IOException, SQLException;
}
