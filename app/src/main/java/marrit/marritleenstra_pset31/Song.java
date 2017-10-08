package marrit.marritleenstra_pset31;


import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Marrit on 24-9-2017.
 * Class that holds all the information about a song.
 */

public class Song implements Serializable {


    private UUID mId;
    private String mTitle;
    private String mArtist;
    private String mAlbum;
    private String mSummary;

    {
        //instance initializer; runs before any constructor
        mId = null;
        mTitle = "";
        mArtist = "";
        mAlbum = "";
        mSummary = "";
    }


    // initiate instance of Class with Id
    public Song() {
        mId = UUID.randomUUID();
    }

    // getters and setters for all fields
    public UUID getID() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
