package com.aqarmap.androidtask.Code.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohammad Desouky on 31/03/2018.
 */

public class JSON
{


    /**
     * trying to read an integer value from a JSON object in a given attribute
     *
     * @param Object              the json object
     * @param AttributeName       the attribute name in the json object
     * @param DefaultValueForNull the default value to return if an error occurs
     * @return the read value or DefaultValueForNull sent value if an error occurs
     */
    public static int getInt(JSONObject Object, String AttributeName, int DefaultValueForNull)
    {
        try
        {
            return Object.getInt(AttributeName);
        } catch (JSONException e)
        {
            //e.printStackTrace();
        }
        //catch if the object is null
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return DefaultValueForNull;
    }

    /**
     * trying to read an double value from a JSON object in a given attribute
     *
     * @param Object              the json object
     * @param AttributeName       the attribute name in the json object
     * @param DefaultValueForNull the default value to return if an error occurs
     * @return the read value or DefaultValueForNull sent value if an error occurs
     */
    public static double getDouble(JSONObject Object, String AttributeName, double DefaultValueForNull)
    {
        try
        {
            return Object.getDouble(AttributeName);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        //catch if the object is null
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return DefaultValueForNull;
    }

    /**
     * trying to read a string value from a JSON object in a given attribute
     *
     * @param Object              the json object
     * @param AttributeName       the attribute name in the json object
     * @param DefaultValueForNull the default value to return if an error occurs
     * @return the read value or DefaultValueForNull sent value if an error occurs
     */
    public static String getString(JSONObject Object, String AttributeName, String DefaultValueForNull)
    {
        try
        {
            return Object.getString(AttributeName);
        } catch (JSONException e)
        {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return DefaultValueForNull;
    }

    /**
     * trying to read a boolean value from a JSON object in the given attribute name
     *
     * @param Object              the json object
     * @param AttributeName       the attribute name in the json object
     * @param DefaultValueForNull the default value to return if an error occurs
     * @return the read value or DefaultValueForNull sent value if an error occurs
     */
    public static boolean getBoolean(JSONObject Object, String AttributeName, boolean DefaultValueForNull)
    {
        try
        {
            return Object.getBoolean(AttributeName);
        } catch (JSONException e)
        {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return DefaultValueForNull;
    }

    /**
     * trying to read a JSON Object  value from a JSON object in the given attribute name
     *
     * @param Object        the json object
     * @param AttributeName the attribute name in the json object
     * @return the read value or Null sent value if an error occurs
     */
    public static JSONObject getJSONObject(JSONObject Object, String AttributeName)
    {
        try
        {
            return Object.getJSONObject(AttributeName);
        } catch (JSONException e)
        {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * trying to read a JSON array value from a JSON object in the given attribute name
     *
     * @param Object        the json object
     * @param AttributeName the attribute name in the json object
     * @return the read value or Null sent value if an error occurs
     */
    public static JSONArray getJSONArray(JSONObject Object, String AttributeName)
    {
        try
        {
            return Object.getJSONArray(AttributeName);
        } catch (JSONException e)
        {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * trying to read an object value from a JSON array in a given index
     *
     * @param Array the json object
     * @param index the index of the wanted object
     * @return the read value or null if an error occurs
     */
    public static JSONObject getObjectFromArray(JSONArray Array, int index)
    {
        try
        {
            return Array.getJSONObject(index);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        //catch if the Array is null
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Trying to parse a json object from a given string
     *
     * @param ObjectAsString the json object as string
     * @return the parsed JSON Object if no errors, Null otherwise
     */
    public static JSONObject getObject(String ObjectAsString)
    {
        try
        {
            return new JSONObject(ObjectAsString);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Trying to parse a json object from a given string
     *
     * @param ArrayAsString the json array as string
     * @return the parsed JSON Object if no errors, Null otherwise
     */
    public static JSONArray getJSONArray(String ArrayAsString)
    {
        try
        {
            return new JSONArray(ArrayAsString);
        } catch (JSONException e)
        {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     */
}
