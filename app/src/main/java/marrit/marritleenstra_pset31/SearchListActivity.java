package marrit.marritleenstra_pset31;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.UUID;

import static marrit.marritleenstra_pset31.SongListActivity.SOURCEACT;

/**
 * Created by Marrit on 2-10-2017.
 */

public class SearchListActivity extends ListActivity {

    // declare variables
    ArrayList<Song> mSearchedSongs;
    SongSearchAdapter mSongSearchAdapter;
    ArrayList<Song> mSongs;

    // add static strings for key-value pairs
    public static final String EXTRA_SONG_ID = "marrit.marritleenstra_pset31.SongId";
    private static final String TAG = "ERRORTAG";
    public static final String TITLE = "marrit.marritleenstra_pset31.title";
    public static final String ARTIST = "marrit.marritleenstra_pset31.artist";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreatecalled");
        setContentView(R.layout.activity_search_song_list);
        Log.d(TAG, "Created SearchListActivity");


        Bundle extras = getIntent().getExtras();
        mSearchedSongs = (ArrayList<Song>) extras.getSerializable("data");

        // instantiate the SongAdapter class
        //mSongSearchAdapter = new SongSearchAdapter(this, R.layout.searched_list_item, mSearchedSongs);
        mSongSearchAdapter = new SongSearchAdapter(this, R.layout.searched_list_item, mSearchedSongs);
        setListAdapter(mSongSearchAdapter);

        ListView lv = getListView();

        // set listener on listview
        lv.setOnItemClickListener(new listViewListener());
    }


    private class listViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            Song song = (Song) parent.getItemAtPosition(position);
            UUID mId = song.getID();
            String mTitle = song.getTitle();
            String mArtist = song.getArtist();

            // get extra info about the track
            extraInfoSearch(view, mArtist, mTitle);



            //Intent intent = SongListActivity.newIntent(SongListActivity, SongListActivity.i.getId());
            Intent intent = new Intent(view.getContext(), SongActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("SONG_ID", mId);
            extras.putString("TITLE", mTitle);
            extras.putString("ARTIST", mArtist);
            extras.putString("SOURCEACT", "SearchListActivity");
            intent.putExtras(extras);
            view.getContext().startActivity(intent);
        }
    }

    public void extraInfoSearch(View view, String artist, String title) {
        TrackAsyncTask2 asyncTask2 = new TrackAsyncTask2(this);
        Log.d(TAG, "calling asyncTask from extraInfoSearch");
        asyncTask2.execute(artist, title);
        Log.d(TAG, "after asyincTask.execute(extraInfoSearch");
    }



}



