import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String title;
    private User creator;
    private List<User> listeners;
    private List<Song> songs;

    public Playlist(String title, User creator) {
        this.title = title;
        this.creator = creator;
        this.listeners = new ArrayList<>();
        this.listeners.add(creator);
        this.songs = new ArrayList<>();
    }

    // Getters and setters
}
