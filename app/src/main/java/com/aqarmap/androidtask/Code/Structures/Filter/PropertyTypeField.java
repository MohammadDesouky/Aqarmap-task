package com.aqarmap.androidtask.Code.Structures.Filter;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONTitledResult;
import com.aqarmap.androidtask.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class PropertyTypeField extends FilterField<JSONTitledResult>
{

    static final String JSON_ARRAY_NAME = "property_types";

    public PropertyTypeField()
    {
        super(AndroidTaskApp.getAppContext().getString(R.string.property_type_url), false);
        MyJSONArrayName = JSON_ARRAY_NAME;
    }

    public PropertyTypeField(JSONArray jsonArray)
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

    public JSONTitledResult getDefault()
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
        LoadedOptions.add(new JSONTitledResult(object));
    }
}
