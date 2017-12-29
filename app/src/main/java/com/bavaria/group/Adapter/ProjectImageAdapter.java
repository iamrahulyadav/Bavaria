package com.bavaria.group.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Activity.FullScreenImageActivity;
import com.bavaria.group.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by archirayan1 on 3/10/2016.
 */
public class ProjectImageAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ArrayList<String> imgList;
    ProjectImageAdapter adapter = this;
    private Context mContext;

    public ProjectImageAdapter(Context mContext, ArrayList<String> imgList) {
        this.mContext = mContext;
        this.imgList = imgList;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object getItem(int position) {
        return imgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        view = inflater.inflate(R.layout.add_image_item, null);
        holder = new ViewHolder();
        holder.ivProperty = (ImageView) view.findViewById(R.id.ivProject_ProjectDetailImage_Activity);
        if(imgList.get(position)!=null) {
            Glide.with(mContext)
                    .load(imgList.get(position))
                    .error(R.drawable.default_img)
                    .into(holder.ivProperty);
        }
        else
        {
            Toast.makeText(mContext, "Image not found", Toast.LENGTH_SHORT).show();
        }
        view.setTag(holder);

        holder = (ViewHolder) view.getTag();
        holder.ivProperty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, FullScreenImageActivity.class);
                intent.putExtra("img_list", imgList);
                intent.putExtra("position", "" + position);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    static class ViewHolder {
        ImageView ivProperty;

    }
}
