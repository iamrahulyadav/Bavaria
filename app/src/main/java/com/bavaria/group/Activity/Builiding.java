package com.bavaria.group.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bavaria.group.Adapter.AvailabilityAdapter;
import com.bavaria.group.Adapter.BuilidingAdapter;
import com.bavaria.group.Constant.ConnectionDetector;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.MainUtils;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.ApiClient;
import com.bavaria.group.retrofit.ApiInterface;
import com.bavaria.group.retrofit.Model.AvailabilityCheckData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by archirayan on 4/18/2016.
 */
public class Builiding extends BaseAppCompactActivity {
    String projectname;
    ArrayList<HashMap<String, String>> projectList;
    private ListView lvProjectList;
    private ImageView ivBack;
    ArrayList<AvailabilityCheckData> newarr;
    public MainUtils utils;
   public BuilidingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        ivBack = (ImageView) findViewById(R.id.activity_building_list_back);
        lvProjectList = (ListView) findViewById(R.id.activity_building_list_list);
        projectList=new ArrayList<HashMap<String, String>>();
        newarr = new ArrayList<>();
        utils = new MainUtils(Builiding.this);
        Intent intent = getIntent();
        projectname = intent.getStringExtra(Constant.ProjectName);
       // Utils.WriteSharePrefrence(Builiding.this,Constant.ProjectEmpName,projectname);
        Log.d("projectname", projectname);
        new getBuildingList().execute();


        ivBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Builiding.this, ProjectDetailsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });
    }

    private void getDataFromIntent()
    {
        /*Intent intent = getIntent();
        projectname = intent.getStringExtra(Constant.ProjectName);
        Utils.WriteSharePrefrence(Builiding.this,Constant.ProjectEmpName,projectname);*/

        //projectname = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_BLOCK_PROJECTNAME);
    }

    private class getBuildingList extends AsyncTask<String, String, String>
    {
        Dialog dialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            dialog = new Dialog(Builiding.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();

        }

        @Override
        protected String doInBackground(String... params)
        {
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?view=raw&page=projectcheck&iview=json&project_name=" +projectname.replaceAll(" ", "%20"));

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            Log.d("projectname", s);

            HashMap<String, String> hashMap = null;
            try {
                JSONArray array = new JSONArray(s.toString());
                for (int i = 0; i < array.length(); i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    hashMap = new HashMap<String, String>();
                    hashMap.put("building_name", "" + object.getString("building_name"));

//                    hashMap.put("image", "" + object.getString("image"));
                    projectList.add(hashMap);
                    adapter = new BuilidingAdapter(Builiding.this, projectList,projectname);
                    lvProjectList.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
           /* if(projectList!=null)
            {
                 adapter = new BuilidingAdapter(Builiding.this, projectList,projectname);
                lvProjectList.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(Builiding.this, R.string.datanotfound, Toast.LENGTH_SHORT).show();
            }*/
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

   /* public void callAvailabilityCheck()
    {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("view", "raw");
        data.put("page", "projectcheck");
        data.put("iview", "json");
        data.put("project_name", projectname);

        Call<ArrayList<AvailabilityCheckData>> loginCall = apiInterface.availabilityCheck(data);
        loginCall.enqueue(new Callback<ArrayList<AvailabilityCheckData>>() {
            @Override
            public void onResponse(Call<ArrayList<AvailabilityCheckData>> call, Response<ArrayList<AvailabilityCheckData>> response) {

                if (response.body() != null)
                {
                    newarr = response.body();
                    lvProjectList.setLayoutManager(new LinearLayoutManager(Builiding.this, LinearLayoutManager.VERTICAL, false));
                    AvailabilityAdapter availabilityAdapter = new AvailabilityAdapter(Builiding.this, newarr);
                    lvProjectList.setAdapter(availabilityAdapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AvailabilityCheckData>> call, Throwable t) {
                Toast.makeText(Builiding.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

}
