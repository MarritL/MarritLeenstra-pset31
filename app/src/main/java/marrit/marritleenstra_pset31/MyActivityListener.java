package marrit.marritleenstra_pset31;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.UUID;

import static marrit.marritleenstra_pset31.SongListActivity.SOURCEACT;

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
        Bundle extras = new Bundle();
        extras.putSerializable("SONG_ID", mId);
        extras.putString("SOURCEACT", "SongListActivity");
        intent.putExtras(extras);

        view.getContext().startActivity(intent);
    }
}
