package com.bavaria.group;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bavaria.group.Activity.PhotoVideoActivity;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.PhotoAdapter;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {

    public Photosetget photosetget;
    public ArrayList<Photosetget> dataList;
    private ListView lvProjectList;
    private ImageView ivBack;
    private PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        lvProjectList = findViewById(R.id.lvProjectList_ProjectListActivity);
        ivBack = findViewById(R.id.ivBack_PhotoVideoListActivity);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotoVideoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });

        if (Utils.isOnline(getApplicationContext())) {
            new getPhotoAndVideoData().execute();
        } else {
            Toast.makeText(PhotoActivity.this, "No internet connectivity found, Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    public class getPhotoAndVideoData extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(PhotoActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            ImageView loader = dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();
            dataList = new ArrayList();

        }

        @Override
        protected String doInBackground(String... params) {
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?view=raw&page=projects&iview=json");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            try {
                JSONArray jArray = new JSONArray(s);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    photosetget = new Photosetget();
                    photosetget.setStr_id(jObj.getString("pid"));
                    photosetget.setStr_name(jObj.getString("category_name"));
                    photosetget.setStr_image(jObj.getString("category_image"));
                    dataList.add(photosetget);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (dataList != null) {
                adapter = new PhotoAdapter(PhotoActivity.this, dataList);
                lvProjectList.setAdapter(adapter);
            } else {
                Toast.makeText(PhotoActivity.this, "Image not available", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
