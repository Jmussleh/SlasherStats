import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

//Main class that handles CRUD operations
public class slasherStatsManager {
    //Stores all horror movies the user adds in a list
    private List<horrorMovie> movies;
    //Tracks points as movies are added
    private int accountPoints;

    //Constructor
    public slasherStatsManager() {
        this.movies = new ArrayList<>();
        //Starts account points at 0
        this.accountPoints = 0;
    }
    //Getter for account points
    public int getAccountPoints() {
        return accountPoints;
    }
    //Returns a copy of the movie list
    public List<horrorMovie> viewMovies() {
        return new ArrayList<>(movies);
    }
    //Adds a movie and if the movie is null it will return false
    public boolean addMovie(horrorMovie movie) {
        if (movie == null) return false;
        //Ensures that the input date is in the valid format. If the date is not right it will prompt the user to enter it again.
        if (!isValidDate(movie.getDateWatched())) {
            System.out.println("Invalid date format. Please use MM-DD-YYYY.");
            return false;
        }
        //Ensures that the rating is also within the valid range. Returns a message and prompts the user to try again.
        if (movie.getRating() < 0.0 || movie.getRating() > 10.0) {
            System.out.println("Invalid rating. Must be between 0.0 and 10.0.");
            return false;
        }
        //If all conditions are met add the movie to the database and add 10 points to account points
        movies.add(movie);
        accountPoints += 10;
        return true;
    }

    public List<horrorMovie> addBulkMovies(String filename) {
        //Makes a list for movies that are successfully added
        List<horrorMovie> added = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                //Opens a file given by the user
                String line = fileScanner.nextLine();
                //Makes sure to know that each field is split by ,
                String[] fields = line.split(",");
                //Skips the line is the fields length is not exactly 8
                if (fields.length != 8) {
                    continue;
                }
                try {
                    //Splits each movie given into the 8 fields that each are expected to have
                    String title = fields[0].trim();
                    String director = fields[1].trim();
                    int year = Integer.parseInt(fields[2].trim());
                    int runtime = Integer.parseInt(fields[3].trim());
                    String platform = fields[4].trim();
                    double rating = Double.parseDouble(fields[5].trim());
                    String tags = fields[6].trim();
                    String date = fields[7].trim();
                    //Makes the horror movie object and validated the date and rating
                    horrorMovie movie = new horrorMovie(title, director, year, runtime, platform, rating, tags, date);
                    if (isValidDate(date) && rating >= 0.0 && rating <= 10.0) {
                        //Adds the movie to the list and adds 10 points to account points for every added movie
                        movies.add(movie);
                        accountPoints += 10;
                        added.add(movie);
                    }
                //Skip the line if it fails
                } catch (Exception ignored) {
                }
            }
        //If there is an error reading the given file display this message
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return added;
    }
    //Method used to update a movie by searching for it by title
    public boolean updateMovieField(String title, int fieldChoice, String newValue) {
        //Looks for the movie in the database
        horrorMovie movie = findMovie(title);
        //If the movie does not exist return false
        if (movie == null) return false;
        //If the movie is found to exist, allow the user to choose which field to update
        try {
            //Allows a user to select a number to indicate which field to update
            switch (fieldChoice) {
                //Choice to set new title
                case 1 -> movie.setTitle(newValue);
                //Choice to set new director
                case 2 -> movie.setDirector(newValue);
                //Choice to set a new release year
                case 3 -> movie.setReleaseYear(Integer.parseInt(newValue));
                //Choice to set new runtime minutes
                case 4 -> movie.setRuntimeMinutes(Integer.parseInt(newValue));
                //Choice to set new streaming platform
                case 5 -> movie.setStreamingPlatform(newValue);
                //Choice to set new user rating
                case 6 -> {
                    double rating = Double.parseDouble(newValue);
                    if (rating < 0.0 || rating > 10.0) return false;
                    movie.setRating(rating);
                }
                //Choice to set new tags
                case 7 -> movie.setTags(newValue);
                //Choice to set new date
                case 8 -> {
                    //Ensures that the date is valid
                    if (!isValidDate(newValue)) return false;
                    movie.setDateWatched(newValue);
                }
                //If none of the appropriate case values are picked return false
                default -> {
                    return false;
                }
            }
            //If a case is chosen return true
            return true;
            //If an exception is caught return false
        } catch (Exception e) {
            return false;
        }
    }

    //Method to delete a movie given its title
    public boolean deleteMovie(String title) {
        //Searches for the movie by title
        horrorMovie movie = findMovie(title);
        //If movie does not exist return false
        if (movie == null) return false;
        //If the movie is found by title then delete the movie from the database
        movies.remove(movie);
        //When the movie is deleted then subtract 10 points from account points
        accountPoints -= 10;
        return true;
    }
    //Searches for a horror movie by its title and ignores case rules
    private horrorMovie findMovie(String title) {
        for (horrorMovie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null;
    }
    //Method to check if the date is valid
    public static boolean isValidDate(String date) {
        try {
            //Requires that the date be returned in this specific format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
