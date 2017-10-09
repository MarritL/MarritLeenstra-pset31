package marrit.marritleenstra_pset31;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
        sSongLab = retrieveSongLabFromSharedPrefs(context);
        if (sSongLab == null) {
            sSongLab = new SongLab(context);
        }
        return sSongLab;
    }

    private SongLab(Context context) {
        mSongs = new ArrayList<>();
    }


    public ArrayList<Song> getSongs(Context context) {
        mSongs = retrieveArrayFromSharedPrefs(context);
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
        s.setSummary(s.getSummary());
        mSongs.add(s);
    }


    public static void removeSong(Song s) {
        mSongs.remove(s);
    }

    // method source: https://stackoverflow.com/questions/22984696/storing-array-list-object-in-sharedpreferences
    public static void saveToSharedPrefs(View view, Context context) {
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

    public static ArrayList<Song>  retrieveArrayFromSharedPrefs(Context context) {
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

    public static SongLab retrieveSongLabFromSharedPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("SETTINGS", context.MODE_PRIVATE);

        Gson songLabGson = new Gson();
        String jsonSongLab = prefs.getString("SONGLAB", null);

        Type type2 = new TypeToken<SongLab>() {}.getType();
        SongLab sSongLab = songLabGson.fromJson(jsonSongLab, type2);
        return sSongLab;
    }


}
