package com.aqarmap.androidtask.Code.Structures.JSONs;

import com.aqarmap.androidtask.Code.Utilities.JSON;
import com.aqarmap.androidtask.Code.Utilities.Strings;

import org.json.JSONObject;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class JSONPropertyAttribute extends JSONResult
{
    static final String
            JSON_NAME = "name",
            JSON_VALUE = "value",
            JSON_TYPE = "type",
            JSON_CUSTOM_FIELD = "custom_field";
    String Value, Name, Type;

    public JSONPropertyAttribute(JSONObject Object)
    {
        super(Object);
        Value = JSON.getString(Object, JSON_VALUE, DEFAULT_STRING_VALUE);
        JSONObject customField = JSON.getJSONObject(Object, JSON_CUSTOM_FIELD);
        Name = JSON.getString(customField, JSON_NAME, DEFAULT_STRING_VALUE);
        Type = JSON.getString(customField, JSON_TYPE, DEFAULT_STRING_VALUE);
    }

    public String getName()
    {
        return Strings.Capitalize(Name);
    }

    public String getValue()
    {
       /* if(Name.toLowerCase().equals("year-built"))
        {
            try
            {
                return Integer.parseInt(Value)+"";
            }
            catch(NumberFormatException NFE)
            {
                NFE.printStackTrace();
                return "";
            }
        }*/
        if (Value.toLowerCase().equals("unknown"))
            return "";
        return Value;
    }

    public boolean isValidAttribute()
    {
        return Name.length() > 0 && getValue().length() > 0;
    }
}
