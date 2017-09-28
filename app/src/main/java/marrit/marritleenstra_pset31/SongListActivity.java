package marrit.marritleenstra_pset31;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);


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

    public static Intent newIntent(Context packageContext, UUID mId) {
        Intent intent = new Intent(packageContext, SongActivity.class);
        intent.putExtra(EXTRA_SONG_ID, mId);
        return intent;
    }
}

