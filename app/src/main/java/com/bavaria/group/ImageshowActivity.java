package com.bavaria.group;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bavaria.group.Activity.PhotoVideoListActivity;
import com.bavaria.group.Activity.PhotoVideoPicsActivity;
import com.bavaria.group.Adapter.PhotoVideoImageAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.MainUtils;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageshowActivity extends AppCompatActivity {
    public static ArrayList<String> imgList;
    private ImageView ivBack;
    private GridView gvPicsList;
    private PhotoVideoImageAdapter adapter;
    private String imgId = "";
    private String category_id = "";
    private String[] catelogueImagelist;
    private String status;
    public MainUtils mainUtils;
    private String str_ImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageshow);
        ivBack = (ImageView) findViewById(R.id.ivBack_PhotoVideoPicsActivity);
        gvPicsList = (GridView) findViewById(R.id.gvImage_PhotoVideoPicsActivity);
        Bundle extras = getIntent().getExtras();
        mainUtils = new MainUtils(ImageshowActivity.this);
        if (extras != null) {
            imgId = extras.getString("pid");
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });

        if (Utils.isOnline(getApplicationContext())) {
            new getPhotoShow().execute();
        } else {
            Toast.makeText(ImageshowActivity.this, "No internet connectivity found, Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private class getPhotoShow extends AsyncTask<String, String, String>
    {
        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgList=new ArrayList<>();
            pd = new ProgressDialog(ImageshowActivity.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String str_response = mainUtils.getResponseofGet(Constant.BASE_URL + "index.php?view=raw&page=picturelist&iview=json&id=" + imgId);
            return str_response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try
            {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("successful").equalsIgnoreCase("true"))
                {
                    JSONArray array = object.getJSONArray("image");
                    for (int i = 0; i < array.length(); i++)
                    {
                        imgList.add(array.getString(i));
                    }
                }else
                {
                    Toast.makeText(ImageshowActivity.this, "Image not available", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (imgList != null)
            {
                adapter = new PhotoVideoImageAdapter(ImageshowActivity.this, imgList);
                gvPicsList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(ImageshowActivity.this,"Image not available", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
