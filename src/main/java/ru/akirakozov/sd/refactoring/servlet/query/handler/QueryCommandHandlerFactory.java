package ru.akirakozov.sd.refactoring.servlet.query.handler;

public class QueryCommandHandlerFactory {
    public static QueryCommandHandler getQueryCommandHandlerByName(String name) {
        switch (name) {
            case "max": {
                return new MaxQueryCommandHandler();
            }
            case "min": {
                return new MinQueryCommandHandler();
            }
            case "sum": {
                return new SumQueryCommandHandler();
            }
            case "count": {
                return new CountQueryCommandHandler();
            }
            default: {
                return new UnknownQueryCommandHandler();
            }
        }
    }
}
