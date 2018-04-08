package com.aqarmap.androidtask.UI.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aqarmap.androidtask.Code.Structures.JSONs.JSONProperty;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONPropertyAttribute;
import com.aqarmap.androidtask.R;
import com.aqarmap.androidtask.UI.Adapters.PropertyDetailsPagerAdapter;

/**
 * Created by Mohammad Desouky on 03/04/2018.
 */

public class PropertyTextDetailsFragment extends Fragment
{
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    JSONProperty prop;
    TextView OfferTypeTxtV, AddressTxtV, PriceTxtV, AreaTxtV, DescriptionTxtV, PublishedAtTxtV;
    LinearLayout AddressContainer, PriceContainer, AreaContainer, PublishedAtContainer, AttributesContainer;

    public PropertyTextDetailsFragment()
    {
        prop = PropertyDetailsPagerAdapter.PropertyToShow;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_property_text_details, container, false);
        AssignComponents(root);
        updateLayoutValues(inflater, container);
        return root;
    }

    private void AssignComponents(View root)
    {
        //assign TextViews
        OfferTypeTxtV = root.findViewById(R.id.prop_offer_type);
        AddressTxtV = root.findViewById(R.id.prop_address_txt);
        PriceTxtV = root.findViewById(R.id.prop_price_txt);
        AreaTxtV = root.findViewById(R.id.prop_area_txt);
        DescriptionTxtV = root.findViewById(R.id.prop_description_txt);
        PublishedAtTxtV = root.findViewById(R.id.prop_published_at_txt);
        //assign containers
        AddressContainer = root.findViewById(R.id.prop_address_container);
        PriceContainer = root.findViewById(R.id.prop_price_container);
        AreaContainer = root.findViewById(R.id.prop_area_container);
        PublishedAtContainer = root.findViewById(R.id.prop_published_at_container);
        AttributesContainer = root.findViewById(R.id.prop_attributes_container);
    }

    private void updateLayoutValues(LayoutInflater inflater, ViewGroup container)
    {
        OfferTypeTxtV.setText(prop.getOfferType());

        AddressTxtV.setText(prop.getAddress());
        if (AddressTxtV.getText().length() < 1)
            AddressContainer.setVisibility(View.GONE);

        PriceTxtV.setText(prop.getFormattedPrice());
        AreaTxtV.setText(prop.getAreaWithUnit());

        DescriptionTxtV.setText(prop.getDescription());
        if (DescriptionTxtV.getText().length() < 1)
            DescriptionTxtV.setVisibility(View.GONE);

        PublishedAtTxtV.setText(prop.getPublishedDate());
        if (PublishedAtTxtV.getText().length() < 1)
            PublishedAtContainer.setVisibility(View.GONE);

        //assign attributes
        for (JSONPropertyAttribute att : prop.getAttributes())
        {
            if (att.isValidAttribute())
            {
                View attrib = inflater.inflate(R.layout.one_attribute_layout, container, false);
                TextView attName = attrib.findViewById(R.id.att_name);
                TextView attValue = attrib.findViewById(R.id.att_value);

                attName.setText(att.getName());
                attValue.setText(att.getValue());
                AttributesContainer.addView(attrib);



              /*  ViewGroup.LayoutParams ContainerLayoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                //adjust the attribute container
                LinearLayout attContainer = new LinearLayout(getContext());
                attContainer.setOrientation(LinearLayout.HORIZONTAL);
                attContainer.setLayoutParams(ContainerLayoutParams);

                //adjsut attribute name and value
                TextView attName=new TextView(getContext()),
                         attValue=new TextView(getContext());
                attName.setText(att.getName());
                attValue.setText(att.getValue());
                attName.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                attValue.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                //adding the components to UI
                attContainer.addView(attName);
                attContainer.addView(attValue);
                AttributesContainer.addView(attContainer);*/
            }
        }
        if (prop.getAttributes() == null || prop.getAttributes().size() == 0)
        {
            AttributesContainer.setVisibility(View.GONE);
        }
    }
}
