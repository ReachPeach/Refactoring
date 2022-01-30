package ru.akirakozov.sd.refactoring.servlet.query.handler;

import ru.akirakozov.sd.refactoring.html.ResponseBuilder;

import java.sql.ResultSet;

public class UnknownQueryCommandHandler implements QueryCommandHandler {
    @Override
    public String getSqlResponse() {
        return null;
    }

    @Override
    public String getQueryResultTitle() {
        return null;
    }

    @Override
    public void handleOutput(ResultSet rs, ResponseBuilder responseBuilder) {

    }
}
