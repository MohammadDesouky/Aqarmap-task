package com.aqarmap.androidtask.Code.Structures.JSONs;

import com.aqarmap.androidtask.Code.Utilities.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class JSONUser extends JSONResult
{
    static final String JSON_E_MAIL = "username",
            JSON_FULL_NAME = "full_name";
    String Email, FullName;
    ArrayList<JSONPhone> Phones;

    public JSONUser(JSONObject Object)
    {
        super(Object);
        Phones = new ArrayList<>();
        Email = JSON.getString(Object, JSON_E_MAIL, DEFAULT_STRING_VALUE);
        FullName = JSON.getString(Object, JSON_FULL_NAME, DEFAULT_STRING_VALUE);
    }

    public void UpdatePhones(JSONArray jsonArray)
    {
        if (jsonArray == null) return;
        for (int i = 0; i < jsonArray.length(); i++)
        {
            Phones.add(new JSONPhone(JSON.getObjectFromArray(jsonArray, i)));
        }
    }

    public String getPhone()
    {
        if (Phones.size() > 0)
            return Phones.get(0).Number;
        return "";
    }
}
