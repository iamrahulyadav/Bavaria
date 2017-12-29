package com.bavaria.group.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bavaria.group.R;

/**
 * Created by Archirayan on 17-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */


public class SupportContactAdapter extends RecyclerView.Adapter<SupportContactAdapter.myViewHolder> {

    Context context;

    public SupportContactAdapter(Context context) {
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.support_contact_item_layout, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position)
    {
        holder.tvNm.setText("+965 22942655");
        holder.tvNm1.setText("+965 55580338");
        holder.tvNm2.setText("+965 66793773");
    }

    @Override
    public int getItemCount()
    {
        return 1;
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvNm, tvNm1, tvNm2;

        public myViewHolder(View itemView) {
            super(itemView);

            tvNm = (TextView) itemView.findViewById(R.id.tvItemName);
            tvNm1 = (TextView) itemView.findViewById(R.id.tvItemName1);
            tvNm2 = (TextView) itemView.findViewById(R.id.tvItemName2);
        }
    }

}
