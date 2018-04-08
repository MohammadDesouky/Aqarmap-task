package com.aqarmap.androidtask.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONPhoto;
import com.aqarmap.androidtask.R;
import com.aqarmap.androidtask.glide.GalleryAdapter;
import com.aqarmap.androidtask.glide.Image;
import com.aqarmap.androidtask.glide.SlideshowDialogFragment;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 03/04/2018.
 */

public class PropertyPhotosFragment extends Fragment
{


    private ArrayList<Image> images;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PropertyPhotosFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    private void fetchImages()
    {

        images.clear();
        Image MainImage = new Image();
        //TODO to be replaced with the commented values
        MainImage.setLarge("https://api.androidhive.info/images/glide/large/bvs.png");
        //PropertyDetailsFragment.SelectedProp.getMainPhoto().getLarge());
        MainImage.setSmall("https://api.androidhive.info/images/glide/small/bvs.png");
        // PropertyDetailsFragment.SelectedProp.getMainPhoto().getSmall());
        images.add(MainImage);
        ArrayList<JSONPhoto> Photos = PropertyDetailsFragment.SelectedProp.getPhotos();
        if (Photos != null)
            for (int i = 0; i < Photos.size(); i++)
            {
                Image image = new Image();
                image.setName(Photos.get(i).getCaption());
                //TODO to be replaced with the commented values
                //dummy images
                image.setSmall("https://api.androidhive.info/images/glide/small/bvs.png");//Photos.get(i).getSmall());
                image.setLarge("https://api.androidhive.info/images/glide/large/bvs.png");//Photos.get(i).getLarge());
                images.add(image);
            }
        mAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_property_gallery, container, false);
        Toolbar toolbar = root.findViewById(R.id.toolbar);
        recyclerView = root.findViewById(R.id.glide_gallery_recycle_view);

        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(AndroidTaskApp.getAppContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(AndroidTaskApp.getAppContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(AndroidTaskApp.getAppContext(), recyclerView, new GalleryAdapter.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position)
            {

            }
        }));

        fetchImages();
        return root;
    }
}