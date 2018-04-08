package com.aqarmap.androidtask.Code.Utilities;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONLocation;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONPrice;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONSection;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONTitledResult;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class SharedPref
{

    static final String PREF_SECTIONS = "PropSectionsData",
            PREF_USER_SELECTED_SECTION = "SelectedSection",
            PREF_LOCATIONS = "PropLocationsData",
            PREF_USER_SELECTED_LOCATION = "SelectedLocation",
            PREF_PROP_TYPES = "PropTypesData",
            PREF_USER_SELECTED_TYPE = "SelectedType",
            PREF_PRICES = "PropPricesData",
            PREF_USER_SELCTED_MIN_PRICE = "SelectedMin",
            PREF_USER_SELCTED_MAX_PRICE = "SelectedMax";

    public static String getString(String Key)
    {
        return AndroidTaskApp.preferences.getString(Key, "");
    }

    public static void setString(String Key, String Value)
    {
        AndroidTaskApp.preferences.edit().putString(Key, Value).apply();
    }

    public static boolean Exists(String Key)
    {
        return AndroidTaskApp.preferences.contains(Key);
    }

    public static String getStoredSections()
    {
        return getString(PREF_SECTIONS);
    }

    public static String getStoredSelectedSection()
    {
        return getString(PREF_USER_SELECTED_SECTION);
    }

    public static String getStoredLocations()
    {
        return getString(PREF_LOCATIONS);
    }

    public static String getStoredSelectedLocation()
    {
        return getString(PREF_USER_SELECTED_LOCATION);
    }

    public static String getStoredPropTypes()
    {
        return getString(PREF_PROP_TYPES);
    }

    public static String getStoredSelectedPropType()
    {
        return getString(PREF_USER_SELECTED_TYPE);
    }

    public static String getStoredPrices()
    {
        return getString(PREF_PRICES);
    }

    public static String getStoredMinPrice()
    {
        return getString(PREF_USER_SELCTED_MIN_PRICE);
    }

    public static String getStoredMaxPrice()
    {
        return getString(PREF_USER_SELCTED_MAX_PRICE);
    }

    public static boolean DoIHaveValidFilterData()
    {
        return Exists(PREF_SECTIONS) &&
                Exists(PREF_LOCATIONS) &&
                Exists(PREF_PROP_TYPES) &&
                Exists(PREF_PRICES);
    }

    public static void savePropertiesSections(String jsonArray)
    {
        setString(PREF_SECTIONS, jsonArray);
    }

    public static void savePropertiesLocations(String jsonArray)
    {
        setString(PREF_LOCATIONS, jsonArray);
    }

    public static void savePropertiesTypes(String jsonArray)
    {
        setString(PREF_PROP_TYPES, jsonArray);
    }

    public static void savePropertiesPrices(String jsonArray)
    {
        setString(PREF_PRICES, jsonArray);
    }

    public static void saveSelectedPropertyType(JSONTitledResult type)
    {
        setString(PREF_USER_SELECTED_TYPE, type.toJSON().toString());
    }

    public static void saveSelectedLocation(JSONLocation location)
    {
        setString(PREF_USER_SELECTED_LOCATION, location.toJSON().toString());
    }

    public static void saveSelectedSection(JSONSection section)
    {
        setString(PREF_USER_SELECTED_SECTION, section.toJSON().toString());
    }

    public static void saveSeletedMinPrice(JSONPrice minPrice)
    {
        setString(PREF_USER_SELCTED_MIN_PRICE, minPrice.toJSON().toString());
    }

    public static void saveSelectedMaxPrice(JSONPrice maxPrice)
    {
        setString(PREF_USER_SELCTED_MAX_PRICE, maxPrice.toJSON().toString());
    }
}