package com.aqarmap.androidtask.Code.Utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.aqarmap.androidtask.AndroidTaskApp;

/**
 * Created by Mohammad Desouky on 05/04/2018.
 */

public class Contact
{

    public static void Call(String Number, Context ActContext)
    {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Number));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActContext.startActivity(callIntent);
    }

    public static void Call(String Number)
    {
        Call(Number, AndroidTaskApp.getAppContext());
    }

    public static void OpenSendingMail(String ToEmail, String Subject, String Body)
    {
        OpenSendingMail(ToEmail, Subject, Body, AndroidTaskApp.getAppContext());
    }

    public static void OpenSendingMail(String ToEmail, String Subject, String Body, Context ActContext)
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{ToEmail});
        i.putExtra(Intent.EXTRA_SUBJECT, Subject);
        i.putExtra(Intent.EXTRA_TEXT, Body);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try
        {
            ActContext.startActivity(Intent.createChooser(i, "Send mail..."));

        } catch (android.content.ActivityNotFoundException ex)
        {
            //ToDO Show a proper message to inform the user that mobile has no default mailing app
            Toast.makeText(ActContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void SendSMS(String Number, String Message)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + Number));
        intent.putExtra("sms_body", Message);
        AndroidTaskApp.getAppContext().startActivity(intent);
    }
}
