package com.aqarmap.androidtask.UI.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Controllers.SearchFilter;
import com.aqarmap.androidtask.Code.Utilities.Animations;
import com.aqarmap.androidtask.R;

public class SearchFilterActivity extends AppCompatActivity
{
    AppCompatButton PropertyTypeBtn, PropertyLocationBtn, SaveAndUpdateBtn;
    Spinner PropertySectionSpnr, PropertyMinPriceSpnr, PropertyMaxPriceSpnr;
    Toolbar toolbar;
    ActionBar actionBar;
    SearchFilter mSearchFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);


        AdjustToolbar();
        AssignLayoutComponents();
        mSearchFilter = AndroidTaskApp.getSearchFilter();
        mSearchFilter.setOwnerActivity(this);
        mSearchFilter.UpdateLayoutComponents(PropertySectionSpnr, PropertyMinPriceSpnr, PropertyMaxPriceSpnr,
                PropertyTypeBtn, PropertyLocationBtn);
        findViewById(R.id.save_and_update_filter_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SaveAndReload();
            }
        });
    }

    private void AdjustToolbar()
    {
        toolbar = findViewById(R.id.search_filter_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void AssignLayoutComponents()
    {
        PropertyTypeBtn = findViewById(R.id.property_type_btn);
        PropertyLocationBtn = findViewById(R.id.property_location_btn);
        SaveAndUpdateBtn = findViewById(R.id.save_and_update_filter_btn);
        PropertySectionSpnr = findViewById(R.id.section_spinner);
        PropertyMinPriceSpnr = findViewById(R.id.min_price_spinner);
        PropertyMaxPriceSpnr = findViewById(R.id.max_price_spinner);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                if (!mSearchFilter.HasChanges())
                {
                    NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                    AnimateActivity();
                } else AlertToSave();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if (canExit())
        {
            super.onBackPressed();
            AnimateActivity();
            finish();
        } else
            AlertToSave();
    }

    private void AnimateActivity()
    {
        Animations.fromDownToScreen(this);
    }

    private void AlertToSave()
    {
        if (mSearchFilter.HasChanges())
        {
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
            mDialogBuilder.setMessage("Unsaved Changes");
            mDialogBuilder.setCancelable(true);
            mDialogBuilder.setPositiveButton(
                    "Save Changes",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.cancel();
                            SaveAndReload();
                        }
                    });

            mDialogBuilder.setNegativeButton(
                    "Discard",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.cancel();
                            finish();
                            AnimateActivity();
                        }
                    });

            AlertDialog alert = mDialogBuilder.create();
            alert.show();
        }
    }

    private void SaveAndReload()
    {
        mSearchFilter.saveSelectionChangesToSharedPref();
        Intent i = new Intent();
        i.putExtra(MainActivity.INTNT_DO, MainActivity.INTNT_RELOAD);
        setResult(MainActivity.MyRequestCode, i);
        finish();
        AnimateActivity();
    }

    boolean canExit()
    {
        return !mSearchFilter.HasChanges();
    }
}
