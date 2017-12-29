package com.bavaria.group.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Activity.BlockActivity;
import com.bavaria.group.Activity.FlatDetailsActivity;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.bavaria.group.Constant.Constant.SHRED_PR.IMAGESHOW_BLOCK;

/**
 * Created by archirayan1 on 5/17/2016.
 */
public class BlockAdapter extends BaseAdapter {
    Context mContext;
    Map<String, List<String>> floorList;
    ArrayList<Integer> keyList;
    String buildingName;
    Map<String, HashMap<String, String>> imgList;
    ArrayList<String> keyImgList;

    String img1 = "";
    String img2 = "";
    private LayoutInflater inflater = null;
    private String str_ImageShow;

    /*   public BlockAdapter(Context mCtx, Map<String, List<String>> list, ArrayList<Integer> keyList, Map<String, HashMap<String, String>> imageList, ArrayList<String> keyImgList, ArrayList<String> keyImgLst) {
           }
   */
    public BlockAdapter(BlockActivity blockActivity, Map<String, List<String>> floorList, ArrayList<Integer> keyList, Map<String, HashMap<String, String>> imageList, ArrayList<String> keyImgList) {
        this.mContext = blockActivity;
        this.floorList = floorList;
        this.keyList = keyList;
        this.imgList = imageList;
        this.keyImgList = keyImgList;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return floorList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // inflate the layout
        view = inflater.inflate(R.layout.item_floor_details, parent, false);
        // well set up the ViewHolder
        TextView tvFloorName = (TextView) view.findViewById(R.id.tvFloorname);
        ImageView ivBuilding = (ImageView) view.findViewById(R.id.item_project_list_Img);
        View vwDivider = (View) view.findViewById(R.id.vwDivider_FloorAdapter);
        LinearLayout llfloorBox = (LinearLayout) view.findViewById(R.id.llFloorBox_FloorAdapter);

        tvFloorName.setText("Floor" + keyList.get(position));

        for (Map.Entry<String, List<String>> entry : floorList.entrySet()) {

            if (keyList.get(position).toString().equalsIgnoreCase(entry.getKey())) {
                List<String> values = entry.getValue();
                final List<String> al = new ArrayList<>();
                Set<String> hs = new HashSet<>();
                hs.addAll(values);
                al.clear();
                al.addAll(hs);

                Collections.sort(al, new Comparator<String>()
                {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });

                llfloorBox.setWeightSum(al.size());
                for (int i = 0; i < al.size(); i++) {

                    final TextView tvFloorBox = new TextView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                    params.setMargins(10, 0, 10, 0);

                    tvFloorBox.setLayoutParams(params);
                    tvFloorBox.setGravity(Gravity.CENTER);
                    tvFloorBox.setTextColor(mContext.getResources().getColor(R.color.white));
                    final String floorName = al.get(i);
                    String combine = keyList.get(position).toString() + floorName;


                    for (Map.Entry<String, HashMap<String, String>> entry1 : imgList.entrySet()) {
                        if (entry1.getKey().equalsIgnoreCase(combine)) {
                            HashMap<String, String> data = entry1.getValue();
//                                Log.e("******", "*****  START  ******");
//                                Log.e("K >> ", "" + entry1.getKey());
//                                Log.e("V >AV> ", "" + data.get("Available"));
                            tvFloorBox.setText(data.get("flat_name"));
                            if (data.get("Available").equalsIgnoreCase("yes")) {
                                tvFloorBox.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.floor_box_bg_green));
                            } else if (data.get("Available").equalsIgnoreCase("no")) {
                                tvFloorBox.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.floor_box_bg_red));
                            } else {
                                tvFloorBox.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.floor_box_bg_green));
                            }
                        } else {

                        }
                    }

//                    }

//                    if(imgList.get(i).get(key).equalsIgnoreCase(key)){
//                        Log.e("IMg"+keyList.get(position).toString()+floorName,""+);
//
//                        tvFloorBox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.floor_box_bg_green));
//                    }

                    llfloorBox.addView(tvFloorBox);

                    tvFloorBox.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                                str_ImageShow=Utils.ReadSharePrefrence(mContext,IMAGESHOW_BLOCK);
                                Utils.WriteSharePrefrence(mContext, Constant.SHRED_PR.KEY_FLOOR_NAME, floorName);
                                Utils.WriteSharePrefrence(mContext, Constant.SHRED_PR.KEY_IMG1, img1);
                                Utils.WriteSharePrefrence(mContext, Constant.SHRED_PR.KEY_IMG2, img2);
                                Intent intent = new Intent(mContext, FlatDetailsActivity.class);
                                intent.putExtra("str_ImageShow",str_ImageShow);
                                mContext.startActivity(intent);
                        }
                    });
                }
            }
        }
        if (Utils.ReadSharePrefrence(mContext, Constant.SHRED_PR.KEY_IS_YES_OR_NO).equalsIgnoreCase("1")) {
            int pos = position + 1;
            if (pos % 2 == 0) {
                vwDivider.setVisibility(View.VISIBLE);
            } else {
                vwDivider.setVisibility(View.GONE);
            }
        }


        return view;
    }
}