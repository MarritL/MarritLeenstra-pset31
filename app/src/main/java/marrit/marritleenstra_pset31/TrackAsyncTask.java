package marrit.marritleenstra_pset31;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Marrit on 2-10-2017.
 * Method for asynchronous searching in the database of Last.fm
 */

class TrackAsyncTask extends AsyncTask<String, Integer, String> {

    // declare variables
    private final Context mContext;
    private final SearchActivity mSearchActivity;

    private static final String method1 = "track.search&track=";
    private static final String mehtod2 = "artist.gettoptracks&artist=";

    TrackAsyncTask(SearchActivity act) {
        this.mSearchActivity = act;
        this.mContext = this.mSearchActivity.getApplicationContext();
    }


    // do before executing the AsyncTask
    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext, "Searching...", Toast.LENGTH_SHORT).show();
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

        // initiate a new arrayList to display the songs found
        ArrayList<Song> mSongsList = new ArrayList<>();

        // check which method is used
        if (result.startsWith(method1)) {
            // delete the extra info about the method used
            result =result.replaceFirst(method1, "");

            // retrieve the songs and cast from JSON to Song objects, then put them in arrayList
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
                    mSongsList.add(song);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // display tracks of artist when searching for artist
        else if (result.startsWith(mehtod2)) {
            // delete the extra info about the method used
            result = result.replaceFirst(mehtod2, "");

            // retrieve the songs and cast from JSON to Song objects, then put in the arrayList
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
                    mSongsList.add(song);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // display results in SearchListActivity
        this.mSearchActivity.trackStartIntent(mSongsList);
    }

}
