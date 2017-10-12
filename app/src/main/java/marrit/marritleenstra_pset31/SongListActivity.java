package marrit.marritleenstra_pset31;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by Marrit on 27-9-2017.
 * Displays a list of songs the user wants to listen (To-Listen List)
 * Based on: https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
 */

public class SongListActivity extends ListActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        // get the songs the user put on the list
        SongLab songLab = SongLab.get(this);
        ArrayList<Song> mSongs = songLab.getSongs(this);

        // instantiate the SongAdapter class
        SongAdapter mAdapter = new SongAdapter(this, R.layout.list_item, mSongs);
        setListAdapter(mAdapter);

        // display list
        ListView lv = getListView();

        lv.setOnItemClickListener(new MyActivityListener());

    }

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

    // listener that starts the songActivity with the right song if item clicked
    private class MyActivityListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {

        Song song = (Song) parent.getItemAtPosition(position);
        UUID mId = song.getID();
        Intent intent = new Intent(view.getContext(), SongActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("SONG_ID", mId);
        extras.putString("SOURCEACT", "SongListActivity");
        intent.putExtras(extras);

        view.getContext().startActivity(intent);
    }
    }

}

