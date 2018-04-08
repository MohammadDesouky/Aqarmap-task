package com.aqarmap.androidtask.Code.Structures.JSONs;

import com.aqarmap.androidtask.Code.Utilities.JSON;

import org.json.JSONObject;

/**
 * Created by Mohammad Desouky on 01/04/2018.
 */

/**
 * Inhertice the JSON Result Class but this supposed to load an object with title attribute
 */
public class JSONTitledResult extends JSONResult
{


    static final String
            JSON_PROP_TITLE = "title",
            DefaultTitleValue = "";

    public JSONTitledResult(JSONObject Object)
    {
        super(Object);
        Title = JSON.getString(Object, JSON_PROP_TITLE, DefaultTitleValue);

    }

    /**
     * @return The Loaded Title Empty String If null or errors
     */
    public String GetTitle()
    {
        return Title;
    }

    public JSONObject toJSON()
    {
        return JSON.getObject("{" + JSON_ID + ":" + GetID() + "," + JSON_PROP_TITLE + ":'" + GetTitle() + "'}");
    }

    @Override
    public String toString()
    {
        return GetTitle().trim();
    }
}
