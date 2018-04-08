package com.aqarmap.androidtask.Code.Threading;


import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONListing;
import com.aqarmap.androidtask.Code.Structures.Web.URLWithParams;
import com.aqarmap.androidtask.Code.Utilities.Network;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Mohammad Desouky on 03/04/2018.
 */

//AsyncTask<Params, Progress, Result>

public class ListingLoadingTask extends Task<Void, Void, JSONListing> implements IThreadTask<Void, Void, JSONListing>
{
    final static String PAGE_TO_LOAD_URL_PARAM_NAME = "PageToLoad";
    JSONListing myList;
    URL mURL;
    String mPageToLoad;

    public ListingLoadingTask(String PageToLoad, IThreadTask<Void, Void, JSONListing> CallBack)
    {
        super();
        mPageToLoad = PageToLoad;
        SetMainAction(this);
        AddAction(CallBack);
    }

    public ListingLoadingTask(URLWithParams SearchURL, IThreadTask<Void, Void, JSONListing> CallBack)
    {
        super();
        mURL = SearchURL.getURL();
        SetMainAction(this);
        AddAction(CallBack);
    }

    @Override
    public void BeforeRun()
    {

    }

    @Override
    public JSONListing Action(Void... Params)
    {
        return LoadListing();
    }

    @Override
    public void AfterRun(JSONListing Result)
    {

    }

    @Override
    public void OnProgress(Void[] Values)
    {

    }

    JSONListing LoadListing()
    {
        try
        {
            //updating the searchfilter
            URLWithParams url = AndroidTaskApp.getSearchFilter().getSearchURL(mPageToLoad);
            return new JSONListing(Network.GetResponseFromHttpUrl(url.getURL()));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return new JSONListing("");
    }
}