package marrit.marritleenstra_pset31;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marrit on 2-10-2017.
 */

public class HttpRequestHelper {

    protected static synchronized String downloadFromServer(String... parems) {
        //String result = "";
        String chosenTag = parems[0];
        String method = parems[1];
        String result = method;

        // creeer een geldinge url met je API en de gegeven zoekterm van de gebruiker
        //String urlString = "http://ws.audioscrobbler.com/2.0/" + "?method=track.search&track=" + chosenTag + "&api_key=" + "efe24c656585a96dea7a7e3ea770f3ab"+ "&format=json";
        String urlString = "http://ws.audioscrobbler.com/2.0/" + "?method=" + method + chosenTag + "&api_key=" + "efe24c656585a96dea7a7e3ea770f3ab"+ "&format=json";

        //maak van je url een URL object
        URL url = null;
        try {
            url = new URL(urlString);
        } catch(MalformedURLException e) {
            System.out.println("The url is not well formed");
        }


        // create a connection object
        HttpURLConnection connect;

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
                }
            } catch (IOException e ) {
                e.printStackTrace();
            }
        }
        return  result;
    }
}
