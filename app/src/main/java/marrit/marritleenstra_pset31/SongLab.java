package marrit.marritleenstra_pset31;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by Marrit on 26-9-2017.
 * Class containing the centralized data stash for Song objects.
 * Based on Phillips, Stewart, Marsicano (2017). Android Programming. The big nerd ranch guide.
 * 3th edition. Chapter 8. Changed to ArrayList (not List) and added some methods.
 */

 class SongLab {

    // declare variables
    private static SongLab sSongLab;
    private static ArrayList<Song> mSongs;

    // get the SongLab object
    static SongLab get(Context context) {
        sSongLab = retrieveSongLabFromSharedPrefs(context);
        if (sSongLab == null) {
            sSongLab = new SongLab();
        }
        return sSongLab;
    }

    // create a new mSongs arrayList for the new SongLab
    private SongLab() {
        mSongs = new ArrayList<>();
    }

    // fill the arrayList with songs if in Shared preferences
    ArrayList<Song> getSongs(Context context) {
        mSongs = retrieveArrayFromSharedPrefs(context);
        return mSongs;
    }

    // find the song with the specified id
    static Song getSong(UUID id) {
        for (Song song : mSongs) {
            if (song.getID().equals(id)) {
                return song;
            }
        }
        return null;
    }

    // check if the specified song is already in the arraylist
    static Boolean duplicateSong(Song s) {
        String title = s.getTitle();
        String artist = s.getArtist();

        for (Song song: mSongs) {
            if (song.getTitle().equals(title)) {
                if (song.getArtist().equals(artist)) {
                    return true;
                }
            }
        }
        return false;
    }

    // add song to the arrayList with all info available
     static void addSong(Song s) {
        s.setTitle(s.getTitle());
        s.setArtist(s.getArtist());
        s.setAlbum(s.getAlbum());
        s.setSummary(s.getSummary());
        mSongs.add(s);
    }

    // remove song from arrayList
    static void removeSong(Song s) {
        mSongs.remove(s);
    }

    // save songLab and it's list of songs to the shared preferences to make them persistent
    // method source: https://stackoverflow.com/questions/22984696/storing-array-list-object-in-sharedpreferences
    static void saveToSharedPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("SETTINGS", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String jsonSongs = gson.toJson(mSongs);

        editor.putString("SAVEDSONGS", jsonSongs);

        Gson songLabGson = new Gson();
        String jsonSongLab = songLabGson.toJson(sSongLab);
        editor.putString("SONGLAB", jsonSongLab);

        editor.apply();
    }

    // get the arrayList from the shared preferences
    private static ArrayList<Song>  retrieveArrayFromSharedPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("SETTINGS", context.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonSongs = prefs.getString("SAVEDSONGS", null);

        if (jsonSongs != null) {
            Type type = new TypeToken<ArrayList<Song>>() {}.getType();
            ArrayList<Song> mSavedSongs = gson.fromJson(jsonSongs, type);
            return mSavedSongs;
        }

        return mSongs;
    }

    // get the SongLab from the shared preferences
    private static SongLab retrieveSongLabFromSharedPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("SETTINGS", context.MODE_PRIVATE);

        Gson songLabGson = new Gson();
        String jsonSongLab = prefs.getString("SONGLAB", null);

        Type type2 = new TypeToken<SongLab>() {}.getType();
        SongLab sSongLab = songLabGson.fromJson(jsonSongLab, type2);
        return sSongLab;
    }

}
