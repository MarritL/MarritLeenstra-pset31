package marrit.marritleenstra_pset31;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Marrit on 2-10-2017.
 */

public class TrackAsyncTask extends AsyncTask<String, Integer, String> {
    private Context mContext;
    private SearchActivity mSearchActivity;

    private static final String TAG = "ERRORTAG";

    private static final String method1 = "track.search&track=";
    private static final String mehtod2 = "artist.gettoptracks&artist=";

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
        Log.d(TAG, "called doInBackground");
        return HttpRequestHelper.downloadFromServer(params);

    }

    // return the result
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(TAG, "called onPostExecute");
        ArrayList<Song> mSongsList = new ArrayList<>();

        // check which method is used
        if (result.startsWith(method1)) {
            result =result.replaceFirst(method1, "");
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
                    //song.setAlbum(track.getString("album"));
                    //song.setGenre(track.getString("genre"));
                    mSongsList.add(song);
                    Log.d(TAG, "after mSongsList.add track");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // display tracks of artist when searching for artist
        else if (result.startsWith(mehtod2)) {
            result = result.replaceFirst(mehtod2, "");

            try {
                JSONObject trackStreamobj = new JSONObject(result);
                JSONObject resultsObj = trackStreamobj.getJSONObject("toptracks");
                JSONArray trackObj = resultsObj.getJSONArray("track");
                for (int i = 0; i < trackObj.length(); i++) {
                    JSONObject track = trackObj.getJSONObject(i);
                    Song song = new Song();
                    song.setTitle(track.getString("name"));
                    JSONObject mArtist = track.getJSONObject("artist");
                    song.setArtist(mArtist.getString("name"));
                    //song.setAlbum(track.getString("album"));
                    //song.setGenre(track.getString("genre"));
                    mSongsList.add(song);
                    Log.d(TAG, "after mSongsList.add artist");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        // display results in SearchListActivity
        Log.d(TAG, "before trackStartIntent");
        this.mSearchActivity.trackStartIntent(mSongsList);
        Log.d(TAG, "after trackStartIntent");
    }

}
