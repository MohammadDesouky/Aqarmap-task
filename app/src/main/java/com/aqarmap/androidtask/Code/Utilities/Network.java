package com.aqarmap.androidtask.Code.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.aqarmap.androidtask.AndroidTaskApp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by Mohammad Desouky on 31/03/2018.
 */

public class Network
{


    public static final int
            NETWORK_STATUS_NOT_CONNECTED = 0,
            NETWORK_STAUS_WIFI = 1,
            NETWORK_STATUS_MOBILE = 2;
    //connectivity
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String GetResponseFromHttpUrl(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try
        {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)
            {
                String data = scanner.next();
                Log.d("NETWORK", data);
                return data;
            } else
            {
                return null;
            }
        } finally
        {
            urlConnection.disconnect();
        }
    }

    public static Bitmap LoadImage(String URL)
    {
        Bitmap bitmap = null;
        InputStream in = null;
        try
        {
            //FOR TEST ONLY
            //https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.y1DsT9culVibbxNcXhHAxAHaEK%26pid%3D15.1&f=1
            // URL="https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.y1DsT9culVibbxNcXhHAxAHaEK%26pid%3D15.1&f=1";
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            in.close();
        } catch (IOException e1)
        {
        }

        return bitmap;
    }

    static InputStream OpenHttpConnection(String strURL) throws IOException
    {
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try
        {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                inputStream = httpConn.getInputStream();
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return inputStream;
    }

    public static int getConnectivityStatus(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork)
        {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatusString(Context context)
    {
        int conn = getConnectivityStatus(context);
        int status = 0;
        if (conn == TYPE_WIFI)
        {
            status = NETWORK_STAUS_WIFI;
        } else if (conn == TYPE_MOBILE)
        {
            status = NETWORK_STATUS_MOBILE;
        } else if (conn == TYPE_NOT_CONNECTED)
        {
            status = NETWORK_STATUS_NOT_CONNECTED;
        }
        return status;
    }

    public static boolean areWeConnected()
    {
        return getConnectivityStatus(AndroidTaskApp.getAppContext()) != NETWORK_STATUS_NOT_CONNECTED;
    }
}
