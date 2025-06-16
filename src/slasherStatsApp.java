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
            switch (input) {}
        }
    }
}
