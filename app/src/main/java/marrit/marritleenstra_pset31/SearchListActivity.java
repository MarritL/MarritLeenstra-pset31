package marrit.marritleenstra_pset31;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Marrit on 2-10-2017.
 */

public class SearchListActivity extends ListActivity {

    // declare variables
    private ArrayList<Song> mSearchedSongs = new ArrayList<Song>();
    private SongSearchAdapter mSongSearchAdapter;

    // add static strings for key-value pairs
    public static final String EXTRA_SONG_ID = "marrit.marritleenstra_pset31.SongId";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_song_list);


        mSearchedSongs = new ArrayList<Song>();

        Bundle extras = getIntent().getExtras();
        mSearchedSongs = (ArrayList<Song>) extras.getSerializable("data");

        //populate list for testing purposes
        /*for (int i = 0; i < 10; i++) {
            Song song = new Song();
            song.setTitle("Song #" + i);
            song.setArtist("Artist #" + i);
            mSearchedSongs.add(song);
        }*/

        // instantiate the SongAdapter class
        mSongSearchAdapter = new SongSearchAdapter(this, R.layout.searched_list_item, mSearchedSongs);
        setListAdapter(mSongSearchAdapter);

        ListView lv = getListView();
    }

}
