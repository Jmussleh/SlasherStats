import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

public class slasherStatsManager {
    private List<horrorMovie> movies;
    private int accountPoints;

    public slasherStatsManager() {
        this.movies = new ArrayList<>();
        this.accountPoints = 0;
    }

    public int getAccountPoints() {
        return accountPoints;
    }

    public List<horrorMovie> viewMovies() {
        return new ArrayList<>(movies);
    }

    public boolean addMovie(horrorMovie movie) {
        if (movie == null) return false;

        if (!isValidDate(movie.getDateWatched())) {
            System.out.println("Invalid date format. Please use MM-DD-YYYY.");
            return false;
        }

        if (movie.getRating() < 0.0 || movie.getRating() > 10.0) {
            System.out.println("Invalid rating. Must be between 0.0 and 10.0.");
            return false;
        }

        movies.add(movie);
        accountPoints += 10;
        return true;
    }

    public List<horrorMovie> addBulkMovies(String filename) {
        List<horrorMovie> added = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] fields = line.split(",");
                if (fields.length != 8) {
                    continue; // Skip malformed lines
                }

                try {
                    String title = fields[0].trim();
                    String director = fields[1].trim();
                    int year = Integer.parseInt(fields[2].trim());
                    int runtime = Integer.parseInt(fields[3].trim());
                    String platform = fields[4].trim();
                    double rating = Double.parseDouble(fields[5].trim());
                    String tags = fields[6].trim();
                    String date = fields[7].trim();

                    horrorMovie movie = new horrorMovie(title, director, year, runtime, platform, rating, tags, date);
                    if (isValidDate(date) && rating >= 0.0 && rating <= 10.0) {
                        movies.add(movie);
                        accountPoints += 10;
                        added.add(movie);
                    }
                } catch (Exception ignored) {
                    // skip invalid lines
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return added;
    }

    public boolean updateMovie(String title, horrorMovie updatedMovie) {
        horrorMovie existing = findMovie(title);
        if (existing == null) return false;

        if (!isValidDate(updatedMovie.getDateWatched())) return false;
        if (updatedMovie.getRating() < 0.0 || updatedMovie.getRating() > 10.0) return false;

        existing.setTitle(updatedMovie.getTitle());
        existing.setDirector(updatedMovie.getDirector());
        existing.setReleaseYear(updatedMovie.getReleaseYear());
        existing.setRuntimeMinutes(updatedMovie.getRuntimeMinutes());
        existing.setStreamingPlatform(updatedMovie.getStreamingPlatform());
        existing.setRating(updatedMovie.getRating());
        existing.setTags(updatedMovie.getTags());
        existing.setDateWatched(updatedMovie.getDateWatched());
        return true;
    }

    public boolean deleteMovie(String title) {
        horrorMovie movie = findMovie(title);
        if (movie == null) return false;
        movies.remove(movie);
        accountPoints -= 10;
        return true;
    }

    private horrorMovie findMovie(String title) {
        for (horrorMovie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null;
    }

    public static boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
