//Imports all classes from the java package util.
import java.util.*;

public class slasherStatsApp {
    //scanner object for reading user input from the console
    static Scanner sc = new Scanner(System.in);
    //Creates a list to show horror movie objects that are created by the user
    static List<horrorMovie> movies = new ArrayList<>();
    //starts account points at 0 as a starting point
    static int accountPoints = 0;

    //starts the program and controls the loop
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            //Displays the menu and reads the user choice
            displayMenu();
            String input = sc.nextLine();
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
    static void displayMenu() {
        System.out.println("\n---*Welcome to the SlasherStats App*---");
        System.out.println("1. Create Horror Movie Entry");
        System.out.println("2. View All Movies");
        System.out.println("3. Update Movie Entry");
        System.out.println("4. Delete Movie Entry");
        System.out.println("5. View Account Points");
        System.out.println("6. Exit");
    }
    //Method for creating a movie object
    static void createMovie() {
        try {
            System.out.println("Enter movie title: ");
            String title = sc.nextLine();

            System.out.println("Enter movie director: ");
            String director = sc.nextLine();

            System.out.println("Enter year of release: ");
            int year = sc.nextInt();

            System.out.println("Enter runtime minutes: ");
            int runtimeMinutes = sc.nextInt();

            System.out.println("Enter streaming platform: ");
            String streamingPlatform = sc.nextLine();

            System.out.println("Enter rating: ");
            double rating = sc.nextDouble();

            System.out.println("Enter tags: ");
            String tags = sc.nextLine();

            System.out.println("Enter datewatched: ");
            boolean dateWatched = sc.nextBoolean();

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
    static void viewMovies() {
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
    static void updateMovie() {}

}