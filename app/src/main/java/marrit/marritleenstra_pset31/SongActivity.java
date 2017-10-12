package marrit.marritleenstra_pset31;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;


/**
 * Created by Marrit on 27-9-2017.
 * Displays details of the song.
 */

public class SongActivity extends AppCompatActivity {

    // declare variable mSong
    private Song mSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        // initialise Views
        TextView mSongTitle = (TextView) findViewById(R.id.song_title);
        TextView mSongArtist = (TextView) findViewById(R.id.song_artist);
        TextView mTVAlbum = (TextView) findViewById(R.id.TV_song_album);
        TextView mTVSummary = (TextView) findViewById(R.id.TV_song_summary);
        TextView mSongAlbum = (TextView) findViewById(R.id.song_album);
        TextView mSongSummary = (TextView) findViewById(R.id.song_summary);
        Button mButtonAdd = (Button) findViewById(R.id.button_add_to_List);
        Button mButtonDelete = (Button) findViewById(R.id.button_delete_from_List);

        // find out which intent started SongActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String startingAct = extras.getString("SOURCEACT");

        // depending on which activity started SongActivity display certain information
        if (startingAct.equals("SongListActivity")) {
            UUID songId = (UUID) extras.getSerializable("SONG_ID");
            mSong = SongLab.getSong(songId);

            // enable only delete song
            mButtonAdd.setVisibility(View.INVISIBLE);
            mButtonDelete.setOnClickListener(new onClickRemoveSong());
        }
        else if (startingAct.equals("SearchListActivity")) {
            mSong = new Song();
            mSong.setTitle(extras.getString("TITLE"));
            mSong.setArtist(extras.getString("ARTIST"));
            mSong.setSummary(extras.getString("SUMMARY"));
            mSong.setAlbum(extras.getString("ALBUM"));

            // enable only add song
            mButtonDelete.setVisibility(View.INVISIBLE);
            mButtonAdd.setOnClickListener(new onClickAddSong());
        }
        // if there is no extra info to display, hide the extra fields and display excuse message
        else if (startingAct.equals("NoWiki")){
            mSong = new Song();
            mSong.setTitle(extras.getString("TITLE"));
            mSong.setArtist(extras.getString("ARTIST"));

            // display excuse message
            Toast.makeText(SongActivity.this, "Sorry, no extra info available.", Toast.LENGTH_SHORT).show();

            // enable only add song
            mButtonDelete.setVisibility(View.INVISIBLE);
            mButtonAdd.setOnClickListener(new onClickAddSong());
        }

        // display title and artist
        mSong.setTitle(mSong.getTitle());
        mSongTitle.setText(mSong.getTitle());
        mSong.setArtist(mSong.getArtist());
        mSongArtist.setText(mSong.getArtist());

        // if available display album and wiki, if not, hide album and wiki.
        String mAlbum = mSong.getAlbum();
        if (mAlbum.equals("")) {
            mTVAlbum.setVisibility(View.INVISIBLE);
        }
        else {
            mSongAlbum.setText(mAlbum);
        }

        String mSummary = mSong.getSummary();
        if (mSummary.equals("")) {
            mTVSummary.setVisibility(View.INVISIBLE);
        }
        else {
            mSongSummary.setText(mSummary);
        }

    }

    // add song to ListenList when button clicked
    private class onClickAddSong implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // make sure a song can be added only once
            if (!SongLab.duplicateSong(mSong)) {
                SongLab.addSong(mSong);

                // update shared Preferences
                SongLab.saveToSharedPrefs(view.getContext());

                // show the updated list of To-Listen songs
                Intent intent = new Intent(view.getContext(), SongListActivity.class);
                view.getContext().startActivity(intent);
            }
            // show message if song is already in To-Listen List
            else {
                Toast.makeText(SongActivity.this, "This song is already in your To-Listen List!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // delete song from ListenList when button clicked
    private class onClickRemoveSong implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            SongLab.removeSong(mSong);

            // update shared preferences
            SongLab.saveToSharedPrefs(view.getContext());

            // show the updated list of To-Listen songs
            Intent intent = new Intent(view.getContext(), SongListActivity.class);
            view.getContext().startActivity(intent);
        }
    }

}

