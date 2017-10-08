package marrit.marritleenstra_pset31;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Marrit on 2-10-2017.
 */

public class SearchActivity extends AppCompatActivity {
    EditText mSearchArtist;
    EditText mSearchTrack;

    Button mSearchArtistButton;
    Button mSearchTrackButton;


    private static final String TAG = "ERRORTAG";

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.i(TAG,"created searchActivity");

        //initialise EditText fields
        mSearchArtist = (EditText) findViewById(R.id.search_artist);
        assert mSearchArtist != null;
        mSearchTrack = (EditText) findViewById(R.id.search_track);
        assert mSearchArtist != null;

        //initialise buttons
        mSearchArtistButton = (Button) findViewById(R.id.button_search_artist);
        mSearchTrackButton = (Button) findViewById(R.id.button_search_track);

        //handle click
        mSearchTrackButton.setOnClickListener(new onButtonClickListener());
        mSearchArtistButton.setOnClickListener(new onButtonClickListener());

    }

    public void trackSearch(View view) {
        String trackSearch = mSearchTrack.getText().toString();
        TrackAsyncTask asyncTask = new TrackAsyncTask(this);
        Log.d(TAG, "calling asyncTask.execute()");
        asyncTask.execute(trackSearch, "track.search&track=");
        Log.d(TAG, "after asyncTask.execute(trackSearch)");

        mSearchTrack.getText().clear();
    }

    public void artistSearch(View view) {
        String artistSearch = mSearchArtist.getText().toString();
        TrackAsyncTask asyncTask = new TrackAsyncTask(this);
        Log.d(TAG, "calling asyncTask from artistSearch");
        asyncTask.execute(artistSearch, "artist.gettoptracks&artist=");
        Log.d(TAG, "after asyincTask.execute(artistSearch");
    }

    public void trackStartIntent(ArrayList<Song> songs) {
        Log.d(TAG, "called trackStartIntent");
        Intent intent = new Intent(this, SearchListActivity.class);
        Log.d(TAG, "created new intent");
        intent.putExtra("data", songs);
        Log.d(TAG, "added extras");
        this.startActivity(intent);
        Log.d(TAG, "finished");
    }

    private class onButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view == mSearchTrackButton) {
                trackSearch(view);
                Log.d(TAG,"after trackSearch(View)");
            }
            else {
                artistSearch(view);
                Log.d(TAG,"after artistSearch(View)");
            }
        }

    }
}
