package com.bavaria.group.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bavaria.group.Adapter.FlatDetailsAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 5/23/2016.
 */
public class FlatDetailsActivity extends BaseAppCompactActivity {
    ArrayList<HashMap<String, String>> flatImgList;
    HashMap<String, String> hashMap;
    private FlatDetailsAdapter adapter;
    private ListView lvflat;
    private ImageView ivBack;
    private String floorname;
    private ImageView ivFirstImg, ivSecondImg;
    private String img1 = "", img2 = "";
    private String str_ImageShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_details);
        floorname = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_FLOOR_NAME);
        Bundle extras = getIntent().getExtras();
        str_ImageShow= (extras.getString("str_ImageShow"));
        init();

//        flatImgList = new ArrayList<HashMap<String, String>>();
//        flatImgList.clear();
        getDataFromIntent();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });


        ivFirstImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MultiTouchActivity.class);
                intent.putExtra("img", str_ImageShow);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

            }
        });

        ivSecondImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MultiTouchActivity.class);
                intent.putExtra("img", str_ImageShow);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

            }
        });


//        if (Utils.isOnline(getApplicationContext())) {
//            adapter = new FlatDetailsAdapter(FlatDetailsActivity.this, flatImgList);
//            lvflat.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        } else {
//            Toast.makeText(FlatDetailsActivity.this, "No internet connection found, Please check your internet connectivity.", Toast.LENGTH_SHORT).show();
//        }
    }

    private void init() {
//        lvflat = (ListView) findViewById(R.id.lvFlatImage_FlateDetailsAdapter);
        ivBack = (ImageView) findViewById(R.id.ivBack_FlatDetailsActivity);
        ivFirstImg = (ImageView) findViewById(R.id.ivFirstImg);
        ivSecondImg = (ImageView) findViewById(R.id.ivSecondImg);

        if(Picasso.with(FlatDetailsActivity.this).load(str_ImageShow)!=null)
        {
            Picasso.with(FlatDetailsActivity.this).load(str_ImageShow).placeholder(R.drawable.default_img).into(ivFirstImg);
        }
    }

    private void getDataFromIntent()
    {
        img1 = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IMG1);
        img2 = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IMG2);

//        hashMap = new HashMap<String, String>();
//        Bundle extra = getIntent().getExtras();
//        img1 = extra.getString("img1");
//        hashMap.put("img1", img1);
      //  Glide.with(getApplicationContext()).load(str_ImageShow).into(ivFirstImg);

        Log.e("img1", "" + img1);

        if (!img2.equalsIgnoreCase("")) {
            Log.e("1111111", "******* " + img2);
            ivSecondImg.setVisibility(View.VISIBLE);
            Picasso.with(this).load(img2).placeholder(R.drawable.default_img).into(ivSecondImg);
          //  Glide.with(getApplicationContext()).load(img2).into(ivSecondImg);
//            hashMap.put("img2", img2);
        } else {
            ivSecondImg.setVisibility(View.GONE);
        }
//        flatImgList.add(hashMap);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

}
