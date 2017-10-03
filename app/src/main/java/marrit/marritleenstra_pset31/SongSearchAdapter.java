package marrit.marritleenstra_pset31;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Marrit on 2-10-2017.
 */

public class SongSearchAdapter extends ArrayAdapter<Song> {

    // declaring an ArrayList of Songs
    private ArrayList<Song> mSearchedSongs;

    // declare TextViews
    public TextView mTitle;
    public TextView mArtist;

    // construct an adapter for the To Listen List
    public SongSearchAdapter(Context context, int textViewResourceId, ArrayList<Song> songs) {
        super(context, textViewResourceId, songs);
        this.mSearchedSongs = songs;
    }

    // create the view for the list
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // check to see if the view is null and need to be inflated
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.searched_list_item, null);
        }

        // iterate through the list
        Song i = mSearchedSongs.get(position);

        if (i != null) {

            // initiate the TextViews
            mTitle = (TextView) v.findViewById(R.id.song_title3);
            mArtist = (TextView) v.findViewById(R.id.song_artist3);

            // if TextView is not null, set text according to Song i
            if (mTitle != null) {
                mTitle.setText(i.getTitle());
            }

            if (mArtist != null) {
                mArtist.setText(i.getArtist());
            }
        }
        return v;
    }
}


