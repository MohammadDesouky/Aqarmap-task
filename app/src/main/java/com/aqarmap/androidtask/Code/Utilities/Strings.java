package com.aqarmap.androidtask.Code.Utilities;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mohammad Desouky on 05/04/2018.
 */

public class Strings
{

    public static String Capitalize(String Input)
    {
        Input = Input.trim().toLowerCase();
        String data[] = Input.split("\\s");
        Input = "";
        for (int i = 0; i < data.length; i++)
        {
            if (data[i].length() > 1)
                Input += data[i].substring(0, 1).toUpperCase() + data[i].substring(1) + " ";
            else
                Input += data[i].toUpperCase();
        }
        return Input.trim();
    }

    public static Date AtomDateFromString(String AtomDateFormat)
    {
        Date t = new Date();
        //skipping the "+" till now!
        //ToDo implement + for the atom date format I noticed that all time send from the service has +000 so I skipped this :D
        AtomDateFormat = AtomDateFormat.contains("+") ? AtomDateFormat.substring(0, AtomDateFormat.indexOf("+")) : AtomDateFormat;
        try
        {
            if (AtomDateFormat == null) return null;
            ParsePosition pos = new ParsePosition(0);
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return simpledateformat.parse(AtomDateFormat, pos);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return t;
    }

    public static String toMoneyFormat(double price)
    {
        return NumberFormat.getNumberInstance(Locale.US).format(price);
    }
}
