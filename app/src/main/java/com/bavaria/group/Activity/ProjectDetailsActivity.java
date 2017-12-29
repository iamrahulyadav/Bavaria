package com.bavaria.group.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Adapter.AvailabilityAdapter;
import com.bavaria.group.Adapter.ProjectImageAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.MainUtils;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.ApiClient;
import com.bavaria.group.retrofit.ApiInterface;
import com.bavaria.group.retrofit.Model.AvailabilityCheckData;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bavaria.group.Constant.Constant.SHRED_PR.KEY_CATID;

/**
 * Created by archirayan1 on 3/8/2016.
 */
public class ProjectDetailsActivity extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback
{
    public static String categoryName, categoryDesc, imagePath, catId, imgId, lat, lng, city, state, country;
    public static ArrayList<String> imgList;
    public String latlong;
    private ImageView ivBack, ivImagePro;
    private Button btCategory;
    private TextView tvCategoryDesc, tvState, tvProName;
    private RatingBar rating;
    private ProjectImageAdapter adapter;
    private GridView gvImage;
    private LinearLayout llProjectDetails, llProjectPics;
    private GoogleMap mMap;
    private Double latitude, longitude;
    public MainUtils mainUtils;
    String strName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        imgList = new ArrayList<String>();

        inIt();
        getDataFromIntent();
        mainUtils=new MainUtils(ProjectDetailsActivity.this);
        addListenerOnRatingBar();
        new getProjectDetailsData().execute();
        if (Utils.isOnline(getApplicationContext()))
        {
            if (!imgId.equalsIgnoreCase(""))
            {
                new getProjectPics().execute();
            } else {
                Toast.makeText(ProjectDetailsActivity.this, "No images found.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ProjectDetailsActivity.this, "No internet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void addListenerOnRatingBar()
    {
           rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
           {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser)
            {
            }
        });
    }

    private void inIt()
    {
        tvState = (TextView) findViewById(R.id.tvState_PropertyDetailsActivity);
        Button btnCheckAvability = (Button) findViewById(R.id.activity_project_details_avability);
        Button btnShareLocation = (Button) findViewById(R.id.activity_project_details_share_location);
        ivBack = (ImageView) findViewById(R.id.ivBack_ProjectDetailsActivity);
        btCategory = (Button) findViewById(R.id.btCategory_ProjectDetailsActivity);
        tvCategoryDesc = (TextView) findViewById(R.id.tvDescription_PropertyDetailsActivity);
        Button btPics = (Button) findViewById(R.id.btPics_ProjectDetailsActivity);
        rating = (RatingBar) findViewById(R.id.ratingBar_ProjectDetailsActivity);
        llProjectDetails = (LinearLayout) findViewById(R.id.llProjectDetails_ProjectDetailsActivity);
        llProjectPics = (LinearLayout) findViewById(R.id.llProjectPics_ProjectDetailsActivity);
        ivImagePro = (ImageView) findViewById(R.id.ivImage_ProjectDetailActivity);
        gvImage = (GridView) findViewById(R.id.gvImageList);
        ivBack.setOnClickListener(this);
        btPics.setOnClickListener(this);
        btCategory.setOnClickListener(this);
        btnCheckAvability.setOnClickListener(this);
        btnShareLocation.setOnClickListener(this);
        if (tvState.toString().equalsIgnoreCase(""))
        {
            tvState.setText("");
        } else {
            tvState.setText(Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_COUNTRY));
        }
    }

