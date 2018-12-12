package movie.service;

public class TechnicalFailureException extends Exception {

    public TechnicalFailureException(String message) {
        super(message);
    }
}

