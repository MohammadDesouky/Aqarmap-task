package com.aqarmap.androidtask.Code.Utilities;

import android.app.Activity;

import com.aqarmap.androidtask.R;

/**
 * Created by Mohammad Desouky on 08/04/2018.
 */

public class Animations
{
    public static void fromRightToScreen(Activity act)
    {
        act.overridePendingTransition(R.anim.from_right_to_screen, R.anim.from_screen_to_left);
    }

    public static void fromLeftToScreen(Activity act)
    {
        act.overridePendingTransition(R.anim.from_left_to_screen, R.anim.from_screen_to_right);
    }

    public static void fromTopToScreen(Activity act)
    {
        act.overridePendingTransition(R.anim.from_top_to_screen, R.anim.from_screen_to_bottom);
    }

    public static void fromDownToScreen(Activity act)
    {
        act.overridePendingTransition(R.anim.from_bottom_to_screen, R.anim.from_screen_to_top);
    }
}
