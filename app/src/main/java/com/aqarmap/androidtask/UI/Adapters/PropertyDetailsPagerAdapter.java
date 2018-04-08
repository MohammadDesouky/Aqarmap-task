package com.aqarmap.androidtask.UI.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aqarmap.androidtask.Code.Structures.JSONs.JSONProperty;
import com.aqarmap.androidtask.UI.Fragments.PropertyMapFragment;
import com.aqarmap.androidtask.UI.Fragments.PropertyPhotosFragment;
import com.aqarmap.androidtask.UI.Fragments.PropertyTextDetailsFragment;

/**
 * Created by Mohammad Desouky on 03/04/2018.
 */

public class PropertyDetailsPagerAdapter extends FragmentStatePagerAdapter
{
    public static JSONProperty PropertyToShow;
    private final String[] mFragmentTitleList = {"Details", "Photos", "Map"};

    public PropertyDetailsPagerAdapter(FragmentManager manager, JSONProperty selectedProp)
    {
        super(manager);
        PropertyToShow = selectedProp;
    }

    @Override
    public Fragment getItem(int position)
    {

        switch (position)
        {
            case 0:
                return new PropertyTextDetailsFragment();
            case 1:
                return new PropertyPhotosFragment();
            case 2:
                return new PropertyMapFragment();
        }
        return null;
    }

    @Override
    public int getCount()
    {
        if (PropertyToShow.HasValidLocationOnMap())
            return 3;
        else
            return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mFragmentTitleList[position];
    }
}
