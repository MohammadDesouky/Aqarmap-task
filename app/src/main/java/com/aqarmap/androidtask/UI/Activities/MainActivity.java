package com.aqarmap.androidtask.UI.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Controllers.SearchFilter;
import com.aqarmap.androidtask.Code.Interfaces.IConnectionChangeNotifier;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONListing;
import com.aqarmap.androidtask.Code.Threading.IThreadTask;
import com.aqarmap.androidtask.Code.Utilities.Animations;
import com.aqarmap.androidtask.Code.Utilities.Network;
import com.aqarmap.androidtask.R;
import com.aqarmap.androidtask.UI.Adapters.PropertiesRecyclerViewAdapter;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PropertyDetailsActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MainActivity extends AppCompatActivity implements IConnectionChangeNotifier
{

    public static final String INTNT_RELOAD = "reload",
            INTNT_DO = "Do";
    static final int MyRequestCode = 25544;
    ProgressDialog filterDataLoadingProgressDialog;
    SearchFilter sf;
    PropertiesRecyclerViewAdapter propertiesRecyclerViewAdapter;
    View recyclerView;
    LinearLayout listLayout;
    LinearLayout disconnectedLayout;
    TextView resultTv;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //Loading properties task call back
    IThreadTask<Void, Void, JSONListing> PropertyLoadingCallback = new IThreadTask<Void, Void, JSONListing>()
    {
        @Override
        public void BeforeRun()
        {
            if (!mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        public JSONListing Action(Void... Params)
        {
            return null;
        }

        @Override
        public void AfterRun(JSONListing Result)
        {
            mSwipeRefreshLayout.setRefreshing(false);
            resultTv.setVisibility(View.VISIBLE);
            resultTv.setText("about " + Result.getPropertyTotalCount() + " " +
                    AndroidTaskApp.getSearchFilter().toString());
        }

        @Override
        public void OnProgress(Void[] Values)
        {

        }
    };
    //Filter OptionsCallBack
    IThreadTask<Void, Void, Void> FilterOptionsCallBack = new IThreadTask<Void, Void, Void>()
    {
        @Override
        public void BeforeRun()
        {

        }

        @Override
        public Void Action(Void... Params)
        {
            return null;
        }

        @Override
        public void AfterRun(Void Result)
        {
            filterDataLoadingProgressDialog.dismiss();
            AndroidTaskApp.SetSearchfilter(sf);
            setupRecyclerView((RecyclerView) recyclerView);
        }

        @Override
        public void OnProgress(Void[] Values)
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_logo);
        setSupportActionBar(toolbar);
        listLayout = findViewById(R.id.list_container);
        disconnectedLayout = findViewById(R.id.disconnected_layout);
        AndroidTaskApp.registerConnectionListner(this);
        resultTv = findViewById(R.id.list_result_tv);
        resultTv.setVisibility(View.GONE);
        if (findViewById(R.id.properties_list_wide) != null)
        {
            //on the wider than 900dp screens
            mTwoPane = true;
            recyclerView = findViewById(R.id.properties_list_wide);
            mSwipeRefreshLayout = findViewById(R.id.properties_refresh_list_wide);
        } else
        {
            recyclerView = findViewById(R.id.properties_list);
            mSwipeRefreshLayout = findViewById(R.id.properties_refresh_list);
        }
        //Checking CurrentSearchFilter
        sf = new SearchFilter();
        if (!sf.LoadFromSharedPref())
        {
            filterDataLoadingProgressDialog = ProgressDialog.show(this, "", "Welcome to Aqarmap, Loading data for first time use\nPlease Wait...");
            //load online
            sf.LoadFromWeb(FilterOptionsCallBack);
        } else
        {
            setupRecyclerView((RecyclerView) recyclerView);
            AndroidTaskApp.SetSearchfilter(sf);
        }
        //check connection
        if (!Network.areWeConnected())
        {
            listLayout.setVisibility(View.GONE);
            disconnectedLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == MyRequestCode)
        {
            if (data.getStringExtra(INTNT_DO).equals(INTNT_RELOAD))
            {
                Reload();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_search:
                ShowSearchFilter();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShowSearchFilter()
    {
        Intent myIntent = new Intent(this, SearchFilterActivity.class);
        startActivityForResult(myIntent, MyRequestCode);
        Animations.fromTopToScreen(this);
    }

    public void Reload()
    {
        //hide the result count lable
        propertiesRecyclerViewAdapter.Clear();
        resultTv
                .setVisibility(View.GONE);
        if (!mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(true);
        propertiesRecyclerViewAdapter.Reload();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        propertiesRecyclerViewAdapter = new PropertiesRecyclerViewAdapter(this, mTwoPane, PropertyLoadingCallback);
        recyclerView.setAdapter(propertiesRecyclerViewAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Reload();
            }
        });
        //automatic loading
        Reload();

    }

    @Override
    public void OnConnected()
    {
        listLayout.setVisibility(View.VISIBLE);
        disconnectedLayout.setVisibility(View.GONE);
        if (propertiesRecyclerViewAdapter.getItemCount() < 1)
        {

        }
        Reload();
    }

    @Override
    public void onDisconnected()
    {
        //TODO handle no internet while loading the filter options first time use
        //no internet while loading the filter options
        if (filterDataLoadingProgressDialog != null && filterDataLoadingProgressDialog.isShowing())
        {

        }

        listLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.VISIBLE);
    }

}
