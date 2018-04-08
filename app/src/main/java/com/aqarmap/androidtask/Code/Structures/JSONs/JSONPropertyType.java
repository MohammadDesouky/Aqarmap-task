package com.aqarmap.androidtask.Code.Structures.JSONs;

import com.aqarmap.androidtask.Code.Utilities.JSON;

import org.json.JSONObject;

/**
 * Created by Mohammad Desouky on 01/04/2018.
 */


public class JSONPropertyType extends JSONTitledResult
{
    static final String JSON_PRIVATE = "private";
    boolean Private;

    public JSONPropertyType(JSONObject Object)
    {
        super(Object);
        Private = JSON.getBoolean(Object, JSON_PRIVATE, DEFAULT_BOOLEAN_VALUE);
    }

    public boolean IsPrivate()
    {
        return Private;
    }
}


//TODO: Implement on child of the JSON retured children of the propertyType
class Child
{

}
