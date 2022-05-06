package wooteco.subway.di.exception;

public class NoSuchPeanutDefinitionException extends RuntimeException {

    public NoSuchPeanutDefinitionException(String name) {
        super("No Nut named " + name + " available ~ ! T.T");
    }
}

