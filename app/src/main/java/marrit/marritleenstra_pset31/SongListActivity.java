package marrit.marritleenstra_pset31;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;


/**
 * Created by Marrit on 27-9-2017.
 * Based on: https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
 */

public class SongListActivity extends ListActivity {

    // declare variables
    private ArrayList<Song> mSongs = new ArrayList<Song>();
    private SongAdapter mAdapter;

    // add static strings for key-value pairs
    public static final String EXTRA_SONG_ID = "marrit.marritleenstra_pset31.SongId";
    public static final String SOURCEACT = "marrit.marritleenstra_pset31.SourceActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        Log.d(TAG, "SongListActivity started");


        SongLab songLab = SongLab.get(this);
        mSongs = songLab.getSongs();

        //populate list for testing purposes
        /*for (int i = 0; i < 10; i++) {
            Song song = new Song();
            song.setTitle("Song #" + i);
            song.setArtist("Artist #" + i);
            mSongs.add(song);
        }*/

        // instantiate the SongAdapter class
        mAdapter = new SongAdapter(this, R.layout.list_item, mSongs);
        setListAdapter(mAdapter);

        ListView lv = getListView();

        lv.setOnItemClickListener(new MyActivityListener());

    }

    /*public static Intent newIntent(Context packageContext, UUID mId) {
        Intent intent = new Intent(packageContext, SongActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_SONG_ID, mId);
        extras.putString(SOURCEACT, "SongListActivity");
        intent.putExtras(extras);
        return intent;
    }*/

    // inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_song_list, menu);
        return true;
    }

    // handle clicks on menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_song:

                // go to SearchActivity to add the song the user wants
                Intent intent = new Intent(this, SearchActivity.class);
                this.startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

