"# SkyParentalControlsApp" 

Source code was implemented compiled, tested and debugged using Jetbrains IntelliJ IDE

For economy of space and time Kept the package structure relatively flat.

Main Service implemented is the ParentalControlService.

Uses The MovieService Interface and two custom Exceptions TechnicalFailureException, TitleNotFoundException,
as requested in the exercise text.

Calls 'matchCustomerControlLevel(customerParentalControlLevel, movieParentalControlLevel)' method to inspect the 
customers' parental control level againts the requested movies' and approve, reject or throw one of two Exceptions

I only had time to work on 5 main Tests:

returnErrorMessageWhenMovieIdNotAvailable()
returnErrorMessageWhenTechnicalFailure()
canWatchWhenMovieLevelLessThanCustomerLevel()
canWatchWhenMovieLevelEqualToCustomerLevel()
canNotWatchWhenMovieLevelHigherThanCustomerLevel()

