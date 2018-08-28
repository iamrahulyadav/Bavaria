package com.bavaria.group.Activity.myAccount;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Adapter.PaymenyHistoryAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PaymenyHistoryActivity extends BaseAppCompactActivity {

    RecyclerView recyclerView;
    PaymenyHistoryAdapter paymentHistoryAdapter;
    TextView ivBack;
    ImageView ivLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymeny_history);

        recyclerView = findViewById(R.id.paymenthistory_recyclerview);
        ivBack = findViewById(R.id.activity_paymenthistory_back);
        ivLogout = findViewById(R.id.ph_logout);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getPopupWindow(PaymenyHistoryActivity.this);

                /*Utils.ClearSharePrefrence(PaymenyHistoryActivity.this);
                Toast.makeText(PaymenyHistoryActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(PaymenyHistoryActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();*/
            }
        });

        new callPaymentHistory().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    @SuppressLint("StaticFieldLeak")
    public class callPaymentHistory extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(PaymenyHistoryActivity.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();

            data.put("view", "raw");
            data.put("page", "ph");
            data.put("iview", "json");
            data.put("id", Utils.ReadSharePrefrence(PaymenyHistoryActivity.this, Constant.CIVIT_ID));
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
//            Log.e("RESPONSE",""+s);
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {
//                    Toast.makeText(PaymenyHistoryActivity.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = object.getJSONArray("data");
                    recyclerView.setLayoutManager(new LinearLayoutManager(PaymenyHistoryActivity.this, LinearLayoutManager.VERTICAL, false));
                    paymentHistoryAdapter = new PaymenyHistoryAdapter(PaymenyHistoryActivity.this, jsonArray);
                    recyclerView.setAdapter(paymentHistoryAdapter);
                } else {
                    Toast.makeText(PaymenyHistoryActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
