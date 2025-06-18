//Imports all classes from the java package util.
import java.util.*;

public class slasherStatsApp {
    //scanner object for reading user input from the console
    private Scanner scanner;
    //Creates a list to show horror movie objects that are created by the user
    private List<horrorMovie> movies;
    //starts account points at 0 as a starting point
    private int accountPoints;

    //starts the program and controls the loop
    public slasherStatsApp() {
        this.scanner = new Scanner(System.in);
        this.movies = new ArrayList<>();
        this.accountPoints = 0;
    }

    public void run() {
        boolean running = true;
        while (running) {
            //Displays the menu and reads the user choice
            displayMenu();
            String input = scanner.nextLine();
            //Determines the action
            switch (input) {
                //Executes the appropriate method based on user input
                case "1": createMovie(); break;
                case "2": viewMovies(); break;
                case "3": updateMovie(); break;
                case "4": deleteMovie(); break;
                case "5": viewAccountPoints(); break;
                //Ends the program and displays a message to the user
                case "6": running = false;
                System.out.println("System is powering down...");
                break;
                default:
                    //Catches invalid inputs from the user and restarts the loop
                    System.out.println("Invalid input");
            }
        }
    }
    //Method for displaying the SlasherStats CLI menu
    private void displayMenu() {
        System.out.println("\n---*Welcome to the SlasherStats App*---");
        System.out.println("1. Create Horror Movie Entry");
        System.out.println("2. View All Movies");
        System.out.println("3. Update Movie Entry");
        System.out.println("4. Delete Movie Entry");
        System.out.println("5. View Account Points");
        System.out.println("6. Exit");
        System.out.println("Enter your choice: ");
    }
    //Method for creating a movie object
    private void createMovie() {
        try {
            System.out.println("Enter movie title: ");
            String title = scanner.nextLine();

            System.out.println("Enter movie director: ");
            String director = scanner.nextLine();

            System.out.println("Enter year of release: ");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter runtime minutes: ");
            int runtimeMinutes = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter streaming platform: ");
            String streamingPlatform = scanner.nextLine();

            System.out.println("Enter rating: ");
            double rating = Double.parseDouble(scanner.nextLine());
            if (rating < 0.0 || rating > 10.0) {
                throw new IllegalArgumentException("Invalid rating value. Rating must be between 0.0 and 10.0.");
            }

            System.out.println("Enter tags: ");
            String tags = scanner.nextLine();

            System.out.println("Enter date watched (MM-DD-YYYY): ");
            String dateWatched = scanner.nextLine();

            horrorMovie movie = new horrorMovie(title, director, year, runtimeMinutes, streamingPlatform, rating, tags, dateWatched);
            //adds a movie to the list
            movies.add(movie);
            //when a new movie is added, 10 points are added to accountPoints
            accountPoints += 10;
            //message to let the user know that the addition was successful
            System.out.println("Horror movie added...");
            //catches the exception if there is an error
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }
    //method to view movies
    private void viewMovies() {
        //if the list is empty, send this error message
        if (movies.isEmpty()) {
            System.out.println("There are no horror movies in the database.");
            return;
        }
        //for every movie in the list, print this
        for (horrorMovie movie : movies) {
            System.out.println(movie);
        }
    }
    //method for updating a movie
    private void updateMovie() {
        System.out.println("Enter title of movie to update: ");
        //reads user input for title
        String title = scanner.nextLine();
        //finds the desired movie to update by title
        horrorMovie movie = findMovie(title);
        //if movie is not in database, display this message
        if (movie == null) {
            System.out.println("Movie not found");
            return;
        }
        //update method movie menu
        System.out.println("Choose a field to update: ");
        System.out.println("1. Title\n2. Director\n3. Release Year\n4. Runtime (Minutes)\n5. Streaming Platform\n6. Rating\n7. Tags\n8. Date Watched");
        //Scans user input for a field
        String field = scanner.nextLine();

        try {
            switch (field) {
                //Reads user input and changes movie title
                case "1":
                    System.out.println("Enter movie title: ");
                    movie.setTitle(scanner.nextLine());
                    break;
                //Reads user input and changes movie director
                case "2":
                    System.out.println("Enter movie director: ");
                    movie.setDirector(scanner.nextLine());
                    break;
                //Reads user input and changes movie year of release
                case "3":
                    System.out.println("Enter year of release: ");
                    movie.setReleaseYear(scanner.nextInt());
                    break;
                //Reads user input and changes movie runtime minutes
                case "4":
                    System.out.println("Enter runtime minutes: ");
                    movie.setRuntimeMinutes(scanner.nextInt());
                    break;
                //Reads user input and changes movie streaming platform
                case "5":
                    System.out.println("Enter streaming platform: ");
                    movie.setStreamingPlatform(scanner.nextLine());
                    break;
                //Reads user input and changes movie rating
                case "6":
                    System.out.println("Enter rating (between 0.0 and 10.0): ");
                    //converts double to String for scanner
                    double newRating = Double.parseDouble(scanner.nextLine());
                    movie.setRating(newRating);
                    break;
                //Reads user input and changes movie tags
                case "7":
                    System.out.println("Enter tags: ");
                    movie.setTags(scanner.nextLine());
                    break;
                //Reads user input and changes movie date watched
                case "8":
                    System.out.println("Enter date watched: ");
                    movie.setDateWatched(scanner.nextLine());
                    break;
                //If invalid options are chosen, display this message and return to the beginning to select a choice again.
                default:
                    System.out.println("Invalid option. Please try again.");
                    return;
            }
            System.out.println("Movie updated...\n" + movie);
        } catch (Exception e) {
            System.out.println("Invalid input. Update failed...");
        }
    }
    //method to delete movie by title
    private void deleteMovie() {
        System.out.println("Enter title of movie to delete: ");
        String title = scanner.nextLine();
        //find movie by title, if movie is not found display this message
        horrorMovie movie = findMovie(title);
        if (movie == null) {
            System.out.println("Movie not found");
            return;
        }
        //remove the movie if found. Take away 10 points from the account ad display a confirmation message.
        movies.remove(movie);
        accountPoints -= 10;
        System.out.println("Movie deleted...\n" + movie);
    }

    //method to view account points
    private void viewAccountPoints() {
        System.out.println("Current account points: " + accountPoints);
    }

    //method to find a horror movie by title
    private horrorMovie findMovie(String title) {
        for (horrorMovie movie : movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        new slasherStatsApp().run();
    }
}