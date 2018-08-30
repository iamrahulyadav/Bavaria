package com.bavaria.group.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bavaria.group.Activity.myAccount.ReplyActivity;
import com.bavaria.group.R;
import com.bavaria.group.retrofit.Model.MyTicketPojo;

import java.util.ArrayList;

public class MyTicketsAdapter extends RecyclerView.Adapter<MyTicketsAdapter.myViewHolder> {

    Context context;
    ArrayList<MyTicketPojo> myTicketPojos;

    public MyTicketsAdapter(Context context, ArrayList<MyTicketPojo> myTicketPojos) {
        this.context = context;
        this.myTicketPojos = myTicketPojos;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mytickets_item_layout, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        holder.tvDate.setText(myTicketPojos.get(position).getDate());
        holder.tvNoData.setText("#" + myTicketPojos.get(position).getId());
        holder.tvSupport.setText(myTicketPojos.get(position).getDep());

        holder.btnViewReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tcktIntent = new Intent(context, ReplyActivity.class);
                tcktIntent.putExtra("ticket_id", myTicketPojos.get(position).getId());
                context.startActivity(tcktIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTicketPojos.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvNoData, tvSupport;
        Button btnViewReply;

        public myViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.txtVwDate);
            tvNoData = itemView.findViewById(R.id.txtVwNoData);
            tvSupport = itemView.findViewById(R.id.txtVwSupport);
            btnViewReply = itemView.findViewById(R.id.btnViewReply);
        }
    }
}
