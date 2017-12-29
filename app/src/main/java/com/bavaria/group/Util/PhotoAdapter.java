package com.bavaria.group.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Activity.CateloguePicsActivity;
import com.bavaria.group.Activity.PhotoVideoPicsActivity;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.ImageshowActivity;
import com.bavaria.group.PhotoActivity;
import com.bavaria.group.Photosetget;
import com.bavaria.group.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan on 7/11/17.
*/

public class PhotoAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Photosetget> projectList;
    private LayoutInflater inflater = null;
    public Photosetget photosetget;
    public String str_PhotoId;
    public LinearLayout layt_main;


    public PhotoAdapter(PhotoActivity photoActivity, ArrayList<Photosetget> dataList)
    {

        this.projectList=dataList;
        this.mContext = photoActivity;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return projectList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        com.bavaria.group.Adapter.PhotoVideoListAdapter.ViewHolderItem viewHolder = null;
        if (view == null) {
            // inflate the layout
            view = inflater.inflate(R.layout.item_photo_video_list, parent, false);

            // well set up the ViewHolder


            viewHolder = new com.bavaria.group.Adapter.PhotoVideoListAdapter.ViewHolderItem();
            viewHolder.tvProjectName = (TextView) view.findViewById(R.id.tvTitle_photoVideoListActivity);
            viewHolder.ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto_photoVideoListActivity);
            // store the holder with the view.
            layt_main= (LinearLayout) view.findViewById(R.id.layt_main);
            layt_main.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    str_PhotoId=projectList.get(position).getStr_id();
                    Intent intent = new Intent(mContext, ImageshowActivity.class);
                    intent.putExtra("pid", str_PhotoId);
                    mContext.startActivity(intent);
                    Activity activity = (Activity) mContext;
                    activity.overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                }
            });
            view.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (com.bavaria.group.Adapter.PhotoVideoListAdapter.ViewHolderItem) view.getTag();
        }



        System.err.println("");
        photosetget= projectList.get(position);
        viewHolder.tvProjectName.setText(photosetget.getStr_name());
        if (!photosetget.getStr_image().equalsIgnoreCase(""))
        {
            Glide.with(mContext).load(photosetget.getStr_image()).placeholder(R.drawable.default_img).into(viewHolder.ivPhoto);
        }

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, ImageshowActivity.class);
                intent.putExtra("pid", str_PhotoId);
                mContext.startActivity(intent);
                Activity activity = (Activity) mContext;
                activity.overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
            }
        });

        return view;
    }

    static class ViewHolderItem
    {
        TextView tvProjectName;
        ImageView ivPhoto;
    }
}
