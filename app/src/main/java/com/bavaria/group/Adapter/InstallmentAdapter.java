package com.bavaria.group.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bavaria.group.Activity.myAccount.PayNowActivity;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.Model.InstallmentDataPojo;

import java.util.ArrayList;

/**
 * Created by Archirayan on 05-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */

public class InstallmentAdapter extends RecyclerView.Adapter<InstallmentAdapter.myViewHolder> {

    Context context;
    ArrayList<InstallmentDataPojo> arrayList;

    public InstallmentAdapter(Context context, ArrayList<InstallmentDataPojo> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.installment_item_layout, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        holder.tvProjectName.setText(arrayList.get(position).getProject_name());
        holder.tvBuildingNm.setText(arrayList.get(position).getBuilding_name());
        holder.tvFloorName.setText(arrayList.get(position).getFloor_name());
        holder.tvFLatNm.setText(arrayList.get(position).getFlat_name());
        holder.tvAmt.setText(arrayList.get(position).getAmount());

        holder.tvPay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PayNowActivity.class);
                intent.putExtra("pay Amount", holder.tvTotalAmt.getText().toString());
                intent.putExtra("bill id", arrayList.get(position).getBill_id());
                intent.putExtra("building id", arrayList.get(position).getBuilding_id());
                intent.putExtra("payment type", "1");

                intent.putExtra("name", Utils.ReadSharePrefrence(context, Constant.USERNAME));
                intent.putExtra("Email_id", Utils.ReadSharePrefrence(context, Constant.EMAIL));
                intent.putExtra("phoneNumber", Utils.ReadSharePrefrence(context, Constant.PHONENUMBER));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvProjectName, tvBuildingNm, tvFloorName, tvFLatNm, tvPay, tvAmt;
        EditText tvTotalAmt;

        public myViewHolder(View itemView) {
            super(itemView);

            tvProjectName = itemView.findViewById(R.id.installment_tvProjectNm);
            tvBuildingNm = itemView.findViewById(R.id.installment_tvBuildingNm);
            tvFloorName = itemView.findViewById(R.id.installment_tvFloorNm);
            tvFLatNm = itemView.findViewById(R.id.installment_tvFlatNm);
            tvAmt = itemView.findViewById(R.id.installment_tvAmount);
            tvTotalAmt = itemView.findViewById(R.id.installment_etTotalAmount);

            tvPay = itemView.findViewById(R.id.installment_tvPay);
        }
    }
}
