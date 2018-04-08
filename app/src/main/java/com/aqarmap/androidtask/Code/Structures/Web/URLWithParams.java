package com.aqarmap.androidtask.Code.Structures.Web;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mohammad Desouky  on 03/04/2018.
 */

public class URLWithParams
{
    ArrayList<Param> Params;
    String mBaseURL;

    public URLWithParams(String URL)
    {
        mBaseURL = URL;
        Params = new ArrayList<Param>();
    }

    public URLWithParams(String URL, ArrayList<Param> Params)
    {
        mBaseURL = URL;
        this.Params = Params;
    }

    public String getBaseURL()
    {
        return getBaseURL();
    }

    public void AddParam(String name, String value)
    {
        if (Params != null)
            Params.add(new Param(name, value));

    }

    public ArrayList<Param> getParams()
    {
        return Params;
    }

    public URL getURL()
    {
        Uri builtUri = Uri.parse(mBaseURL);
        Uri.Builder b = builtUri.buildUpon();
        if (Params != null)
            for (URLWithParams.Param p : Params)
            {
                b = b.appendQueryParameter(p.Name, p.Value);
            }
        URL url = null;
        try
        {
            url = new URL(b.toString());
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return url;
    }

    public class Param
    {
        public String Name, Value;

        public Param(String name, String value)
        {
            Name = name;
            Value = value;
        }
    }

}