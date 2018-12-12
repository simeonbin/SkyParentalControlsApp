package parental.control;

import movie.service.MovieService;
import movie.service.TechnicalFailureException;
import movie.service.TitleNotFoundException;

import java.util.Comparator;
import java.util.List;

public class ParentalControlService {

    private final MovieService movieService;
    private final List<String> controlLevels;
    private String errorMessage;

    public ParentalControlService(MovieService movieService, List<String> controlLevels) {
        this.movieService = movieService;
        this.controlLevels = controlLevels;
    }

    public boolean canWatchMovie(String customerParentalControlLevel, String movieId) {
        try {
            final String movieParentalControlLevel = movieService.getParentalControlLevel(movieId);

            return matchCustomerControlLevel(customerParentalControlLevel, movieParentalControlLevel);
        } catch (TitleNotFoundException | TechnicalFailureException e) {
            setErrorMessage(e.getMessage());
            return false;
        }
    }

    private boolean matchCustomerControlLevel(String customerParentalControlLevel, String movieParentalControlLevel) {

       Comparator<String> controlLevelComparator = (controlLevel1, controlLevel2) -> controlLevels.indexOf(controlLevel1) -
                                                                                        controlLevels.indexOf(controlLevel2);
       boolean cmpMovieCustomerControls = ( controlLevelComparator.compare( movieParentalControlLevel,
                                                                            customerParentalControlLevel ) <= 0 );
        return cmpMovieCustomerControls;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
