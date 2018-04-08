package com.aqarmap.androidtask.Code.Structures.JSONs;

import com.aqarmap.androidtask.Code.Utilities.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class JSONLocation extends JSONTitledResult
{
    static final String JSON_SLUG = "slug",
            JSON_SEARCHABLE = "searchable",
            JSON_CHILDREN = "children";
    String mSlug;
    boolean mSearchable;
    ArrayList<JSONLocation> mChildren;

    public JSONLocation(JSONObject Object)
    {
        super(Object);
        mSlug = JSON.getString(Object, JSON_SLUG, DEFAULT_STRING_VALUE);
        mSearchable = JSON.getBoolean(Object, JSON_SEARCHABLE, DEFAULT_BOOLEAN_VALUE);
        //loading children locations
        JSONArray JSONChildren = JSON.getJSONArray(Object, JSON_CHILDREN);
        if (JSONChildren != null && JSONChildren.length() > 0)
        {
            mChildren = new ArrayList<>();
            for (int i = 0; i < JSONChildren.length(); i++)
            {
                JSONObject location = JSON.getObjectFromArray(JSONChildren, i);
                if (location != null)
                    mChildren.add(new JSONLocation(location));
            }
        }
    }

    public boolean isSearchable()
    {
        return mSearchable;
    }

    public ArrayList<JSONLocation> getChildren()
    {
        return mChildren;
    }

    public String getSlug()
    {
        return mSlug;
    }

    @Override
    public String toString()
    {
        return GetTitle();
    }
}
