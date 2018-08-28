package com.bavaria.group.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bavaria.group.OnItemClickListener;
import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.Model.verifyUserData;

import java.util.ArrayList;

import static com.bavaria.group.Activity.PayOnlineActivity.paymentAdapter;
import static com.bavaria.group.Activity.PayOnlineActivity.spPaymentTowards;
import static com.bavaria.group.Activity.PayOnlineActivity.tvTotal;
import static com.bavaria.group.Constant.Constant.BUILDING_ID;
import static com.bavaria.group.Constant.Constant.CHECK_CLICK;
import static com.bavaria.group.Constant.Constant.FLAT_NAME;
import static com.bavaria.group.Constant.Constant.FLOOR_NAME;
import static com.bavaria.group.Constant.Constant.INSTALLMENT_ID;
import static com.bavaria.group.Constant.Constant.MEMBERSHIP_ID;
import static com.bavaria.group.Constant.Constant.PROJECT_NAME;
import static com.bavaria.group.Constant.Constant.TOTAL_AMT;
import static com.bavaria.group.Constant.Constant.WATERBILL_ID;

/**
 * Created by Archirayan on 20-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */

public class PayOnlineAdapter extends RecyclerView.Adapter<PayOnlineAdapter.myViewHolder> {

    Context context;
    public static ArrayList<verifyUserData> verifyUserDataa;
    OnItemClickListener onItemClickListener;
    public static int lastCheckedPosition = -1;
    public static int lastCheckedPosition1 = -1;

    public static int pos1;

    public PayOnlineAdapter(Context context, ArrayList<verifyUserData> verifyUserDataa) {
        this.context = context;
        PayOnlineAdapter.verifyUserDataa = verifyUserDataa;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.open_onlineitemadapter, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

//        pos1 = position;

        holder.tvProjectNm.setText(verifyUserDataa.get(position).getProject_name());
        holder.tvBuildingNm.setText(verifyUserDataa.get(position).getBuilding_name());
        holder.tvFloorNm.setText(verifyUserDataa.get(position).getFloor_name());
        holder.tvFlatNm.setText(verifyUserDataa.get(position).getFlat_name());

        if (Integer.parseInt(verifyUserDataa.get(position).getBuilding_id()) == lastCheckedPosition1)
        {
            holder.rbSelect.setChecked(true);

            Utils.WriteSharePrefrence(context, CHECK_CLICK, "true");
        } else {
            holder.rbSelect.setChecked(false);

        }

        holder.rbSelect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lastCheckedPosition = position;
                lastCheckedPosition1 = Integer.parseInt(verifyUserDataa.get(position).getBuilding_id());
                notifyItemRangeChanged(0, verifyUserDataa.size());
                pos1 = lastCheckedPosition;
                tvTotal.setEnabled(false);
                //tvTotal.setText(verifyUserDataa.get(position).getTotal_amount());
                spPaymentTowards.setAdapter(paymentAdapter);
              //  spPaymentType.setAdapter(othersnameAdapter);
                Utils.WriteSharePrefrence(context, CHECK_CLICK, "true");
                Utils.WriteSharePrefrence(context, BUILDING_ID, verifyUserDataa.get(position).getBuilding_id());
                Utils.WriteSharePrefrence(context, TOTAL_AMT, verifyUserDataa.get(position).getTotal_amount());
                Utils.WriteSharePrefrence(context, INSTALLMENT_ID, verifyUserDataa.get(position).getInstallment_id());
                Utils.WriteSharePrefrence(context, WATERBILL_ID, verifyUserDataa.get(position).getWaterbill_id());
                Utils.WriteSharePrefrence(context, MEMBERSHIP_ID, verifyUserDataa.get(position).getFees_id());
                Utils.WriteSharePrefrence(context, PROJECT_NAME, verifyUserDataa.get(position).getProject_name());
                Utils.WriteSharePrefrence(context, FLOOR_NAME, verifyUserDataa.get(position).getFloor_name());
                Utils.WriteSharePrefrence(context, FLAT_NAME, verifyUserDataa.get(position).getFlat_name());
            }
        });

        holder.llMain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Utils.WriteSharePrefrence(context, CHECK_CLICK, "true");
                lastCheckedPosition = position;
                lastCheckedPosition1 = Integer.parseInt(verifyUserDataa.get(position).getBuilding_id());
                notifyItemRangeChanged(0, verifyUserDataa.size());
                pos1 = lastCheckedPosition;
                tvTotal.setText(verifyUserDataa.get(position).getTotal_amount());
                spPaymentTowards.setAdapter(paymentAdapter);
               // spPaymentType.setAdapter(othersnameAdapter);

                Utils.WriteSharePrefrence(context, BUILDING_ID, verifyUserDataa.get(position).getBuilding_id());
                Utils.WriteSharePrefrence(context, TOTAL_AMT, verifyUserDataa.get(position).getTotal_amount());
                Utils.WriteSharePrefrence(context, INSTALLMENT_ID, verifyUserDataa.get(position).getInstallment_id());
                Utils.WriteSharePrefrence(context, WATERBILL_ID, verifyUserDataa.get(position).getWaterbill_id());
                Utils.WriteSharePrefrence(context, MEMBERSHIP_ID, verifyUserDataa.get(position).getFees_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return verifyUserDataa.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvProjectNm, tvBuildingNm, tvFloorNm, tvFlatNm;
        RadioButton rbSelect;
        RelativeLayout rlMain;
        LinearLayout llMain;

        public myViewHolder(final View itemView)
        {
            super(itemView);
            rlMain = itemView.findViewById(R.id.rl_main);
            llMain = itemView.findViewById(R.id.ll_main);
            tvProjectNm = itemView.findViewById(R.id.projectNm);
            tvBuildingNm = itemView.findViewById(R.id.blockNm);
            tvFloorNm = itemView.findViewById(R.id.FloorNm);
            tvFlatNm = itemView.findViewById(R.id.FlatNm);
            rbSelect = itemView.findViewById(R.id.radio_btn);
        }
    }

}

