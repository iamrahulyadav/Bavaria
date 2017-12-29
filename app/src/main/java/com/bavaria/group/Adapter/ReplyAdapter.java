package com.bavaria.group.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bavaria.group.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Archirayan on 19-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.myViewHolder> {

    Context context;
    JSONArray arrayList;

    public ReplyAdapter(Context context, JSONArray arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reply_item_layout, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        try {
            holder.tvReplyerNm.setText(arrayList.getJSONObject(position).getString("replied_by"));
            holder.tvMsg.setText(arrayList.getJSONObject(position).getString("message"));
           // holder.tvDate.setText(arrayList.getJSONObject(position).getString("date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return arrayList.length();
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvReplyerNm, tvMsg;//, tvDate;

        public myViewHolder(View itemView) {
            super(itemView);

            tvReplyerNm = (TextView) itemView.findViewById(R.id.replier_nm);
            tvMsg = (TextView) itemView.findViewById(R.id.reply_msg);
         //   tvDate = (TextView) itemView.findViewById(R.id.reply_date);
        }
    }

}