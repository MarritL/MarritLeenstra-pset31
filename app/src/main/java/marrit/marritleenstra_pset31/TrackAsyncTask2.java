package marrit.marritleenstra_pset31;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Marrit on 8-10-2017.
 */

public class TrackAsyncTask2 extends AsyncTask<String, Integer, String> {
    private Context mContext;
    private SearchListActivity mSearchListActivity;

    private static final String TAG = "ERRORTAG";

    public TrackAsyncTask2(SearchListActivity act) {
        this.mSearchListActivity = act;
        this.mContext = this.mSearchListActivity.getApplicationContext();
    }


    // do before executing the AsyncTask
    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext, "Getting Info...", Toast.LENGTH_SHORT).show();
    }

    // the AsyncTask = search in last.fm database
    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "called doInBackground");
        return HttpRequestHelper2.downloadFromServer(params);

    }

    // return the result
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(TAG, "called onPostExecute");

            try {
            JSONObject trackStreamobj = new JSONObject(result);
            JSONObject track = trackStreamobj.getJSONObject("track");

            Song song = new Song();

            song.setTitle(track.getString("name"));

            JSONObject mArtist = track.getJSONObject("artist");
            song.setArtist(mArtist.getString("name"));

            JSONObject mAlbum = track.getJSONObject("album");
            song.setAlbum(mAlbum.getString("title"));
            JSONObject wikiObj = track.getJSONObject("wiki");

            song.setSummary(wikiObj.getString("summary"));
            Log.d(TAG, "after add info to song");


            Intent intent = new Intent(this.mSearchListActivity, SongActivity.class);
            UUID mmId = song.getID();
            String mmTitle = song.getTitle();
            String mmArtist = song.getArtist();
            String mmAlbum = song.getAlbum();
            String mmSummary = song.getSummary();

            Bundle extras = new Bundle();
            extras.putSerializable("SONG_ID", mmId);
            extras.putString("TITLE", mmTitle);
            extras.putString("ARTIST", mmArtist);
            extras.putString("ALBUM", mmAlbum);
            extras.putString("SUMMARY", mmSummary);
            extras.putString("SOURCEACT", "SearchListActivity");
            intent.putExtras(extras);
            Log.d(TAG, "starting from TrackAsyncTask2");
            this.mSearchListActivity.startActivityForResult(intent, 0);


            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

}

