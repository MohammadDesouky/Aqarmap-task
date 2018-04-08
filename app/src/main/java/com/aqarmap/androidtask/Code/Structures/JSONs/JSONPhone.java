package com.aqarmap.androidtask.Code.Structures.JSONs;

import com.aqarmap.androidtask.Code.Utilities.JSON;

import org.json.JSONObject;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class JSONPhone extends JSONResult
{
    static final String JSON_NUMBER = "number";
    String Number;

    public JSONPhone(JSONObject Object)
    {
        super(Object);
        Number = JSON.getString(Object, JSON_NUMBER, DEFAULT_STRING_VALUE);
    }
}
