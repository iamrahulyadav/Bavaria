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

import com.bavaria.group.Adapter.InstallmentAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.ApiClient;
import com.bavaria.group.retrofit.ApiInterface;
import com.bavaria.group.retrofit.Model.InstallmentDataPojo;
import com.bavaria.group.retrofit.Model.InstallmentPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstallmentActivity extends BaseAppCompactActivity {

    RecyclerView recyclerView;
    InstallmentAdapter installmentAdapter;
    TextView ivBack;
    ImageView logout;

    ArrayList<InstallmentDataPojo> installmentDataPojos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installment);

        recyclerView = (RecyclerView) findViewById(R.id.installment_recyclerview);
        ivBack = (TextView) findViewById(R.id.installment_btnBack);
        logout = (ImageView) findViewById(R.id.installment_logout);

        installmentDataPojos = new ArrayList<>();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getPopupWindow(InstallmentActivity.this);
               /* Utils.ClearSharePrefrence(InstallmentActivity.this);
                Toast.makeText(InstallmentActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(InstallmentActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();*/
            }
        });

        new callInstallment().execute();
    }

    public void callInstallment() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("view", "raw");
        data.put("page", "inst");
        data.put("iview", "json");
        data.put("id", Utils.ReadSharePrefrence(InstallmentActivity.this, Constant.CIVIT_ID));

        Call<InstallmentPojo> loginCall = apiInterface.installment(data);
        loginCall.enqueue(new Callback<InstallmentPojo>() {
            @Override
            public void onResponse(Call<InstallmentPojo> call, Response<InstallmentPojo> response) {

                if (response.body() != null) {

                    InstallmentPojo installmentDataa = response.body();
                    installmentDataPojos = installmentDataa.getData();

                    if (installmentDataa.getStatus().equalsIgnoreCase("true")) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(InstallmentActivity.this, LinearLayoutManager.VERTICAL, false));
                        installmentAdapter = new InstallmentAdapter(InstallmentActivity.this, installmentDataPojos);

                        recyclerView.setAdapter(installmentAdapter);
                    } else {
                        Toast.makeText(InstallmentActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<InstallmentPojo> call, Throwable t) {
                Toast.makeText(InstallmentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    public class callInstallment extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(InstallmentActivity.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("view", "raw");
            data.put("page", "inst");
            data.put("iview", "json");
            data.put("id", Utils.ReadSharePrefrence(InstallmentActivity.this, Constant.CIVIT_ID));

            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
//            Log.e("RESPONSE",""+s);
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("status").toString().equalsIgnoreCase("true"))
                {
                    JSONArray jsonArray = object.getJSONArray("data");
                    JSONObject explrObject = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        explrObject = jsonArray.getJSONObject(i);

                        InstallmentDataPojo installmentDataPojo = new InstallmentDataPojo();
                        installmentDataPojo.setProject_name(explrObject.getString("project_name"));
                        installmentDataPojo.setBuilding_name(explrObject.getString("building_name"));
                        installmentDataPojo.setFloor_name(explrObject.getString("floor_name"));
                        installmentDataPojo.setFlat_name(explrObject.getString("flat_name"));
                        installmentDataPojo.setBuilding_id(explrObject.getString("building_id"));
                        installmentDataPojo.setBill_id(explrObject.getString("bill_id"));
                        installmentDataPojo.setAmount(explrObject.getString("amount"));


                        installmentDataPojos.add(installmentDataPojo);

                        recyclerView.setLayoutManager(new LinearLayoutManager(InstallmentActivity.this, LinearLayoutManager.VERTICAL, false));
                        installmentAdapter = new InstallmentAdapter(InstallmentActivity.this, installmentDataPojos);

                        recyclerView.setAdapter(installmentAdapter);

                    }
                } else {
                    Toast.makeText(InstallmentActivity.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
