package com.aqarmap.androidtask;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.aqarmap.androidtask.Code.BroadcastRecievers.NetworkBroadcastReceiver;
import com.aqarmap.androidtask.Code.Controllers.SearchFilter;
import com.aqarmap.androidtask.Code.Interfaces.IConnectionChangeNotifier;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 01/04/2018.
 */

public class AndroidTaskApp extends Application
{
    public static SharedPreferences preferences;
    static ArrayList<IConnectionChangeNotifier> ConnectionListners;
    private static Context mContext;
    private static SearchFilter mSearchFilter = null;

    public static Context getAppContext()
    {
        return mContext;
    }

    public static SearchFilter getSearchFilter()
    {
        return mSearchFilter;
    }

    public static void SetSearchfilter(SearchFilter Filter)
    {
        if (Filter != null)
            mSearchFilter = Filter;
    }

    public static void registerConnectionListner(IConnectionChangeNotifier notifier)
    {
        if (!ConnectionListners.contains(notifier))
        {
            ConnectionListners.add(notifier);
        }

    }

    public static void unRegisterConnectionListner(IConnectionChangeNotifier notifier)
    {

        if (ConnectionListners.contains(notifier))
        {
            ConnectionListners.remove(notifier);
        }
    }

    public static void OnConnected()
    {
        for (IConnectionChangeNotifier notifier : ConnectionListners)
            try
            {
                notifier.OnConnected();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
    }

    public static void onDisconnected()
    {
        for (IConnectionChangeNotifier notifier : ConnectionListners)
            try
            {
                notifier.onDisconnected();

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
    }

    public void onCreate()
    {
        super.onCreate();
        mContext = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        ConnectionListners = new ArrayList<>();
       /* //for not supported SVG might some images with not supported svg formats displays nothing
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);*/

        //ANDROID DOCS
        //Apps targeting Android 7.0 (API level 24) and higher do not receive CONNECTIVITY_ACTION broadcasts
        // if they declare the broadcast receiver in their manifest. Apps will still receive CONNECTIVITY_ACTION
        // broadcasts if they register their BroadcastReceiver with Context.registerReceiver() and that context is still valid.
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new NetworkBroadcastReceiver(), intentFilter);
    }
}
