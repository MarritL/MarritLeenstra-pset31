package marrit.marritleenstra_pset31;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Marrit on 27-9-2017.
 * Based on: https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
 */

public class SongListActivity extends ListActivity {

    // declare variables
    private ArrayList<Song> mSongs = new ArrayList<Song>();
    private SongAdapter mAdapter;

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

    }
}

