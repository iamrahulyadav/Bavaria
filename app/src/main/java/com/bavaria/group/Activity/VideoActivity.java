package com.bavaria.group.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bavaria.group.Adapter.VideoAdapter;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 3/11/2016.
 */
public class VideoActivity extends Activity {
    VideoAdapter adapter;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    ImageView icBackImg;
    ListView listView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        MultiDex.install(this);
        icBackImg = findViewById(R.id.activity_video_icBack_Img);
        listView = findViewById(R.id.activity_video_Lv);
        icBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, PhotoVideoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });
        adapter = new VideoAdapter(VideoActivity.this, dataList);
        if (Utils.isOnline(VideoActivity.this)) {
            new getVideoData().execute();
        } else {
            Toast.makeText(VideoActivity.this, "No internet connectivity found, Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    @SuppressLint("StaticFieldLeak")
    private class getVideoData extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(VideoActivity.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }


        @Override
        protected String doInBackground(String... params) {
//            StringBuilder response = new StringBuilder();
//            URL url = null;
//            try {
//                url = new URL(Constant.BASE_URL + "get_videos.php");
//                HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
//                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
////                    Log.d("StatusCode", "Done");
//                    BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()), 8192);
//                    String strLine = null;
//                    while ((strLine = input.readLine()) != null) {
//                        response.append(strLine);
//                    }
//                    input.close();
//                } else {
////                    Log.d("StatusCode", "NotDone");
//                }
//                Log.d("ResCode", String.valueOf(httpconn.getResponseCode()));

            return new MakeServiceCall().MakeServiceCall("https://www.bavariagroup.net/index.php?view=raw&page=videos&iview=json");


//            } catch (Exception e) {
//                Log.d("Exception", e.toString());
//            }
//            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
//            Log.e("Response", "" + s);
            try {
                JSONObject obj = new JSONObject(s);

                JSONArray jArray = obj.getJSONArray("data");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject object = jArray.getJSONObject(i);
                    HashMap<String, String> hm = new HashMap<>();
                    hm.put("id", "" + object.getString("id"));
                    hm.put("pro_name", "" + object.getString("pro_name"));
                    hm.put("pro_small_desc", "" + object.getString("pro_small_desc"));
                    hm.put("video", "" + object.getString("video"));
                    hm.put("video_image", "" + object.getString("video_image"));

                    dataList.add(hm);
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Log.e(">>>",""+e.toString());
            }
            adapter = new VideoAdapter(VideoActivity.this, dataList);
            listView.setAdapter(adapter);
        }
    }
}
