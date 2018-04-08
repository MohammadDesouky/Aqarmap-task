package com.aqarmap.androidtask.Code.Structures.JSONs;

import android.graphics.Bitmap;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Utilities.JSON;
import com.aqarmap.androidtask.Code.Utilities.Network;
import com.aqarmap.androidtask.Code.Utilities.Strings;
import com.aqarmap.androidtask.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mohammad Desouky on 01/04/2018.
 */

public class JSONProperty extends JSONTitledResult
{


    //JSON attributes names assignment
    static final String
            JSON_PHOTOS = "photos",                       //obj
            JSON_MAIN_PHOTO = "main_photo",               //obj
    //id and title are inherted you will find id in the JSONResult & Title in JSONTitledResult
    JSON_PARENT = "parent",                       //int --DONE
            JSON_DESCRIPTION = "description",             //string --DONE
            JSON_SECTION = "section",                     //obj --DONE
            JSON_AREA = "area",                          //double --DONE
            JSON_PRICE = "price",                        //double --DONE
            JSON_ADDRESS = "address",                   //string --DONE
            JSON_LOCATION_LATITUDE = "center_lat",      //double --DONE
            JSON_LOCATION_LONGITUDE = "center_lng",    //double --DONE
            JSON_STATUS = "status",                    //int --DONE
            JSON_PROPERTY_VIEW = "property_view",      //int --DONE
            JSON_PAYMENT_METHOD = "payment_method",    //int --DONE
            JSON_VIDEO_URL = "video_url",              //string --DONE
            JSON_PUBLISHED_DATE = "published_at",      //string as dateTime sample 2016-05-04T08:33:22+0000 --DONE
            JSON_PROPERTY_TYPE = "property_type",      //obj
            JSON_LOCATION = "location",                //obj
            JSON_USER = "user",                        //obj
            JSON_ATTRIBUTES = "attributes",           //obj
            JSON_PHONES = "phones";                   //obj
    public Bitmap MyImage;
    int Parent, Status, PropertyView, PaymentMethod;
    double Area, Price, Latitude, Longitude;
    String Description, Address, VideoURL;
    Date PublishedTime;
    JSONSection Section;
    ArrayList<JSONPhoto> Photos;
    ArrayList<JSONPropertyAttribute> Attributes;
    JSONPhoto MainPhoto;
    JSONLocation Location;
    JSONUser User;
    JSONPropertyType PropertyType;
    JSONObject mJSONObj;

    public JSONProperty(JSONObject Object)
    {
        super(Object);
        mJSONObj = Object;
        //assign All integers
        Parent = JSON.getInt(Object, JSON_PARENT, DEFAULT_INT_VALUE);
        Status = JSON.getInt(Object, JSON_STATUS, DEFAULT_INT_VALUE);
        PropertyView = JSON.getInt(Object, JSON_PROPERTY_VIEW, DEFAULT_INT_VALUE);
        PaymentMethod = JSON.getInt(Object, JSON_PAYMENT_METHOD, DEFAULT_INT_VALUE);

        //Assign Doubles
        Area = JSON.getDouble(Object, JSON_AREA, DEFAULT_DOUBLE_VALUE);
        Price = JSON.getDouble(Object, JSON_PRICE, DEFAULT_DOUBLE_VALUE);
        Latitude = JSON.getDouble(Object, JSON_LOCATION_LATITUDE, DEFAULT_DOUBLE_VALUE);
        Longitude = JSON.getDouble(Object, JSON_LOCATION_LONGITUDE, DEFAULT_DOUBLE_VALUE);

        //Assign Strings
        Description = JSON.getString(Object, JSON_DESCRIPTION, DEFAULT_STRING_VALUE);
        Address = JSON.getString(Object, JSON_ADDRESS, DEFAULT_STRING_VALUE);
        VideoURL = JSON.getString(Object, JSON_VIDEO_URL, DEFAULT_STRING_VALUE);
        //published date
        PublishedTime = Strings.AtomDateFromString(JSON.getString(Object, JSON_PUBLISHED_DATE, DEFAULT_STRING_VALUE));


        // Assign Objects
        Section = new JSONSection(JSON.getJSONObject(Object, JSON_SECTION));
        MainPhoto = new JSONPhoto(JSON.getJSONObject(Object, JSON_MAIN_PHOTO));
        Location = new JSONLocation(JSON.getJSONObject(Object, JSON_LOCATION));
        User = new JSONUser(JSON.getJSONObject(Object, JSON_USER));
        //apped phones to the user
        User.UpdatePhones(JSON.getJSONArray(Object, JSON_PHONES));
        PropertyType = new JSONPropertyType(JSON.getJSONObject(Object, JSON_PROPERTY_TYPE));
        //Property photos
        Photos = new ArrayList<>();
        JSONArray JSONPhotos = JSON.getJSONArray(Object, JSON_PHOTOS);
        if (JSONPhotos != null)
        {
            for (int i = 0; i < JSONPhotos.length(); i++)
            {
                Photos.add(new JSONPhoto(JSON.getObjectFromArray(JSONPhotos, i)));
            }
        }
        //Property Attributes
        Attributes = new ArrayList<>();
        JSONArray JSONAttributes = JSON.getJSONArray(Object, JSON_ATTRIBUTES);
        if (JSONAttributes != null)
        {
            for (int i = 0; i < JSONAttributes.length(); i++)
            {
                Attributes.add(new JSONPropertyAttribute(JSON.getObjectFromArray(JSONAttributes, i)));
            }
        }
    }

