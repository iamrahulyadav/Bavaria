package com.bavaria.group.Activity.myAccount;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Adapter.WaterBillAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.ApiClient;
import com.bavaria.group.retrofit.ApiInterface;
import com.bavaria.group.retrofit.Model.WaterBillDataPojo;
import com.bavaria.group.retrofit.Model.WaterBillPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaterbillActivity extends BaseAppCompactActivity {

    RecyclerView recyclerView;
    WaterBillAdapter waterBillAdapter;
    TextView ivBack;
    ImageView ivLogout;

    ArrayList<WaterBillDataPojo> waterBillDataPojos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterbill);

        recyclerView = (RecyclerView) findViewById(R.id.waterbill_recyclerview);
        ivBack = (TextView) findViewById(R.id.waterbill_btnBack);
        ivLogout = (ImageView) findViewById(R.id.waterbill_logout);

        waterBillDataPojos = new ArrayList<>();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Utils.getPopupWindow(WaterbillActivity.this);
            }
        });

        new callWaterBill().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    public void callWaterBill() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("view", "raw");
        data.put("page", "wb");
        data.put("iview", "json");
        data.put("id", Utils.ReadSharePrefrence(WaterbillActivity.this, Constant.CIVIT_ID));

        Call<WaterBillPojo> loginCall = apiInterface.waterBill(data);
        loginCall.enqueue(new Callback<WaterBillPojo>() {
            @Override
            public void onResponse(Call<WaterBillPojo> call, Response<WaterBillPojo> response) {

                if (response.body() != null) {

                    WaterBillPojo waterBillPojo = response.body();
                    waterBillDataPojos = waterBillPojo.getData();

                    if (waterBillPojo.getStatus().equalsIgnoreCase("true")) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(WaterbillActivity.this, LinearLayoutManager.VERTICAL, false));
                        waterBillAdapter = new WaterBillAdapter(WaterbillActivity.this, waterBillDataPojos);

                        recyclerView.setAdapter(waterBillAdapter);
                    }
                } else {
                    Toast.makeText(WaterbillActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WaterBillPojo> call, Throwable t) {
                Toast.makeText(WaterbillActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class callWaterBill extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(WaterbillActivity.this);
            pd.setMessage("Loading");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("view", "raw");
            data.put("page", "wb");
            data.put("iview", "json");
            data.put("id", Utils.ReadSharePrefrence(WaterbillActivity.this, Constant.CIVIT_ID));
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {

                    JSONArray jsonArray = object.getJSONArray("data");
                    JSONObject explrObject = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        explrObject = jsonArray.getJSONObject(i);

                        WaterBillDataPojo waterBillDataPojo = new WaterBillDataPojo();
                        waterBillDataPojo.setProject_name(explrObject.getString("project_name"));
                        waterBillDataPojo.setBuilding_name(explrObject.getString("building_name"));
                        waterBillDataPojo.setFloor_name(explrObject.getString("floor_name"));
                        waterBillDataPojo.setFlat_name(explrObject.getString("flat_name"));
                        waterBillDataPojo.setExpiry_date(explrObject.getString("Expiry_date"));
                        waterBillDataPojo.setBuilding_id(explrObject.getString("building_id"));
                        waterBillDataPojo.setBill_id(explrObject.getString("bill_id"));
                        waterBillDataPojo.setAmount(explrObject.getString("amount"));
                        waterBillDataPojos.add(waterBillDataPojo);
                        recyclerView.setLayoutManager(new LinearLayoutManager(WaterbillActivity.this, LinearLayoutManager.VERTICAL, false));
                        waterBillAdapter = new WaterBillAdapter(WaterbillActivity.this, waterBillDataPojos);
                        recyclerView.setAdapter(waterBillAdapter);
                    }

                } else {
                    Toast.makeText(WaterbillActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
