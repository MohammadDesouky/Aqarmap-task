package com.aqarmap.androidtask.UI.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqarmap.androidtask.Code.Structures.JSONs.JSONProperty;
import com.aqarmap.androidtask.Code.Utilities.JSON;
import com.aqarmap.androidtask.R;
import com.aqarmap.androidtask.UI.Activities.MainActivity;
import com.aqarmap.androidtask.UI.Activities.PropertyDetailsActivity;
import com.aqarmap.androidtask.UI.Adapters.PropertyDetailsPagerAdapter;

/**
 * A fragment representing a single property detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link PropertyDetailsActivity}
 * on handsets.
 */
public class PropertyDetailsFragment extends Fragment
{

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String PROPERTY_KEY = "one_prop";
    public static JSONProperty SelectedProp;

    public PropertyDetailsFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(PROPERTY_KEY))
        {
            SelectedProp = new JSONProperty(JSON.getObject(getArguments().get(PROPERTY_KEY).toString()));
          /*   Activity activity = this.getActivity();
           CollapsingToolbarLayout appBarLayout =  activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
            {
                String title = SelectedProp.GetTitle();
                title = title.length() > 25 ? (title.substring(0, 20) + "...") : title;
                appBarLayout.setTitle(title);
            }*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_property_detail, container, false);
        ViewPager viewPager = rootView.findViewById(R.id.property_details_pager);
        viewPager.setAdapter(new PropertyDetailsPagerAdapter(getChildFragmentManager(), SelectedProp));
        TabLayout tabLayout = rootView.findViewById(R.id.tabs);

        TabLayout actTab = getActivity().findViewById(R.id.prop_details_tabs);
        if (actTab != null)
        {
            actTab.setupWithViewPager(viewPager);
            tabLayout.setVisibility(View.GONE);
        } else
            tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

}
