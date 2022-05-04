package wooteco.subway.di.exception;

public class NoSuchNutDefinitionException extends RuntimeException {

    private static final String MESSAGE = "";
    public NoSuchNutDefinitionException(String name) {
        super("No Nut named " + name + " available ~ ! T.T");
    }
}

