package com.bavaria.group.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.squareup.picasso.Picasso;

/**
 * Created by archirayan1 on 5/23/2016.
 */
public class FlatDetailsActivity extends BaseAppCompactActivity {

    private ImageView ivBack;
    private ImageView ivFirstImg, ivSecondImg;
    private String str_ImageShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_details);
//        String floorname = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_FLOOR_NAME);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_ImageShow = (extras.getString("str_ImageShow"));
        }
        init();

//        flatImgList = new ArrayList<HashMap<String, String>>();
//        flatImgList.clear();
        getDataFromIntent();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });


        ivFirstImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlatDetailsActivity.this, MultiTouchActivity.class);
                intent.putExtra("image", str_ImageShow);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

            }
        });

        ivSecondImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlatDetailsActivity.this, MultiTouchActivity.class);
                intent.putExtra("image", str_ImageShow);
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
        ivBack = findViewById(R.id.ivBack_FlatDetailsActivity);
        ivFirstImg = findViewById(R.id.ivFirstImg);
        ivSecondImg = findViewById(R.id.ivSecondImg);

        if (Picasso.with(FlatDetailsActivity.this).load(str_ImageShow) != null) {
            Picasso.with(FlatDetailsActivity.this).load(str_ImageShow).placeholder(R.drawable.default_img).into(ivFirstImg);
        }
    }

    private void getDataFromIntent() {
        String img1 = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IMG1);
        String img2 = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IMG2);

//        hashMap = new HashMap<String, String>();
//        Bundle extra = getIntent().getExtras();
//        img1 = extra.getString("img1");
//        hashMap.put("img1", img1);
        //  Glide.with(getApplicationContext()).load(str_ImageShow).into(ivFirstImg);

        Log.e("img1", "" + img1);

        if (!img2.equalsIgnoreCase("")) {
            Log.e("1111111", "******* " + img2);
            ivSecondImg.setVisibility(View.VISIBLE);
            Picasso.with(this).load(img2).placeholder(R.drawable.default_img).fit().into(ivSecondImg);
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

//
//    public static Bitmap getBitmapFromURL(String src) {
//        try {
//            Log.e("src",src);
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            Log.e("Bitmap","returned");
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("Exception",e.getMessage());
//            return null;
//        }
//    }

}
