package marrit.marritleenstra_pset31;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Marrit on 2-10-2017.
 * Gives the user the option to search for an Artist or Track
 * Initiates the searching on button clicked.
 */

public class SearchActivity extends AppCompatActivity {

    // declare views
    private EditText mSearchArtist;
    private EditText mSearchTrack;
    private Button mSearchArtistButton;
    private Button mSearchTrackButton;

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

    // method to search on track
    private void trackSearch() {
        String trackSearch = mSearchTrack.getText().toString();
        TrackAsyncTask asyncTask = new TrackAsyncTask(this);
        asyncTask.execute(trackSearch, "track.search&track=");

        mSearchTrack.getText().clear();
    }

    // method to search on artist
    private void artistSearch() {
        String artistSearch = mSearchArtist.getText().toString();
        TrackAsyncTask asyncTask = new TrackAsyncTask(this);
        asyncTask.execute(artistSearch, "artist.gettoptracks&artist=");

        mSearchArtist.getText().clear();
    }

    // method to move to the list of searched songs
    public void trackStartIntent(ArrayList<Song> songs) {
        Intent intent = new Intent(this, SearchListActivity.class);
        intent.putExtra("data", songs);
        this.startActivity(intent);
    }

    // handle click events on the two different buttons
    private class onButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view == mSearchTrackButton) {
                trackSearch();
            }
            else {
                artistSearch();
            }
        }
    }

}
