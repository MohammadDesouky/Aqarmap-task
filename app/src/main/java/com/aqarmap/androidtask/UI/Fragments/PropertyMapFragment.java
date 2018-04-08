package com.aqarmap.androidtask.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqarmap.androidtask.R;
import com.aqarmap.androidtask.UI.Adapters.PropertyDetailsPagerAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Mohammad Desouky on 03/04/2018.
 */

public class PropertyMapFragment extends Fragment implements OnMapReadyCallback
{

    MapView mapView;
    float Zoom = 18f;
    private GoogleMap mMap;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PropertyMapFragment()
    {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_property_map, container, false);
        mapView = root.findViewById(R.id.map);
        Bundle mapViewBundle = null;
        mapViewBundle = new Bundle();

        if (savedInstanceState != null)
        {
            mapViewBundle = savedInstanceState.getBundle(getResources().getString(R.string.google_maps_api_key));
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        return root;

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        LatLng propLoc = new LatLng(PropertyDetailsPagerAdapter.PropertyToShow.getLatitude(),
                PropertyDetailsPagerAdapter.PropertyToShow.getLongitude());
        mMap.addMarker(new MarkerOptions().position(propLoc).title("Property Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(propLoc, Zoom));
    }
}
