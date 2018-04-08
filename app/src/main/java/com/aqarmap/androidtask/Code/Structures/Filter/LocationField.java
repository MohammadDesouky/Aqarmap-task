package com.aqarmap.androidtask.Code.Structures.Filter;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONLocation;
import com.aqarmap.androidtask.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class LocationField extends FilterField<JSONLocation>
{
    static final String JSON_ARRAY_NAME = "locations";

    public LocationField()
    {
        super(AndroidTaskApp.getAppContext().getString(R.string.location_url), false);
        MyJSONArrayName = JSON_ARRAY_NAME;
    }

    public LocationField(JSONArray jsonArray)
    {
        super(jsonArray);
        IsOptional = false;
        try
        {
            ReloadOptions();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public JSONLocation getDefault()
    {
        if (LoadedOptions != null && LoadedOptions.size() > 0)
            return LoadedOptions.get(0);
        return null;
    }

    @Override
    public void addToOptions(JSONObject object)
    {
        if (LoadedOptions == null)
            LoadedOptions = new ArrayList<>();
        LoadedOptions.add(new JSONLocation(object));
    }

}
