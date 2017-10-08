package marrit.marritleenstra_pset31;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

import static android.content.ContentValues.TAG;
import static marrit.marritleenstra_pset31.SongListActivity.SOURCEACT;


public class SongActivity extends AppCompatActivity {

    private Song mSong;
    private TextView mSongTitle;
    private TextView mSongArtist;
    private TextView mTVAlbum;
    private TextView mSongAlbum;
    private TextView mTVGenre;
    private TextView mSongGenre;
    private Button mButtonAdd;
    private Button mButtonDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Log.d(TAG, "SongActivity Created");

        // initialise Views
        mSongTitle = (TextView) findViewById(R.id.song_title);
        mSongArtist = (TextView) findViewById(R.id.song_artist);
        mTVAlbum = (TextView) findViewById(R.id.TV_song_album);
        mSongAlbum = (TextView) findViewById(R.id.song_album);
        mTVGenre = (TextView) findViewById(R.id.TV_song_genre);
        mSongGenre = (TextView) findViewById(R.id.song_genre);
        mButtonAdd = (Button) findViewById(R.id.button_add_to_List);
        mButtonDelete = (Button) findViewById(R.id.button_delete_from_List);

        // find out which intent started SongActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String startingAct = extras.getString("SOURCEACT");
        System.out.println(startingAct);

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
            mSong.setGenre(extras.getString("GENRE"));
            mSong.setAlbum(extras.getString("ALBUM"));
            //mSongAlbum.setVisibility(View.INVISIBLE);
            //mTVAlbum.setVisibility(View.INVISIBLE);
            //mSongGenre.setVisibility(View.INVISIBLE);
            //mTVGenre.setVisibility(View.INVISIBLE);
            mButtonDelete.setVisibility(View.INVISIBLE);
            mButtonAdd.setOnClickListener(new onClickAddSong());
        }
        else {
            System.out.println("intent starter not found");
        }


        mSong.setTitle(mSong.getTitle());
        mSongTitle.setText(mSong.getTitle());

        mSong.setArtist(mSong.getArtist());
        mSongArtist.setText(mSong.getArtist());

        mSong.setAlbum(mSong.getAlbum());
        mSongAlbum.setText(mSong.getAlbum());

        mSong.setGenre(mSong.getGenre());
        mSongGenre.setText(mSong.getGenre());
    }

    // add song to ListenList when button clicked
    private class onClickAddSong implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // TODO: get other info about song from server!
            SongLab.addSong(mSong);
            Intent intent = new Intent(view.getContext(), SongListActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    // delete song from ListenList when button clicked
    private class onClickRemoveSong implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            SongLab.removeSong(mSong);
            Intent intent = new Intent(view.getContext(), SongListActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}
