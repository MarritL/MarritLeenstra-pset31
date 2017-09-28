package marrit.marritleenstra_pset31;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.UUID;


public class SongActivity extends AppCompatActivity {

    private Song mSong;
    private TextView mSongTitle;
    private TextView mSongArtist;
    private TextView mSongAlbum;
    private TextView mSongGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        UUID songId = (UUID) getIntent().getSerializableExtra("SONG_ID");
        // mSong = new Song;
        mSong = SongLab.getSong(songId);


        mSongTitle = (TextView) findViewById(R.id.song_title);
        mSong.setTitle(mSong.getTitle());
        mSongTitle.setText(mSong.getTitle());

        mSongArtist = (TextView) findViewById(R.id.song_artist);
        mSong.setArtist(mSong.getArtist());
        mSongArtist.setText(mSong.getArtist());

        mSongAlbum = (TextView) findViewById(R.id.song_album);
        mSong.setAlbum(mSong.getAlbum());
        mSongAlbum.setText(mSong.getAlbum());

        mSongGenre = (TextView) findViewById(R.id.song_genre);
        mSong.setGenre(mSong.getGenre());
        mSongGenre.setText(mSong.getGenre());
    }
}
