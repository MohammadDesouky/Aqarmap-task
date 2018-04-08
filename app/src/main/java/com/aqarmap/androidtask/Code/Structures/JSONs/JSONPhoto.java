package com.aqarmap.androidtask.Code.Structures.JSONs;

import com.aqarmap.androidtask.Code.Utilities.JSON;

import org.json.JSONObject;

/**
 * Created by Mohammad Desouky on 01/04/2018.
 */

public class JSONPhoto extends JSONResult
{
    static final String JSON_CAPTION = "caption",
            JSON_TYPE = "type",
            JSON_THUMBS = "thumbnails",
            JSON_SMALL_THUMB = "small",
            JSON_LARGE_THUMB = "large",
            JSON_FILE = "file";

    String Caption,
            Type,
            SmallThumb,
            LargeThumb;
    int FileID;
    JSONObject FileObject, ThumbsObject;

    public JSONPhoto(JSONObject Object)
    {
        super(Object);
        Caption = JSON.getString(Object, JSON_CAPTION, DEFAULT_STRING_VALUE);
        Type = JSON.getString(Object, JSON_TYPE, DEFAULT_STRING_VALUE);
        FileObject = JSON.getJSONObject(Object, JSON_FILE);
        FileID = JSON.getInt(FileObject, JSON_ID, DEFAULT_INT_VALUE);
        ThumbsObject = JSON.getJSONObject(FileObject, JSON_THUMBS);
        SmallThumb = JSON.getString(ThumbsObject, JSON_SMALL_THUMB, DEFAULT_STRING_VALUE);
        LargeThumb = JSON.getString(ThumbsObject, JSON_LARGE_THUMB, DEFAULT_STRING_VALUE);
    }

    public String getLarge()
    {
        return LargeThumb;
    }

    public String getSmall()
    {
        return SmallThumb;
    }

    public String getCaption()
    {
        return Caption;
    }
}
