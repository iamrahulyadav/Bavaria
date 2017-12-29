package com.bavaria.group.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bavaria.group.R;
import com.bavaria.group.retrofit.Model.verifyUserData;

import java.util.ArrayList;

/**
 * Created by Archirayan on 06-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */


public class VerifyUserAdapter extends RecyclerView.Adapter<VerifyUserAdapter.myViewHolder> {

    Context context;
    ArrayList<verifyUserData> arrayList;

    public VerifyUserAdapter(Context context, ArrayList<verifyUserData> arrayList) {

        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.openticket_item_layout, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {


        public myViewHolder(View itemView) {
            super(itemView);


        }
    }
}
