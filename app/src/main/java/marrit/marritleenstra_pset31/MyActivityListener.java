package marrit.marritleenstra_pset31;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import java.util.UUID;

/**
 * Created by Marrit on 28-9-2017.
 */

public class MyActivityListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {

        Song song = (Song) parent.getItemAtPosition(position);
        UUID mId = song.getID();
        //Intent intent = SongListActivity.newIntent(SongListActivity, SongListActivity.i.getId());
        Intent intent = new Intent(view.getContext(), SongActivity.class);
        intent.putExtra("SONG_ID", mId);
        view.getContext().startActivity(intent);
    }
}
