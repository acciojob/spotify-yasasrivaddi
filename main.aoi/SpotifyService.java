import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpotifyService {
    private Map<String, User> users;
    private Map<String, Artist> artists;
    private Map<String, Album> albums;
    private Map<String, Song> songs;
    private List<Playlist> playlists;

    public SpotifyService() {
        users = new HashMap<>();
        artists = new HashMap<>();
        albums = new HashMap<>();
        songs = new HashMap<>();
        playlists = new ArrayList<>();
    }

    public void createUser(String name, String mobileNumber) {
        User newUser = new User(name, mobileNumber);
        users.put(mobileNumber, newUser);
    }

    public void createArtist(String name) {
        Artist newArtist = new Artist(name);
        artists.put(name, newArtist);
    }

    public void createAlbum(String title, String artistName) {
        Artist artist = artists.get(artistName);
        if (artist == null) {
            artist = new Artist(artistName);
            artists.put(artistName, artist);
        }
        Album newAlbum = new Album(title, artistName);
        albums.put(title, newAlbum);
    }

    public void createSong(String title, String albumName) throws Exception {
        Album album = albums.get(albumName);
        if (album == null) {
            throw new Exception("Album does not exist");
        }
        Song newSong = new Song(title, albumName);
        songs.put(title, newSong);
    }

    public void createPlaylistBasedOnLength(String title, String mobileNumber, int songLength) {
        User user = users.get(mobileNumber);
        if (user == null) {
            return;
        }

        Playlist newPlaylist = new Playlist(title, user);
        for (Song song : songs.values()) {
            if (songLength == getSongLength(song)) {
                newPlaylist.getSongs().add(song);
            }
        }
        playlists.add(newPlaylist);
    }

    public void createPlaylistBasedOnName(String title, String mobileNumber, List<String> songTitles) {
        User user = users.get(mobileNumber);
        if (user == null) {
            return;
        }

        Playlist newPlaylist = new Playlist(title, user);
        for (String songTitle : songTitles) {
            Song song = songs.get(songTitle);
            if (song != null) {
                newPlaylist.getSongs().add(song);
            }
        }
        playlists.add(newPlaylist);
    }

    public void findPlaylist(String playlistTitle, String mobileNumber) {
        User user = users.get(mobileNumber);
        if (user == null) {
            return;
        }

        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equals(playlistTitle)) {
                if (!playlist.getListeners().contains(user)) {
                    playlist.getListeners().add(user);
                }
                break;
            }
        }
    }

    public void likeSong(String songTitle, String mobileNumber) {
        User user = users.get(mobileNumber);
        if (user == null) {
            return;
        }

        Song song = songs.get(songTitle);
        if (song != null && !user.getLikedSongs().contains(song)) {
            song.setLikes(song.getLikes() + 1);
            user.getLikedSongs().add(song);
            Artist artist = artists.get(getArtistName(song));
            if (artist != null) {
                artist.setLikes(artist.getLikes() + 1);
            }
        }
    }

    public String mostPopularArtist() {
        int maxLikes = Integer.MIN_VALUE;
        String popularArtist = null;
        for (Artist artist : artists.values()) {
            if (artist.getLikes() > maxLikes) {
                maxLikes = artist.getLikes();
                popularArtist = artist.getName();
            }
        }
        return popularArtist;
    }

    public String mostPopularSong() {
        int maxLikes = Integer.MIN_VALUE;
        String popularSong = null;
        for (Song song : songs.values()) {
            if (song.getLikes() > maxLikes) {
                maxLikes = song.getLikes();
                popularSong = song.getTitle();
            }
        }
        return popularSong;
    }

    private int getSongLength(Song song) {
        // Implement this method if needed
    }

    private String getArtistName(Song song) {
        Album album = albums.get(song.getAlbumName());
        if (album != null) {
            return album.getArtistName();
        }
        return null;
    }
}
