package marrit.marritleenstra_pset31;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by Marrit on 26-9-2017.
 * Class containing the centralized data stash for Song objects.
 * Based on Phillips, Stewart, Marsicano (2017). Android Programming. The big nerd ranch guide.
 * 3th edition. Chapter 8. Changed to ArrayList (not List).
 */

public class SongLab {
    private static SongLab sSongLab;

    private static ArrayList<Song> mSongs;

    public static SongLab get(Context context) {
        if (sSongLab == null) {
            sSongLab = new SongLab(context);
        }
        return sSongLab;
    }


    private SongLab(Context context) {
        mSongs = new ArrayList<>();

        /*//populate list for testing purposes
        for (int i = 0; i <100; i++) {
            Song song = new Song();
            song.setTitle("Song #"+i);
            song.setArtist("Artist #"+i);
            song.setAlbum("Album #"+i);
            song.setGenre("Genre #" +i);
            mSongs.add(song);
        }*/
    }

    public ArrayList<Song> getSongs() {
        return mSongs;
    }

    public static Song getSong(UUID id) {
        for (Song song : mSongs) {
            if (song.getID().equals(id)) {
                return song;
            }
        }
        return null;
    }

    public static void addSong(Song s) {
        s.setTitle(s.getTitle());
        s.setArtist(s.getArtist());
        s.setAlbum(s.getAlbum());
        s.setGenre(s.getGenre());
        mSongs.add(s);
    }

}
