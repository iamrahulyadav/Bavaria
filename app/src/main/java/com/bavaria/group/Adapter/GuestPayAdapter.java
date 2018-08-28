package com.bavaria.group.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bavaria.group.R;
import com.bavaria.group.retrofit.Model.guestPayData;

import java.util.ArrayList;

public class GuestPayAdapter extends BaseAdapter {

    ArrayList<guestPayData> guestPayDataArrayList;
    Context context;

    public GuestPayAdapter(Context context, ArrayList<guestPayData> guestPayDataArrayList) {
        this.context = context;
        this.guestPayDataArrayList = guestPayDataArrayList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder = null;
        if (convertView == null) {
            // inflate the layout

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_row, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.textView = convertView.findViewById(R.id.tvItemName);
            // store the holder with the view.
            convertView.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        viewHolder.textView.setText(guestPayDataArrayList.get(position).getFlat_name().toString());
        return convertView;
    }

    @Override
    public int getCount() {
        return guestPayDataArrayList.size();
    }

    @Override
    public ArrayList<guestPayData> getItem(int position) {
        return guestPayDataArrayList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolderItem viewHolder = null;
        if (view == null) {
            // inflate the layout

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_row, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.textView = view.findViewById(R.id.tvItemName);
            // store the holder with the view.
            view.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) view.getTag();
        }

        viewHolder.textView.setText(guestPayDataArrayList.get(position).getFlat_name().toString());
        return view;
    }

    static class ViewHolderItem {
        TextView textView;
    }

}
