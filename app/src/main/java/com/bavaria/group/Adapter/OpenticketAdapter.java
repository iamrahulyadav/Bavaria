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
import android.widget.Toast;

import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.Model.verifyUserData;

import java.util.ArrayList;

import static com.bavaria.group.Constant.Constant.BUILDING;

/**
 * Created by Archirayan on 17-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */


public class OpenticketAdapter extends RecyclerView.Adapter<OpenticketAdapter.myViewHolder>
{

    Context context;
    ArrayList<verifyUserData> verifyUserDataa;
    private int lastCheckedPosition = -1;

    public OpenticketAdapter(Context context, ArrayList<verifyUserData> verifyUserDataa) {
        this.context = context;
        this.verifyUserDataa = verifyUserDataa;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.openticket_item_layout, parent, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        holder.tvProjectNm.setText(verifyUserDataa.get(position).getProject_name());
        holder.tvBuildingNm.setText(verifyUserDataa.get(position).getBuilding_name());
        holder.tvFloorNm.setText(verifyUserDataa.get(position).getFloor_name());
        holder.tvFlatNm.setText(verifyUserDataa.get(position).getFlat_name());

        if (Integer.parseInt(verifyUserDataa.get(position).getBuilding_id()) == lastCheckedPosition) {
            holder.rbSelect.setChecked(true);
        } else {
            holder.rbSelect.setChecked(false);
        }
        holder.rbSelect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lastCheckedPosition = Integer.parseInt(verifyUserDataa.get(position).getBuilding_id());
                notifyItemRangeChanged(0, verifyUserDataa.size());
                Utils.WriteSharePrefrence(context, BUILDING, verifyUserDataa.get(position).getBuilding_id());
            }
        });


        holder.layot_data.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                lastCheckedPosition = Integer.parseInt(verifyUserDataa.get(position).getBuilding_id());
                notifyItemRangeChanged(0, verifyUserDataa.size());
                Utils.WriteSharePrefrence(context, BUILDING, verifyUserDataa.get(position).getBuilding_id());
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
        RelativeLayout layot_data;
        // ImageView imgDotIndicator;

        public myViewHolder(final View itemView) {
            super(itemView);

            tvProjectNm = (TextView) itemView.findViewById(R.id.projectNm);
            tvBuildingNm = (TextView) itemView.findViewById(R.id.blockNm);
            tvFloorNm = (TextView) itemView.findViewById(R.id.FloorNm);
            tvFlatNm = (TextView) itemView.findViewById(R.id.FlatNm);
            rbSelect = (RadioButton) itemView.findViewById(R.id.radio_btn);
            layot_data= (RelativeLayout) itemView.findViewById(R.id.layot_data);
            //   imgDotIndicator = (ImageView) itemView.findViewById(R.id.pageDotIndicatorImg);
        }
    }

}
