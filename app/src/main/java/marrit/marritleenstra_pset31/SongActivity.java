package marrit.marritleenstra_pset31;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class SongActivity extends AppCompatActivity {

    private Song mSong;
    private TextView mSongTitle;
    private TextView mSongArtist;
    private TextView mTVAlbum;
    private TextView mSongAlbum;
    private TextView mTVSummary;
    private TextView mSongSummary;
    private Button mButtonAdd;
    private Button mButtonDelete;

    private ArrayList<Song> mSearcedSongs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Log.d(TAG, "SongActivity Created");

        // initialise Views
        mSongTitle = (TextView) findViewById(R.id.song_title);
        mSongArtist = (TextView) findViewById(R.id.song_artist);
        mTVAlbum = (TextView) findViewById(R.id.TV_song_album);
        mTVSummary = (TextView) findViewById(R.id.TV_song_summary);
        mSongAlbum = (TextView) findViewById(R.id.song_album);
        mSongSummary = (TextView) findViewById(R.id.song_summary);
        mButtonAdd = (Button) findViewById(R.id.button_add_to_List);
        mButtonDelete = (Button) findViewById(R.id.button_delete_from_List);

        // find out which intent started SongActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String startingAct = extras.getString("SOURCEACT");
        mSearcedSongs = (ArrayList<Song>) extras.getSerializable("data");

        // get extras
        if (startingAct.equals("SongListActivity")) {
            UUID songId = (UUID) extras.getSerializable("SONG_ID");
            mSong = SongLab.getSong(songId);
            mButtonAdd.setVisibility(View.INVISIBLE);
            mButtonDelete.setOnClickListener(new onClickRemoveSong());
        }
        else if (startingAct.equals("SearchListActivity")) {
            mSong = new Song();
            mSong.setTitle(extras.getString("TITLE"));
            mSong.setArtist(extras.getString("ARTIST"));
            mSong.setSummary(extras.getString("SUMMARY"));
            mSong.setAlbum(extras.getString("ALBUM"));
            mButtonDelete.setVisibility(View.INVISIBLE);
            mButtonAdd.setOnClickListener(new onClickAddSong());
        }
        else if (startingAct.equals("NoWiki")){
            mSong = new Song();
            mSong.setTitle(extras.getString("TITLE"));
            mSong.setArtist(extras.getString("ARTIST"));
            mTVSummary.setVisibility(View.INVISIBLE);
            mTVAlbum.setVisibility(View.INVISIBLE);
            mButtonDelete.setVisibility(View.INVISIBLE);
            Toast.makeText(SongActivity.this, "Sorry, no extra info available.", Toast.LENGTH_SHORT).show();
            mButtonAdd.setOnClickListener(new onClickAddSong());
        }

        mSong.setTitle(mSong.getTitle());
        mSongTitle.setText(mSong.getTitle());

        mSong.setArtist(mSong.getArtist());
        mSongArtist.setText(mSong.getArtist());

        mSong.setAlbum(mSong.getAlbum());
        mSongAlbum.setText(mSong.getAlbum());

        mSong.setSummary(mSong.getSummary());
        mSongSummary.setText(mSong.getSummary());
    }

    // add song to ListenList when button clicked
    private class onClickAddSong implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (!SongLab.duplicateSong(mSong)) {
                SongLab.addSong(mSong);
                SongLab.saveToSharedPrefs(view, view.getContext());
                Intent intent = new Intent(view.getContext(), SongListActivity.class);
                view.getContext().startActivity(intent);
            }
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
            SongLab.saveToSharedPrefs(view, view.getContext());
            Intent intent = new Intent(view.getContext(), SongListActivity.class);
            view.getContext().startActivity(intent);
        }
    }

}

