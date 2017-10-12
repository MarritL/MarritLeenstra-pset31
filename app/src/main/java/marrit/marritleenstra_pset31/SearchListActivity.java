package marrit.marritleenstra_pset31;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by Marrit on 2-10-2017.
 * Activity showing the result of the searching activity in a listview
 */

public class SearchListActivity extends ListActivity {

    // declare variables
    private ArrayList<Song> mSearchedSongs;
    private SongSearchAdapter mSongSearchAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_song_list);

        // get list of songs (found at Search Activity)
        Bundle extras = getIntent().getExtras();
        mSearchedSongs = (ArrayList<Song>) extras.getSerializable("data");

        // instantiate the SongAdapter class
        mSongSearchAdapter = new SongSearchAdapter(this, R.layout.searched_list_item, mSearchedSongs);
        setListAdapter(mSongSearchAdapter);

        // display list
        ListView lv = getListView();

        // set listener on listView
        lv.setOnItemClickListener(new listViewListener());
    }

    // handle clicks on listView
    private class listViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            // find out which item was clicked
            Song song = (Song) parent.getItemAtPosition(position);

            // get extra info about the track
            String mTitle = song.getTitle();
            String mArtist = song.getArtist();
            extraInfoSearch(mArtist, mTitle);

        }
    }

    // Asynchronous method to find extra info about the song
    private void extraInfoSearch(String artist, String title) {
        TrackAsyncTask2 asyncTask2 = new TrackAsyncTask2(this);
        asyncTask2.execute(artist, title);
    }

}



