package com.aqarmap.androidtask.Code.Structures.JSONs;

import com.aqarmap.androidtask.Code.Utilities.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 03/04/2018.
 */

public class JSONListing extends JSONResult

{

        /*
        * current_page_number	"1"
    num_items_per_page	10
    items	[…]
    total_count	200
    page_range	5*/

    final static String
            JSON_CURRENT_PAGE = "current_page_number",
            JSON_ITEMS_PER_PAGE = "num_items_per_page",
            JSON_ITEMS = "items",
            JSON_TOTAL_COUNT = "total_count",
            JSON_PAGE_RANGE = "page_range",
            JSON_LISTINGS = "listings";

    String mPageNumber;
    int mItemsPerPage, mTotalCount, mPageRange;
    ArrayList<JSONProperty> Properties;
    JSONObject mObject;

    public JSONListing(String Object)
    {
        super(null);
        Properties = new ArrayList<>();
        mObject = JSON.getObject(Object);
        mObject = JSON.getJSONObject(mObject, JSON_LISTINGS);
        LoadData();
    }


    public JSONListing(JSONObject object)
    {
        super(null);
        Properties = new ArrayList<>();
        mObject = object;
        LoadData();
    }

    private void LoadData()
    {
        mPageNumber = JSON.getString(mObject, JSON_CURRENT_PAGE, DEFAULT_STRING_VALUE);
        mItemsPerPage = JSON.getInt(mObject, JSON_ITEMS_PER_PAGE, DEFAULT_INT_VALUE);
        mTotalCount = JSON.getInt(mObject, JSON_TOTAL_COUNT, DEFAULT_INT_VALUE);
        mPageRange = JSON.getInt(mObject, JSON_PAGE_RANGE, DEFAULT_INT_VALUE);

        //loading properties
        JSONArray items = JSON.getJSONArray(mObject, JSON_ITEMS);
        if (items != null)
            if (items.length() > 0)
            {
                for (int i = 0; i < items.length(); i++)
                {
                    JSONProperty p = new JSONProperty(JSON.getObjectFromArray(items, i));
                    //ToDO now I replaced my download logic with glide for image caching and background loading I think it will be better to implement ours later
                    //the loading logic comes in on bind in the recycler view
                    //also i can easily download in background with my threading logic
                    //p.DownloadMainPhoto();
                    Properties.add(p);
                }
            }
    }

    public int getPropertyTotalCount()
    {
        return mTotalCount;
    }

    public ArrayList<JSONProperty> getProperties()
    {
        return Properties;
    }

    public void Clear()
    {
        Properties.clear();
    }
}
