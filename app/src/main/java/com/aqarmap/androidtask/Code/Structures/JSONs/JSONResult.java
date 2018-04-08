package com.aqarmap.androidtask.Code.Structures.JSONs;

import com.aqarmap.androidtask.Code.Utilities.JSON;

import org.json.JSONObject;

/**
 * Created by Mohammad Desouky on 01/04/2018.
 */

public class JSONResult
{

    //Default Values for null or error json assignments
    protected static final int DEFAULT_INT_VALUE = -1;
    protected static final String DEFAULT_STRING_VALUE = "";
    protected static final boolean DEFAULT_BOOLEAN_VALUE = false;
    protected static final Object DEFAULT_OBJECT_VALUE = null;
    protected static final double DEFAULT_DOUBLE_VALUE = -Double.MIN_VALUE;


    static final String
            JSON_ID = "id";
    int id;
    String Title;

    public JSONResult(JSONObject Object)
    {
        id = JSON.getInt(Object, JSON_ID, DEFAULT_INT_VALUE);
    }

    /**
     * @return The loaded ID, -1 if null or error occurs
     */
    public int GetID()
    {
        return id;
    }

}
