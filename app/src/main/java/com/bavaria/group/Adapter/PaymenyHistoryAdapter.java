package com.bavaria.group.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bavaria.group.Activity.myAccount.ViewInvoiceActivity;
import com.bavaria.group.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Archirayan on 07-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */


public class PaymenyHistoryAdapter extends RecyclerView.Adapter<PaymenyHistoryAdapter.myViewHolder> {

    Context context;
    JSONArray arrayList;

    public PaymenyHistoryAdapter(Context context, JSONArray arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_history_item_layout, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        try {
            holder.tvProjectNm.setText(arrayList.getJSONObject(position).getString("project_name").replaceAll("\"", ""));
            holder.tvPaymentTowards.setText(arrayList.getJSONObject(position).getString("Payment_towards").replaceAll("\"", ""));
            holder.tvTransactionID.setText(arrayList.getJSONObject(position).getString("Transaction_Id").replaceAll("\"", ""));
            holder.tvTransactionDate.setText(arrayList.getJSONObject(position).getString("Transaction_Date").replaceAll("\"", ""));
            holder.tvTransactionTime.setText(arrayList.getJSONObject(position).getString("Transaction_Time").replaceAll("\"", ""));
            holder.tvAmt.setText(arrayList.getJSONObject(position).getString("Amount_Paid").replaceAll("\"", ""));
            holder.tvType.setText(arrayList.getJSONObject(position).getString("Transaction_Type").replaceAll("\"", ""));

            holder.tvView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(context, ViewInvoiceActivity.class);
                    try {
                        in.putExtra("invoiceid", arrayList.getJSONObject(position).getString("id").replaceAll("\"", ""));
                        in.putExtra("invoicekid", arrayList.getJSONObject(position).getString("kid").replaceAll("\"", ""));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    context.startActivity(in);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.length();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvProjectNm, tvPaymentTowards, tvTransactionID, tvTransactionDate, tvTransactionTime, tvAmt, tvView, tvType;

        public myViewHolder(View itemView) {
            super(itemView);

            tvProjectNm = (TextView) itemView.findViewById(R.id.paymentHistory_tvProjectNm);
            tvPaymentTowards = (TextView) itemView.findViewById(R.id.paymentHistory_tvProjectTowards);
            tvTransactionID = (TextView) itemView.findViewById(R.id.paymentHistory_tvTid);
            tvTransactionDate = (TextView) itemView.findViewById(R.id.paymentHistory_tvTdate);
            tvTransactionTime = (TextView) itemView.findViewById(R.id.paymentHistory_tvTtime);
            tvAmt = (TextView) itemView.findViewById(R.id.paymentHistory_tvAmount);
            tvType = (TextView) itemView.findViewById(R.id.paymentHistory_tvTtype);
            tvView = (TextView) itemView.findViewById(R.id.paymentHistory_tvInvoice);

        }
    }

}