    //
    public String getFormattedPrice()
    {
        String Currency = AndroidTaskApp.getAppContext().getResources().getString(R.string.egypt_currency);
        return Strings.toMoneyFormat(Price) + " " + Currency;
    }

    public String getAreaWithUnit()
    {
        String Unit = AndroidTaskApp.getAppContext().getResources().getString(R.string.area_unit);
        if (Area > 0)
            return (int) Area + " " + Unit;
        return "";
    }

    public String getFormattedArea()
    {

        return "Area: " + getAreaWithUnit();
    }

    public JSONPhoto getMainPhoto()
    {
        return MainPhoto;
    }

    public JSONObject getJSONObject()
    {
        return mJSONObj;
    }

    public String getFormattedMedia()
    {
        String r = Photos != null && Photos.size() > 0 ? "Has " + (Photos.size() + 1) + " Photos" : "Has 1 Photo";
        r += r == "" && VideoURL != DEFAULT_STRING_VALUE ? "Has 1 Video" : "";//if no photos and there is a video
        r += !r.equals("Has 1 Video") && VideoURL != DEFAULT_STRING_VALUE ? " and 1 Video" : "";
        return r;
    }

    public String getFormattedTitle()
    {
        return getFormattedPrice() + "\n" + getFormattedArea();
    }

    public String getFormattedLocation()
    {
        return "Location: " + Location.GetTitle();
    }

    /**
     * you have to run this method in a background thread!
     */
    public void DownloadMainPhoto()
    {
        MyImage = Network.LoadImage(getMainPhoto().LargeThumb);
    }

    public boolean HasValidLocationOnMap()
    {
        //I Think the null location stored as this becuase there is a negative values for longitude and latitude
        return Latitude != 0 && Longitude != 0;
    }

    public double getLongitude()
    {
        return Longitude;
    }

    public double getLatitude()
    {
        return Latitude;
    }

    public String getOfferType()
    {
        return Section.GetTitle();
    }

    public String getAddress()
    {
        return Address;
    }

    public String getPublishedDate()
    {
        if (PublishedTime != null)
            return new SimpleDateFormat("dd MMM yyyy").format(PublishedTime);
        return "";
    }

    public String getDescription()
    {
        return Description;
    }

    public ArrayList<JSONPropertyAttribute> getAttributes()
    {
        return Attributes;
    }

    public ArrayList<JSONPhoto> getPhotos()
    {
        return Photos;
    }

    public String getPhone()
    {
        return User.getPhone();
    }

    public String getMail()
    {
        return User.Email;
    }
}
