package com.aqarmap.androidtask.UI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONListing;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONProperty;
import com.aqarmap.androidtask.Code.Threading.IThreadTask;
import com.aqarmap.androidtask.Code.Threading.ListingLoadingTask;
import com.aqarmap.androidtask.R;
import com.aqarmap.androidtask.UI.Activities.MainActivity;
import com.aqarmap.androidtask.UI.Listeners.PropertyClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class PropertiesRecyclerViewAdapter extends RecyclerView.Adapter<PropertyViewHolder>
{
    private final static int DURATION = 300;
    IThreadTask<Void, Void, JSONListing> LoadingCallback;
    JSONListing mJsonListing;
    int mCurrentLoadedPage = 0;
    PropertyClickListener mOnClickListener;

    public PropertiesRecyclerViewAdapter(MainActivity mainActivity, boolean mTwoPane,
                                         IThreadTask<Void, Void, JSONListing> callback)
    {
        mOnClickListener = new PropertyClickListener(mainActivity, mTwoPane);
        LoadingCallback = callback;
    }

    /**
     * Reloads the data from the beginning after clearing the current Properties data
     * supposed to be used if the user refresh the list with slide down gesture
     */
    public void Reload()
    {

        ListingLoadingTask t = new ListingLoadingTask("1", LoadingCallback);
        notifyDataSetChanged();
        t.AddAction(new IThreadTask<Void, Void, JSONListing>()
        {
            @Override
            public void BeforeRun()
            {

            }

            @Override
            public JSONListing Action(Void... Params)
            {
                return null;
            }

            @Override
            public void AfterRun(JSONListing Result)
            {
                mJsonListing = Result;
                if (Result != null)
                    notifyDataSetChanged();
            }

            @Override
            public void OnProgress(Void[] Values)
            {

            }
        });

        t.execute();

    }

    /**
     * Load more properties supposed to be used after user reach the end of the list
     */
    //todo implement this and use it
    public void LoadMore()
    {
        /*ListingLoadingTask t=new ListingLoadingTask("1",LoadingCallback);
        try
        {
            ArrayList<JSONProperty> props=t.execute().get().getProperties();
            for(JSONProperty p : props)
                Properties.add(p);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }*/
    }

    public int getTotalCount()
    {
        return mJsonListing != null ? mJsonListing.getPropertyTotalCount() : 0;
    }

    public void Clear()
    {
        if (mJsonListing == null) return;
        mJsonListing.Clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return mJsonListing != null ? mJsonListing.getProperties().size() : 0;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_property_list_content, parent, false);

        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder holder, int position)
    {
        // holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);
        holder.UpdateLayout(mJsonListing.getProperties().get(position));
        holder.itemView.setTag(mJsonListing.getProperties().get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
        setFadeAnimation(holder.itemView);

        //loading with glide is better for caching the images but not good for storage!
        JSONProperty prop = mJsonListing.getProperties().get(position);
        Glide.with(AndroidTaskApp.getAppContext()).load(prop.getMainPhoto().getSmall())
                .thumbnail(0.5f)
                .placeholder(R.drawable.dummy_img)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mMainImg);

    }

    private void setFadeAnimation(View view)
    {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(DURATION);
        view.startAnimation(anim);
        //Animation
    }

    private void setScaleAnimation(View view)
    {
        ScaleAnimation anim = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(DURATION);
        view.startAnimation(anim);
    }


}
