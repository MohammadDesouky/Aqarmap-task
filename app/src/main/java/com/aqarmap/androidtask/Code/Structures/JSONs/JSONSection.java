package com.aqarmap.androidtask.Code.Structures.JSONs;

import com.aqarmap.androidtask.Code.Utilities.JSON;

import org.json.JSONObject;

/**
 * Created by Mohammad Desouky on 01/04/2018.
 */

public class JSONSection extends JSONTitledResult
{
    static final String
            JSON_IS_MAIN = "main",
            JSON_IS_SEARCHABLE = "searchable";

    boolean IsMain, IsSearchable;
    JSONSection Section;

    public JSONSection(JSONObject Object)
    {
        super(Object);
        IsMain = JSON.getBoolean(Object, JSON_IS_MAIN, false);
        IsSearchable = JSON.getBoolean(Object, JSON_IS_SEARCHABLE, false);
        // Section=JSON.getJSONObject()
    }

    public boolean GetIsMain()
    {
        return IsMain;
    }

    public boolean GetIsSearchable()
    {
        return IsSearchable;
    }

    @Override
    public String toString()
    {
        return Title;
    }

    @Override
    public boolean equals(Object obj)
    {
        try
        {

        } catch (Exception ex)
        {

        }
        return false;
    }
}
