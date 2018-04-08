package com.aqarmap.androidtask.Code.Structures.Filter;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONPrice;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONSection;
import com.aqarmap.androidtask.Code.Threading.IThreadTask;
import com.aqarmap.androidtask.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 01/04/2018.
 */

public class SectionField extends FilterField<JSONSection>
{
    static final String JSON_ARRAY_NAME = "sections";
    MinMaxPrices minMaxPrices;
    JSONSection FilterSection;

    public SectionField()
    {
        super(AndroidTaskApp.getAppContext().getString(R.string.section_url), false);
        MyJSONArrayName = JSON_ARRAY_NAME;
        minMaxPrices = new MinMaxPrices();
    }

    public SectionField(JSONArray jsonArray, JSONArray PricesArray)
    {
        super(jsonArray);
        minMaxPrices = new MinMaxPrices(PricesArray);
        IsOptional = false;
        try
        {
            ReloadOptions();
            minMaxPrices.ReloadOptions();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public JSONSection getDefault()
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
        LoadedOptions.add(new JSONSection(object));
    }

    @Override
    public void LoadFromWeb(IThreadTask<Void, Void, JSONArray> Callback)
    {
        super.LoadFromWeb(Callback);
        minMaxPrices.LoadFromWeb(Callback);
    }

    public ArrayList<String> OptionsToArrayListString()
    {
        ArrayList<String> output = new ArrayList<>();
        for (JSONSection f : LoadedOptions)
            output.add(f.GetTitle());
        return output;
    }

    public FilterField<JSONPrice> getPrices()
    {
        return minMaxPrices;
    }

    public void UpdateSelectedSection(JSONSection selectedSection)
    {
        FilterSection = selectedSection;
    }

    public ArrayList<JSONPrice> getMinPrices()
    {
        minMaxPrices.updatePrices(FilterSection);
        return minMaxPrices.getMinPrices();
    }

    public ArrayList<JSONPrice> getMaxPrices(JSONPrice Selected)
    {
        if (minMaxPrices.getMaxPrices().size() == 0)
            minMaxPrices.updatePrices(FilterSection);

        minMaxPrices.updateMaxPrices(Selected);
        return minMaxPrices.getMaxPrices();
    }

    public JSONPrice getDefaultPrice()
    {
        if (getMinPrices() != null && getMinPrices().size() > 0)
            return getMinPrices().get(0);
        return null;

    }

}
