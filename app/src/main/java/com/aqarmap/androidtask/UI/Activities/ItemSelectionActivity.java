package com.aqarmap.androidtask.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONLocation;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONTitledResult;
import com.aqarmap.androidtask.R;

public class ItemSelectionActivity extends AppCompatActivity
{
    public static final String INTENT_SELECT_FROM = "SelectFrom",
            INTNET_LOCATIONS = "Locations",
            INTENT_PROPERTY_TYPES = "Types",
            LOCATIONS_ACT_TITLE = "Choose the location",
            TYPES_ACT_TITLE = "Choose a type:";
    public static JSONLocation CurrentLocation;
    public static JSONTitledResult CurrentType;
    TextView HeaderTv;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selection);
        HeaderTv = findViewById(R.id.item_select_main_item_txt_v);
        mListView = findViewById(R.id.items_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String Loading = getIntent().getStringExtra(INTENT_SELECT_FROM);
        if (Loading.equals(INTNET_LOCATIONS))
        {
            AdjustForLocations();
        } else
        {
            AdjustForPropertyTypes();
        }
    }

    private void AdjustForPropertyTypes()
    {
        getSupportActionBar().setTitle(TYPES_ACT_TITLE);
        ArrayAdapter<JSONTitledResult> adpt = new ArrayAdapter<JSONTitledResult>(getBaseContext(), android.R.layout.simple_list_item_1);
        HeaderTv.setVisibility(View.GONE);
        adpt.addAll(AndroidTaskApp.getSearchFilter().getPropertyTypes());
        adpt.notifyDataSetChanged();
        mListView.setAdapter(adpt);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                CurrentType = (JSONTitledResult) parent.getAdapter().getItem(position);
                EndSelection();
            }
        });
    }

    private void AdjustForLocations()
    {
        ArrayAdapter<JSONLocation> adpt = new ArrayAdapter<JSONLocation>(getBaseContext(), android.R.layout.simple_list_item_1);

        getSupportActionBar().setTitle(LOCATIONS_ACT_TITLE);
        HeaderTv.setVisibility(View.GONE);
        if (CurrentLocation != null)
        {
            HeaderTv.setVisibility(View.VISIBLE);
            HeaderTv.setText("Select a location in " + CurrentLocation.GetTitle() + " or click here!");
            HeaderTv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    EndSelection();

                }
            });
            adpt.addAll(CurrentLocation.getChildren());

        }
        if (adpt.getCount() == 0)
        {
            adpt.addAll(AndroidTaskApp.getSearchFilter().getLocations());
        }
        adpt.notifyDataSetChanged();
        mListView.setAdapter(adpt);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                CurrentLocation = (JSONLocation) parent.getAdapter().getItem(position);
                if (CurrentLocation.getChildren() != null)
                {
                    //start new instance of this
                    Intent i = new Intent(getBaseContext(), ItemSelectionActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.putExtra(ItemSelectionActivity.INTENT_SELECT_FROM, ItemSelectionActivity.INTNET_LOCATIONS);
                    getBaseContext().startActivity(i);
                } else
                    EndSelection();
            }
        });
    }

    private void EndSelection()
    {
        if (CurrentLocation != null)
        {
            AndroidTaskApp.getSearchFilter().setSelectedLocation(CurrentLocation);
            CurrentLocation = null;
        }
        if (CurrentType != null)
        {

            AndroidTaskApp.getSearchFilter().setSelectedPropertyType(CurrentType);
            CurrentType = null;
        }
        finish();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        CurrentLocation = null;
        CurrentType = null;
    }
}
