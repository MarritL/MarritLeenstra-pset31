package marrit.marritleenstra_pset31;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Marrit on 2-10-2017.
 */

public class TrackAsyncTask extends AsyncTask<String, Integer, String> {
    private Context mContext;
    private SearchActivity mSearchActivity;

    public TrackAsyncTask(SearchActivity act) {
        this.mSearchActivity = act;
        this.mContext = this.mSearchActivity.getApplicationContext();
    }

    // do before executing the AsyncTask
    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext, "Searching for tracks...", Toast.LENGTH_SHORT).show();
    }

    // the AsyncTask = search in last.fm database
    @Override
    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer(params);
    }

    // return the result
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ArrayList<Song> mSongsList = new ArrayList<>();

        try {
            JSONObject trackStreamobj = new JSONObject(result);
            JSONObject resultsObj = trackStreamobj.getJSONObject("results");
            JSONObject trackmatchesObj = resultsObj.getJSONObject("trackmatches");
            JSONArray trackObj = trackmatchesObj.getJSONArray("track");
            for (int i = 0; i < trackObj.length(); i++) {
                JSONObject track = trackObj.getJSONObject(i);
                Song song = new Song();
                song.setTitle(track.getString("name"));
                song.setArtist(track.getString("artist"));
                song.setAlbum(track.getString("album"));
                song.setGenre(track.getString("genre"));
                mSongsList.add(song);
            }


            // ga verder naar beneden
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // als je de gevonden data hebt naar volgende activiteit
        this.mSearchActivity.trackStartIntent(mSongsList);
    }

}
