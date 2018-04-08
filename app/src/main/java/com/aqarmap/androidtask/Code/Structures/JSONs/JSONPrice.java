package com.aqarmap.androidtask.Code.Structures.JSONs;

import android.support.annotation.NonNull;

import com.aqarmap.androidtask.Code.Utilities.JSON;
import com.aqarmap.androidtask.Code.Utilities.Strings;

import org.json.JSONObject;

import java.util.Comparator;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class JSONPrice extends JSONResult implements Comparable<JSONPrice>
{
    final static String JSON_VALUE = "value",
            JSON_SECTION_ID = "id",
            JSON_SECTION = "section";
    int Value, SectionID;

    public JSONPrice(JSONObject Object)
    {
        super(Object);
        Value = JSON.getInt(Object, JSON_VALUE, DEFAULT_INT_VALUE);
        SectionID = JSON.getInt(JSON.getJSONObject(Object, JSON_SECTION), JSON_SECTION_ID, DEFAULT_INT_VALUE);
    }

    public int getSectionID()
    {
        return SectionID;
    }

    //for sorting purposees
    @Override
    public int compareTo(@NonNull JSONPrice o)
    {
        int x = 0;
        x = Value < o.getValue() ? -1 : x;
        x = Value > o.getValue() ? 1 : x;
        return x;
    }

    public int getValue()
    {
        return Value;
    }

    @Override
    public String toString()
    {
        return Value > 0 ? Strings.toMoneyFormat((double) Value) : "None";
    }

    public JSONObject toJSON()
    {
        return JSON.getObject("{" + JSON_ID + ":" + GetID() + "," + JSON_VALUE + ":" + Value + "}");
    }
}

//for sorting purposees
class JSONPriceComparator implements Comparator<JSONPrice>
{
    public int compare(JSONPrice left, JSONPrice right)
    {
        return left.compareTo(right);
    }
}

