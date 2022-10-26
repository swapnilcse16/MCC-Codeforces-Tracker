package com.example.mcc;


import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String NEWS_BASE_URL = "https://codeforces.com/api/user.status?";

    private static final String HANDLE = "handle";
    private static final String FROM = "from";
    private static final String COUNT = "count";

    private static final String API_KEY = "apiKey";


    public static String getNews(String handle) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String newsJsonString = null;


        try {
            Uri buildUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                    .appendQueryParameter(HANDLE, handle)
                    .appendQueryParameter(FROM, "1")
                    .appendQueryParameter(COUNT, "2000").build();

            URL requestedUrl = new URL(buildUri.toString());
            urlConnection = (HttpURLConnection) requestedUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            newsJsonString = builder.toString();
            Log.i("Ok", newsJsonString);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return newsJsonString;
    }
}
