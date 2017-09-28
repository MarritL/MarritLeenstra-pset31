package marrit.marritleenstra_pset31;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


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

        mSong = new Song();

        mSongTitle = (TextView) findViewById(R.id.song_title);
        // TODO get songTitle
        mSong.setTitle("testTitle");
        mSongTitle.setText(mSong.getTitle());

        mSongArtist = (TextView) findViewById(R.id.song_artist);
        // TODO get songArtist
        mSong.setArtist("testArtist");
        mSongArtist.setText(mSong.getArtist());

        mSongAlbum = (TextView) findViewById(R.id.song_album);
        // TODO get songArtist
        mSong.setAlbum("testAlbum");
        mSongAlbum.setText(mSong.getAlbum());

        mSongGenre = (TextView) findViewById(R.id.song_genre);
        // TODO get songArtist
        mSong.setGenre("testGenre");
        mSongGenre.setText(mSong.getGenre());
    }
}