    private void getDataFromIntent()
    {


        catId = Utils.ReadSharePrefrence(ProjectDetailsActivity.this, KEY_CATID);
        imagePath = Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_IMAGE_PATH);
        categoryName =  Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_CAT_NAME);
        imgId = Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_IMAGE_ID);
        categoryDesc = Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_CAT_DESC);
        lat = Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_LAT);
        lng = Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_LNG);
        latlong = lat + lng;
        city = Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_CITY);
        state = Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_STATE);
        country = Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_COUNTRY);
        setData();
    }

    private void setData()
    {
        btCategory.setText(categoryName);
        tvCategoryDesc.setText(city);
        if (!imagePath.equalsIgnoreCase(""))
        {
            Glide.with(getApplicationContext()).load(imagePath).into(ivImagePro);
        }
        latitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lng);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ivBack_ProjectDetailsActivity:
                // intent = new Intent(getApplicationContext(), ProjectListaActivity.class);
                // startActivity(intent);
                finish();
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);

                break;
            case R.id.btCategory_ProjectDetailsActivity:
                llProjectPics.setVisibility(View.GONE);
                llProjectDetails.setVisibility(View.VISIBLE);
                break;
            case R.id.btPics_ProjectDetailsActivity:
                if (llProjectPics.getVisibility() == View.GONE)
                {
                    llProjectDetails.setVisibility(View.GONE);
                    llProjectPics.setVisibility(View.VISIBLE);
                            if(mainUtils.isConnectingToInternet(ProjectDetailsActivity.this)) {
                                new getProjectPics().execute();
                            }
                            else
                            {
                                Toast.makeText(ProjectDetailsActivity.this,"Please check internet connection.", Toast.LENGTH_SHORT).show();
                            }

                }
                break;
            case R.id.activity_project_details_avability:
                intent = new Intent(getApplicationContext(), Builiding.class);
                intent.putExtra(Constant.ProjectName, categoryName);
                Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_BLOCK_PROJECTNAME, categoryName);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                callAvailabilityCheck();
                break;

            case R.id.activity_project_details_share_location:
                intent = new Intent();
                // intent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.google.co.in/maps?q=loc:"+latlong));
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Bavaria location: https://www.google.co.in/maps?q=loc:" + latlong);
                intent.setType("text/plain");
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title(categoryName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
    }

    public void callAvailabilityCheck()
    {

        strName= Utils.ReadSharePrefrence(ProjectDetailsActivity.this, Constant.SHRED_PR.KEY_CAT_NAME);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("view", "raw");
        data.put("page", "projectcheck");
        data.put("iview", "json");
        data.put("project_name",strName );

        Call<ArrayList<AvailabilityCheckData>> loginCall = apiInterface.availabilityCheck(data);
        loginCall.enqueue(new Callback<ArrayList<AvailabilityCheckData>>() {
            @Override
            public void onResponse(Call<ArrayList<AvailabilityCheckData>> call, Response<ArrayList<AvailabilityCheckData>> response) {

                if (response.body() != null)
                {

                    ArrayList<AvailabilityCheckData> newarr = response.body();
                    AvailabilityAdapter availabilityAdapter = new AvailabilityAdapter(ProjectDetailsActivity.this, newarr);
                 //    Toast.makeText(ProjectDetailsActivity.this, "datafound", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<AvailabilityCheckData>> call, Throwable t)
            {
                Log.i("data","value");
              //  Toast.makeText(ProjectDetailsActivity.this, R.string.datanotfound, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    public class getProjectDetailsData extends AsyncTask<String, String, String> {
        private Dialog dialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new Dialog(ProjectDetailsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("id", catId);
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "property_pic.php", data);
        }

        @Override
        protected void onPostExecute(String s) {
//            Log.d("Response", "" + s);
            dialog.dismiss();
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("successful").equalsIgnoreCase("true")) {
                    Glide.with(ProjectDetailsActivity.this).load(object.getString("image")).into(ivImagePro);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getProjectPics extends AsyncTask<String, String, String> {
        private Dialog dialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new Dialog(ProjectDetailsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            imgList.clear();
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();

        }

        @Override
        protected String doInBackground(String... params)
        {
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "?view=raw&page=picturelist&iview=json&id=" +imgId);
        }

        @Override
        protected void onPostExecute(String s)
        {
            dialog.dismiss();
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("successful").equalsIgnoreCase("true"))
                {
                    JSONArray array = object.getJSONArray("image");
                    for (int i = 0; i < array.length(); i++)
                    {
                        imgList.add(array.getString(i));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(!imgList.isEmpty())
            {
                adapter = new ProjectImageAdapter(ProjectDetailsActivity.this, imgList);
                gvImage.setAdapter(adapter);
            }
            else
            {
               // Toast.makeText(ProjectDetailsActivity.this, "Image not available.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
