package com.bavaria.group.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bavaria.group.R;
import com.bavaria.group.retrofit.Model.AvailabilityCheckData;

import java.util.ArrayList;

/**
 * Created by Archirayan on 08-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */


public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.myViewHolder> {

    Context context;
    ArrayList<AvailabilityCheckData> arrayList;
//    OnItemClickListener onItemClickListener;

    public AvailabilityAdapter(Context context, ArrayList<AvailabilityCheckData> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
//        this.onItemClickListener =onItemClickListener;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.availability_item_layout, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.tvProjectName.setText(arrayList.get(position).getBuilding_name());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvProjectName;

        public myViewHolder(View itemView)
        {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.availability_bllock);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view)
//                  {
//                    onItemClickListener.onClick(view, getAdapterPosition());
//
//                }
//            });

        }
    }

}

