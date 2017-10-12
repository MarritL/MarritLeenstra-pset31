package marrit.marritleenstra_pset31;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marrit on 8-10-2017.
 * Second HttpRequestHelper performing the actual searching on the database of Last.fm
 */

class HttpRequestHelper2 {

    static synchronized String downloadFromServer(String... parems) {

        // // retrieve search terms
        String chosenArtist = parems[0];
        String chosenTitle = parems[1];
        String result = "";

        // create the URL with the searchterms
        String urlString = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=" + "efe24c656585a96dea7a7e3ea770f3ab" + "&artist="+ chosenArtist + "&track=" + chosenTitle + "&format=json";


        // make the actual url object
        URL url = null;
        try {
            url = new URL(urlString);
        } catch(MalformedURLException e) {
            System.out.println("The url is not well formed");
        }


        // create a connection object
        HttpURLConnection connect;

        // connect to database and perform the search using your own url
        if(url != null) {
            try {
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");

                Integer responseCode = connect.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {

                    // create an inputStreamReader
                    BufferedReader bReader = new BufferedReader(
                            new InputStreamReader(connect.getInputStream()));

                    // safe inputStream in result
                    String line;
                    while ((line = bReader.readLine()) != null) {
                        result += line;
                    }
                    // add the artist and songtitle to the end of the result to use in TrackAsyncTask2
                    result += ",,,"+chosenArtist+"-"+chosenTitle;
                }
            } catch (IOException e ) {
                e.printStackTrace();
            }
        }
        return  result;
    }
}
