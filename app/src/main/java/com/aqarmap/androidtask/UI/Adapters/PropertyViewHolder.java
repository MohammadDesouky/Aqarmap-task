package com.aqarmap.androidtask.UI.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqarmap.androidtask.Code.Structures.JSONs.JSONProperty;
import com.aqarmap.androidtask.Code.Utilities.Contact;
import com.aqarmap.androidtask.R;


/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class PropertyViewHolder extends RecyclerView.ViewHolder
{

    TextView mTitleTxt, mLocationTxt, mMediaTxt;
    ImageView mMainImg, mCallImg, mMailImg;
    JSONProperty mJsonProp;
    Context cntxt;

    public PropertyViewHolder(View view)
    {
        super(view);
        mTitleTxt = view.findViewById(R.id.property_title_txt_view);
        mLocationTxt = view.findViewById(R.id.property_location_txt_view);
        mMediaTxt = view.findViewById(R.id.property_media_txt_view);
        mMainImg = view.findViewById(R.id.property_main_photo);
        mCallImg = view.findViewById(R.id.call_img_view);
        mMailImg = view.findViewById(R.id.email_img_view);
        cntxt = view.getContext();
        mCallImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Contact.Call(mJsonProp.getPhone());
            }
        });
        mMailImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Contact.OpenSendingMail(mJsonProp.getMail(), "Your ad. on Aqarmap!", "", cntxt);
            }
        });
    }

    public void UpdateLayout(JSONProperty jsonProperty)
    {
        mJsonProp = jsonProperty;
        try
        {
            Bitmap bm = jsonProperty.MyImage;
            if (bm != null)
                mMainImg.setImageBitmap(bm);
            mTitleTxt.setText(jsonProperty.getFormattedTitle());
            mLocationTxt.setText(jsonProperty.getFormattedLocation());
            mMediaTxt.setText(jsonProperty.getFormattedMedia());

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
