package marrit.marritleenstra_pset31;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Marrit on 8-10-2017.
 * Method to Asynchronous search extra information about a song in database of Last.fm
 */

class TrackAsyncTask2 extends AsyncTask<String, Integer, String> {

    // declare variables
    private final Context mContext;
    private final SearchListActivity mSearchListActivity;


    TrackAsyncTask2(SearchListActivity act) {
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
        return HttpRequestHelper2.downloadFromServer(params);

    }

    // return the result
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

            // retrieve the song and it's information. Then put it in a song object
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

            // create a new intent
            Intent intent = new Intent(this.mSearchListActivity, SongActivity.class);
            UUID mmId = song.getID();
            String mmTitle = song.getTitle();
            String mmArtist = song.getArtist();
            String mmAlbum = song.getAlbum();
            String mmSummary = song.getSummary();

            // put all the info about the song in the new intent
            Bundle extras = new Bundle();
            extras.putSerializable("SONG_ID", mmId);
            extras.putString("TITLE", mmTitle);
            extras.putString("ARTIST", mmArtist);
            extras.putString("ALBUM", mmAlbum);
            extras.putString("SUMMARY", mmSummary);
            extras.putString("SOURCEACT", "SearchListActivity");
            intent.putExtras(extras);

            // go to the SongActivity to display all the information
            this.mSearchListActivity.startActivityForResult(intent, 0);

            // if the information was not found (i.e. track not found in database or other error)
            } catch (JSONException e) {
                e.printStackTrace();

                // retrieve the searchterms from the result
                String[] results =result.split(",,,");
                String chosens = results[1];
                String[] tags = chosens.split("-");
                String mmArtist = tags[0];
                String mmTitle = tags[1];

                // start new intent with the info that you had already in the SearchList (i.e. searchterms)
                Intent intent = new Intent(mSearchListActivity, SongActivity.class);
                Song song = new Song();
                UUID mmId = song.getID();
                Bundle extras = new Bundle();
                extras.putSerializable("SONG_ID", mmId);
                extras.putString("TITLE", mmTitle);
                extras.putString("ARTIST", mmArtist);
                extras.putString("SOURCEACT", "NoWiki");
                intent.putExtras(extras);

                // show all the available information in the SongActivity (is only Artist and Title)
                this.mSearchListActivity.startActivityForResult(intent, 0);
            }
    }

}

