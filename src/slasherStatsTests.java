import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class slasherStatsTests {

    private slasherStatsManager appManager;

    @BeforeEach
    public void setUp() {
        appManager = new slasherStatsManager();
    }

    @Test
    public void testAddMovie_Success() {
        horrorMovie movie = new horrorMovie("Scream", "Wes Craven", 1996, 111, "HBO Max", 7.8, "slasher", "10-30-2021");
        boolean result = appManager.addMovie(movie);
        assertTrue(result);
        assertEquals(1, appManager.viewMovies().size());
    }

    @Test
    public void testAddMovie_Failure_NullMovie() {
        boolean result = (appManager.addMovie(null));
        assertFalse(result);
    }

    @Test
    public void testDeleteMovie_Success() {
        horrorMovie movie = new horrorMovie("The Thing", "John Carpenter", 1982, 109, "Shudder", 8.2, "creature", "11-05-2020");
        appManager.addMovie(movie);
        boolean result = appManager.deleteMovie("The Thing");
        assertTrue(result);
        assertEquals(0, appManager.viewMovies().size());
    }

    @Test
    public void testDeleteMovie_Failure_NotFound() {
        boolean result = appManager.deleteMovie("Nonexistent");
        assertFalse(result);
    }

    @Test
    public void testUpdateMovie_Success() {
        horrorMovie movie = new horrorMovie("Halloween", "John Carpenter", 1978, 91, "Shudder", 7.9, "slasher", "10-31-2022");
        appManager.addMovie(movie);
        horrorMovie updated = new horrorMovie("Halloween", "Rob Zombie", 2007, 109, "Peacock", 6.1, "remake", "10-31-2023");
        boolean result = appManager.updateMovie("Halloween", updated);
        assertTrue(result);
        assertEquals("Rob Zombie", appManager.viewMovies().get(0).getDirector());
    }

    @Test
    public void testUpdateMovie_Failure_NotFound() {
        horrorMovie updated = new horrorMovie("Wrong Title", "Someone", 2022, 100, "Netflix", 5.0, "tags", "10-01-2023");
        boolean result = appManager.updateMovie("Nonexistent", updated);
        assertFalse(result);
    }

    @Test
    public void testAddBulkMovies_Success() throws Exception {
        String filename = "test_bulk_movies.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("It Follows,David Robert Mitchell,2014,100,Netflix,6.8,supernatural,10-29-2021");
            writer.println("The Babadook,Jennifer Kent,2014,94,Hulu,6.9,psychological,10-30-2021");
        }
        List<horrorMovie> added = appManager.addBulkMovies(filename);
        assertEquals(2, added.size());
    }

    @Test
    public void testAddBulkMovies_Failure_FileNotFound() {
        List<horrorMovie> added = appManager.addBulkMovies("nonexistent_file.txt");
        assertEquals(0, added.size());
    }

    @Test
    public void testAccountPoints_CustomFeature() {
        assertEquals(0, appManager.getAccountPoints());
        appManager.addMovie(new horrorMovie("Hereditary", "Ari Aster", 2018, 127, "Max", 7.3, "supernatural", "10-15-2022"));
        assertEquals(10, appManager.getAccountPoints());
        appManager.addMovie(new horrorMovie("Midsommar", "Ari Aster", 2019, 148, "Hulu", 7.1, "folk", "10-16-2022"));
        assertEquals(20, appManager.getAccountPoints());
        appManager.deleteMovie("Hereditary");
        assertEquals(10, appManager.getAccountPoints());
    }

    @Test
    public void testAccountPoints_NegativeCase() {
        // Add and remove to zero
        appManager.addMovie(new horrorMovie("Movie1", "Director", 2020, 100, "Platform", 5.0, "tag", "10-10-2020"));
        appManager.deleteMovie("Movie1");
        assertEquals(0, appManager.getAccountPoints());

        // Try deleting a non-existent movie shouldn't affect points
        appManager.deleteMovie("Nonexistent");
        assertEquals(0, appManager.getAccountPoints());
    }
}
