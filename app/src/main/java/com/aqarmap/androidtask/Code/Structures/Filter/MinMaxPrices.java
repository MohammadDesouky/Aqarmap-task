package com.aqarmap.androidtask.Code.Structures.Filter;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONPrice;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONSection;
import com.aqarmap.androidtask.Code.Utilities.JSON;
import com.aqarmap.androidtask.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class MinMaxPrices extends FilterField<JSONPrice>
{
    static final String JSON_ARRAY_NAME = "price_filters";
    /**
     * default constructor loads data from internet
     */
    ArrayList<JSONPrice> MinPrices, MaxPrices;

    public MinMaxPrices()
    {
        super(AndroidTaskApp.getAppContext().getString(R.string.price_url), true);
        MyJSONArrayName = JSON_ARRAY_NAME;
        MinPrices = new ArrayList<>();
        MaxPrices = new ArrayList<>();
    }

    public MinMaxPrices(JSONArray jsonArray)
    {
        super(jsonArray);
        IsOptional = true;
        MinPrices = new ArrayList<>();
        MaxPrices = new ArrayList<>();
    }

    public JSONPrice getDefault()
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
        LoadedOptions.add(new JSONPrice(object));
    }

    public ArrayList<JSONPrice> getMinPrices()
    {
        return MinPrices;
    }

    public ArrayList<JSONPrice> getMaxPrices()
    {
        return MaxPrices;
    }

    public void updatePrices(JSONSection Section)
    {
        MinPrices.clear();
        MinPrices.add(new JSONPrice(JSON.getObject("{id:-1,value:0}")));
        MaxPrices.clear();
        MaxPrices.add(new JSONPrice(JSON.getObject("{id:-1,value:0}")));
        for (JSONPrice p : LoadedOptions)
        {
            if (Section == null || Section.GetID() == p.getSectionID())
            {
                MinPrices.add(p);
                MaxPrices.add(p);
            }
        }
    }

    public void updateMaxPrices(JSONPrice SelectedMinPrice)
    {
        MaxPrices.clear();
        MaxPrices.add(new JSONPrice(JSON.getObject("{id:-1,value:0}")));
        for (JSONPrice p : MinPrices)
        {
            if (SelectedMinPrice == null || p.getValue() >= SelectedMinPrice.getValue() && p.getValue() > 0)
                MaxPrices.add(p);
        }
    }
}
