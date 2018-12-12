import movie.service.MovieService;
import movie.service.TechnicalFailureException;
import movie.service.TitleNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import parental.control.ParentalControlService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

// @RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceTest {

    @InjectMocks
    private ParentalControlService SUT; // Source Under Test

    @Mock
    private MovieService movieService;

    private List<String> controlLevels = Arrays.asList("U", "PG", "12", "15", "18");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        SUT = new ParentalControlService(movieService, controlLevels);
    }

    @Test
    public void returnErrorMessageWhenMovieIdNotAvailable() throws Exception {
        // Given
        String expectedErrorMessage = "The movie service could not find the given movie";
        String movieId = "movieId1";
        String customerParentalControlLevel = "PG";

        when(movieService.getParentalControlLevel(movieId)).thenThrow(new TitleNotFoundException(expectedErrorMessage));

        // When
        final boolean actualResult = SUT.canWatchMovie(customerParentalControlLevel, movieId);
        String error = SUT.getErrorMessage();
        // Then
        assertThat(actualResult, is(false));
        SUT.setErrorMessage(expectedErrorMessage);
        String errMsg = error;
        Assert.assertEquals(errMsg, expectedErrorMessage);
      //  verify(error, times(1)).equalsIgnoreCase(expectedErrorMessage);
      //  final boolean b = verify(error).equalsIgnoreCase( eq(expectedErrorMessage) );
    }

    @Test
    public void returnErrorMessageWhenTechnicalFailure() throws Exception {
        // Given
        String expectedErrorMessage = "System error";
        String movieId = "movieId1";
        String customerParentalControlLevel = "PG";

        when(movieService.getParentalControlLevel(movieId))
                .thenThrow(new TechnicalFailureException(expectedErrorMessage));

        // When
        final boolean actualResult = SUT.canWatchMovie(customerParentalControlLevel, movieId);
        String error = SUT.getErrorMessage();
        // Then
        assertThat(actualResult, is(false));
        SUT.setErrorMessage(expectedErrorMessage);
        String errMsg = error;
        Assert.assertEquals(errMsg, expectedErrorMessage);

        /*verify(error, times(1)).equalsIgnoreCase(expectedErrorMessage);
        verify(error).equalsIgnoreCase(eq(expectedErrorMessage));*/
    }

    @Test
    public void canWatchWhenMovieLevelLessThanCustomerLevel() throws Exception {
        // Given
        String customerParentalControlLevel = "PG";
        String movieId = "movieId1";
    //    SUT = new ParentalControlService(movieService, controlLevels);

        when(movieService.getParentalControlLevel(movieId)).thenReturn("U");

        // When
        final boolean actualResult = SUT.canWatchMovie(customerParentalControlLevel, movieId);
        String error = SUT.getErrorMessage();
        // Then
        assertThat(actualResult, is(true));
        /*verifyZeroInteractions(error);
        verify(error, times(0)).equalsIgnoreCase(anyString());*/
    }

    @Test
    public void canWatchWhenMovieLevelEqualToCustomerLevel() throws Exception {
        // Given
        String customerParentalControlLevel = "12";
        String movieId = "movieId1";
    //    SUT = new ParentalControlService(movieService, controlLevels);

        when(movieService.getParentalControlLevel(movieId)).thenReturn("12");

        // When
        final boolean actualResult = SUT.canWatchMovie(customerParentalControlLevel, movieId);
        String error = SUT.getErrorMessage();

        // Then
        assertThat(actualResult, is(true));

       /* verifyZeroInteractions(error);
        verify(error, times(0)).equalsIgnoreCase(anyString());*/
    }

    @Test
    public void canNotWatchWhenMovieLevelHigherThanCustomerLevel() throws Exception {
        // Given
        String customerParentalControlLevel = "12";
        String movieId = "movieId1";
    //    SUT = new ParentalControlService(movieService, controlLevels);

        when(movieService.getParentalControlLevel(movieId)).thenReturn("15");

        // When
        final boolean actualResult = SUT.canWatchMovie(customerParentalControlLevel, movieId);
        String error = SUT.getErrorMessage();
        // Then
        assertThat(actualResult, is(false));

        /*verifyZeroInteractions(error);
        verify(error, times(0)).equalsIgnoreCase(anyString());*/
    }
}

