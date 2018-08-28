package com.bavaria.group.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bavaria.group.Activity.myAccount.PayNowActivity;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.Model.MembershipDataPojo;

import java.util.ArrayList;

/**
 * Created by Archirayan on 05-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */

public class MembershipAdapter extends RecyclerView.Adapter<MembershipAdapter.myViewHolder> {

    Context context;
    ArrayList<MembershipDataPojo> arrayList;
//    OnItemClickListener onItemClickListener;

    public MembershipAdapter(Context context, ArrayList<MembershipDataPojo> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
//        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yearly_membership_item_layout, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

        holder.tvProject.setText(arrayList.get(position).getProject_name());
        holder.tvBuilding.setText(arrayList.get(position).getBuilding_name());
        holder.tvFloor.setText(arrayList.get(position).getFloor_name());
        holder.tvFlat.setText(arrayList.get(position).getFlat_name());
        holder.tvExpiry.setText(arrayList.get(position).getExpary_date());
        holder.tvAmt.setText(arrayList.get(position).getAmount());

        holder.tvPay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (holder.etAmt.getText().toString().isEmpty()) {
                    holder.etAmt.setError("Please Enter Amount");
                }else  if(holder.etAmt.getText().toString().equals("0")){
                    holder.etAmt.setError("Please Enter Valid Amount");
                } else {
                    Intent intent = new Intent(context, PayNowActivity.class);
                    intent.putExtra("pay Amount", holder.etAmt.getText().toString());
                    intent.putExtra("bill id", arrayList.get(position).getBill_id());
                    intent.putExtra("building id", arrayList.get(position).getBuilding_id());
                    intent.putExtra("payment type", "Yearly Maintenance");
                    intent.putExtra("name", Utils.ReadSharePrefrence(context, Constant.USERNAME));
                    intent.putExtra("Email_id", Utils.ReadSharePrefrence(context, Constant.EMAIL));
                    intent.putExtra("phoneNumber", Utils.ReadSharePrefrence(context, Constant.PHONENUMBER));

                    context.startActivity(intent);
                }}
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvProject, tvBuilding, tvFloor, tvFlat, tvExpiry, tvAmt, tvPay;
        LinearLayout llMember;
        EditText etAmt;

        public myViewHolder(View itemView) {
            super(itemView);

            tvProject = itemView.findViewById(R.id.membership_tvProjectNm);
            tvBuilding = itemView.findViewById(R.id.membership_tvBuildingNm);
            tvFloor = itemView.findViewById(R.id.membership_tvFloorNm);
            tvFlat = itemView.findViewById(R.id.membership_tvFlatNm);
            tvExpiry = itemView.findViewById(R.id.membership_tvExpiry);
            tvAmt = itemView.findViewById(R.id.membership_tvAmount);
            tvPay = itemView.findViewById(R.id.membership_tvPay);

            etAmt = itemView.findViewById(R.id.membership_tvTotalAmount);

            llMember = itemView.findViewById(R.id.llMembership);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemClickListener.onClick(view, getAdapterPosition());
//
//                }
//            });

        }
    }

}
