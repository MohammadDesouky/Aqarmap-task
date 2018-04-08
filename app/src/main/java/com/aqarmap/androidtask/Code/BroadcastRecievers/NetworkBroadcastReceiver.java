package com.aqarmap.androidtask.Code.BroadcastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Utilities.Network;

/**
 * Created by Mohammad Desouky on 07/04/2018.
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(final Context context, final Intent intent)
    {

        int status = Network.getConnectivityStatusString(context);
        // if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()))
        {
            if (status == Network.NETWORK_STATUS_NOT_CONNECTED)
            {
                AndroidTaskApp.onDisconnected();
            } else
            {
                AndroidTaskApp.OnConnected();
            }

        }
    }
}