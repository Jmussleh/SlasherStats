//This class can be instantiated for a horror movie object. Also contains
//all getters and setters for this object.
public class horrorMovie {
    //All fields for my horror movie object.
    private String title;
    private String director;
    private int releaseYear;
    private int runtimeMinutes;
    private String streamingPlatform;
    private String rating;
    private String tags;
    private String dateWatched;

    //Constructor
    public horrorMovie(String title, String director, int releaseYear, int runtimeMinutes, String streamingPlatform, String rating, String tags, String dateWatched) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.runtimeMinutes = runtimeMinutes;
        this.streamingPlatform = streamingPlatform;
        this.rating = rating;
        this.tags = tags;
        this.dateWatched = dateWatched;
    }

}
