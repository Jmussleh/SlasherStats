import java.util.Scanner;
import java.util.List;

public class slasherStatsApp {
    private Scanner scanner;
    private slasherStatsManager appManager;

    public slasherStatsApp() {
        this.scanner = new Scanner(System.in);
        this.appManager = new slasherStatsManager();
    }

    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    horrorMovie newMovie = gatherMovieDetailsFromUser();
                    if (newMovie != null) {
                        appManager.addMovie(newMovie);
                        System.out.println("Horror movie added...");
                    }
                    break;
                case "2":
                    System.out.print("Enter filename: ");
                    String filename = scanner.nextLine();
                    List<horrorMovie> bulkAdded = appManager.addBulkMovies(filename);
                    System.out.println("Added " + bulkAdded.size() + " movies.");
                    break;
                case "3":
                    List<horrorMovie> allMovies = appManager.viewMovies();
                    if (allMovies.isEmpty()) {
                        System.out.println("No movies in the database.");
                    } else {
                        allMovies.forEach(System.out::println);
                    }
                    break;
                case "4":
                    System.out.print("Enter title of movie to update: ");
                    String updateTitle = scanner.nextLine();
                    horrorMovie updatedMovie = gatherMovieDetailsFromUser();
                    boolean updated = appManager.updateMovie(updateTitle, updatedMovie);
                    System.out.println(updated ? "Movie updated." : "Movie not found.");
                    break;
                case "5":
                    System.out.print("Enter title of movie to delete: ");
                    String deleteTitle = scanner.nextLine();
                    boolean deleted = appManager.deleteMovie(deleteTitle);
                    System.out.println(deleted ? "Movie deleted." : "Movie not found.");
                    break;
                case "6":
                    System.out.println("Account points: " + appManager.getAccountPoints());
                    break;
                case "7":
                    running = false;
                    System.out.println("System is powering down...");
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n---*Welcome to the SlasherStats App*---");
        System.out.println("1. Create Horror Movie Entry");
        System.out.println("2. Add bulk Horror Movie Entries");
        System.out.println("3. View All Movies");
        System.out.println("4. Update Movie Entry");
        System.out.println("5. Delete Movie Entry");
        System.out.println("6. View Account Points");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private horrorMovie gatherMovieDetailsFromUser() {
        try {
            System.out.print("Enter movie title: ");
            String title = scanner.nextLine();

            System.out.print("Enter movie director: ");
            String director = scanner.nextLine();

            System.out.print("Enter year of release: ");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter runtime minutes: ");
            int runtimeMinutes = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter streaming platform: ");
            String platform = scanner.nextLine();

            System.out.print("Enter rating (0.0 - 10.0): ");
            double rating = Double.parseDouble(scanner.nextLine());
            if (rating < 0.0 || rating > 10.0) {
                System.out.println("Invalid rating range.");
                return null;
            }

            System.out.print("Enter tags: ");
            String tags = scanner.nextLine();

            System.out.print("Enter date watched (MM-DD-YYYY): ");
            String date = scanner.nextLine();
            if (!slasherStatsManager.isValidDate(date)) {
                System.out.println("Invalid date format.");
                return null;
            }

            return new horrorMovie(title, director, year, runtimeMinutes, platform, rating, tags, date);
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return null;
        }
    }

    public static void main(String[] args) {
        new slasherStatsApp().run();
    }
}


