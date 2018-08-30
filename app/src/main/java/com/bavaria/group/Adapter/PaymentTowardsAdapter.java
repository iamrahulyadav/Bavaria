package com.bavaria.group.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bavaria.group.R;
import com.bavaria.group.retrofit.Model.PaymentTowardsModel;

import java.util.ArrayList;

public class PaymentTowardsAdapter extends BaseAdapter {

    ArrayList<PaymentTowardsModel> paymentTowardsModels;
    Context context;

    public PaymentTowardsAdapter(Context context, ArrayList<PaymentTowardsModel> paymentTowardsModels) {
        this.context = context;
        this.paymentTowardsModels = paymentTowardsModels;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        GuestPayAdapter.ViewHolderItem viewHolder = null;
        if (convertView == null) {
            // inflate the layout

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_row, parent, false);

            // well set up the ViewHolder
            viewHolder = new GuestPayAdapter.ViewHolderItem();
            viewHolder.textView = convertView.findViewById(R.id.tvItemName);
            // store the holder with the view.
            convertView.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (GuestPayAdapter.ViewHolderItem) convertView.getTag();
        }

        viewHolder.textView.setText(paymentTowardsModels.get(position).getName());
        return convertView;
    }

    @Override
    public int getCount() {
        return paymentTowardsModels.size();
    }

    @Override
    public ArrayList<PaymentTowardsModel> getItem(int position) {
        return paymentTowardsModels;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        GuestPayAdapter.ViewHolderItem viewHolder = null;
        if (view == null) {
            // inflate the layout

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_row, parent, false);

            // well set up the ViewHolder
            viewHolder = new GuestPayAdapter.ViewHolderItem();
            viewHolder.textView = view.findViewById(R.id.tvItemName);
            // store the holder with the view.
            view.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (GuestPayAdapter.ViewHolderItem) view.getTag();
        }

        viewHolder.textView.setText(paymentTowardsModels.get(position).getName());
        return view;
    }

    static class ViewHolderItem {
        TextView textView;
    }

}
