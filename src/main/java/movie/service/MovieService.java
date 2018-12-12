package movie.service;

public interface MovieService {

    String getParentalControlLevel(String movieId) throws TitleNotFoundException, TechnicalFailureException;
}
