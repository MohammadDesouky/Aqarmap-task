package com.aqarmap.androidtask.Code.Structures.Filter;

import android.text.TextUtils;

import com.aqarmap.androidtask.Code.Threading.IThreadTask;
import com.aqarmap.androidtask.Code.Threading.Task;
import com.aqarmap.androidtask.Code.Utilities.JSON;
import com.aqarmap.androidtask.Code.Utilities.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 01/04/2018.
 */

public class FilterField<T> extends Task<Void, Void, JSONArray> implements IThreadTask<Void, Void, JSONArray>
{
    ArrayList<T> LoadedOptions;
    String MyJSONArrayName;
    String DataSourceURL;
    Boolean IsOptional;
    JSONArray jsonArray;

    /**
     * Default contstuctor
     */

    public FilterField()
    {
        LoadedOptions = new ArrayList<T>();
        DataSourceURL = "";
    }

    public FilterField(JSONArray Object)
    {
        LoadedOptions = new ArrayList<T>();
        DataSourceURL = "";
        jsonArray = Object;
    }

    /**
     * Constructs the object with the sent URL, isOptional and creates empty options
     * then load the data from the given url must be run in a thread
     *
     * @param dataSourceURL
     * @param isOptional    specifiy Either the field is optional or mandatory
     */
    public FilterField(String dataSourceURL, boolean isOptional)
    {
        DataSourceURL = dataSourceURL;
        IsOptional = isOptional;
        LoadedOptions = new ArrayList<T>();
    }

    public void LoadFromWeb(IThreadTask<Void, Void, JSONArray> Callback)
    {
        SetMainAction(this);
        AddAction(Callback);
        execute();
    }

    /**
     * Clears the options and reload it again from the field url
     */
    public void ReloadOptions() throws Exception
    {
        if (jsonArray != null)
        {
            LoadedOptions.clear();
            for (int i = 0; i < jsonArray.length(); i++)
                addToOptions(JSON.getObjectFromArray(jsonArray, i));
        }
    }

    public void SaveToSharedPrefrences()
    {

    }

    public void LoadFromSharedPrefrences()
    {

    }

    /**
     * MUST BE OVERRIDE WITH GENEREIC TO CREATE ANEW GENERIC TYPE then put in in the loaded options list
     *
     * @param object
     */
    public void addToOptions(JSONObject object) throws Exception
    {
        throw new Exception("THIS METHOD MUST BE OVERRID");
    }

    /**
     * Get the loaded options for that field
     *
     * @return the Options list
     * Note: It's might be NULL ^_^
     */
    public ArrayList<T> Options()
    {
        return LoadedOptions;
    }


    public JSONArray LoadOnlineJSON()
    {
        if (!TextUtils.isEmpty(MyJSONArrayName))
        {
            try
            {
                JSONObject loadedObject = JSON.getObject(Network.GetResponseFromHttpUrl(new URL(DataSourceURL)));
                return JSON.getJSONArray(loadedObject, MyJSONArrayName);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    //background methods implementations

    @Override
    public void BeforeRun()
    {

    }

    @Override
    public JSONArray Action(Void... Params)
    {
        return LoadOnlineJSON();
    }

    @Override
    public void AfterRun(JSONArray Result)
    {
        jsonArray = Result;
        try
        {
            ReloadOptions();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void OnProgress(Void[] Values)
    {

    }

    public ArrayList<T> getLoadedOptions()
    {
        return LoadedOptions;
    }

    public String getJSONArray()
    {
        return jsonArray.toString();
    }
}
