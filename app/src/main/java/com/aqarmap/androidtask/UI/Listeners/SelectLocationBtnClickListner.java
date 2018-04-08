package com.aqarmap.androidtask.UI.Listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.aqarmap.androidtask.UI.Activities.ItemSelectionActivity;

/**
 * Created by Mohammad Desouky on 08/04/2018.
 */

public class SelectLocationBtnClickListner implements View.OnClickListener
{

    Activity mActivity;

    public SelectLocationBtnClickListner(Activity ownerActivity)
    {
        mActivity = ownerActivity;
    }

    @Override
    public void onClick(View v)
    {
        //open new activity for location selection
        Intent i = new Intent(mActivity, ItemSelectionActivity.class);
        i.putExtra(ItemSelectionActivity.INTENT_SELECT_FROM, ItemSelectionActivity.INTNET_LOCATIONS);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        mActivity.startActivity(i);
    }
}
