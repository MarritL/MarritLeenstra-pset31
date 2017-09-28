package marrit.marritleenstra_pset31;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Marrit on 28-9-2017.
 */

public class MyActivityListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {

        Intent intent = new Intent(view.getContext(), SongActivity.class);
        view.getContext().startActivity(intent);
    }
}
