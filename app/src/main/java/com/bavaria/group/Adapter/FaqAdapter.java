package com.bavaria.group.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bavaria.group.OnItemClickListener;
import com.bavaria.group.R;
import com.bavaria.group.retrofit.Model.FaqPojo;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.myViewHolder> {

    Context context;
    ArrayList<FaqPojo> faqPojos;
    OnItemClickListener onItemClickListener;

    public FaqAdapter(Context context, ArrayList<FaqPojo> faqPojos, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.faqPojos = faqPojos;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faqs_item_layout, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position)
    {
        holder.tvPinningads.setText(faqPojos.get(position).getComment_count());

    }

    @Override
    public int getItemCount() {
        return faqPojos.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tvPinningads;

        public myViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    onItemClickListener.onClick(view, getAdapterPosition());

                }
            });
            tvPinningads = itemView.findViewById(R.id.tvPinningads);
        }
    }
}
