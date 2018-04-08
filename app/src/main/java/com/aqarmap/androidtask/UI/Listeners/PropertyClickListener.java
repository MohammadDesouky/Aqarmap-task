package com.aqarmap.androidtask.UI.Listeners;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aqarmap.androidtask.Code.Structures.JSONs.JSONProperty;
import com.aqarmap.androidtask.Code.Utilities.Animations;
import com.aqarmap.androidtask.R;
import com.aqarmap.androidtask.UI.Activities.MainActivity;
import com.aqarmap.androidtask.UI.Activities.PropertyDetailsActivity;
import com.aqarmap.androidtask.UI.Fragments.PropertyDetailsFragment;

/**
 * Created by Mohammad Desouky on 03/04/2018.
 */

public class PropertyClickListener implements View.OnClickListener
{

    boolean mTwoPane;
    MainActivity mParentActivity;

    public PropertyClickListener(MainActivity act, boolean TwoPane)
    {
        mTwoPane = TwoPane;
        mParentActivity = act;
    }

    @Override
    public void onClick(View view)
    {
        JSONProperty item = (JSONProperty) view.getTag();
        if (mTwoPane)
        {
            Bundle arguments = new Bundle();
            arguments.putString(PropertyDetailsFragment.PROPERTY_KEY, item.getJSONObject().toString());
            PropertyDetailsFragment fragment = new PropertyDetailsFragment();
            fragment.setArguments(arguments);
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.property_details_wide, fragment)
                    .commit();
        } else
        {
            Intent intent = new Intent(mParentActivity.getBaseContext(), PropertyDetailsActivity.class);
            intent.putExtra(PropertyDetailsFragment.PROPERTY_KEY, item.getJSONObject().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mParentActivity.getBaseContext().startActivity(intent);
            Animations.fromRightToScreen(mParentActivity);
        }

    }

}
