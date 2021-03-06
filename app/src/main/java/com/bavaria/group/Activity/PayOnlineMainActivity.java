package com.bavaria.group.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.Model.verifyUserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PayOnlineMainActivity extends BaseAppCompactActivity {

    TextView payOnline_btnBack;
    ImageView payOnline_logout;
    EditText edtTxtCivilID;
    Button btnSubmit, btnPayGuest;
    LinearLayout payonline_llMain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payonline_main);
        initViews();
    }

    private void initViews() {
        payonline_llMain = findViewById(R.id.payonlinemain_ll);
        payOnline_btnBack = findViewById(R.id.payOnline_btnBack);
        payOnline_logout = findViewById(R.id.payOnline_logout);
        edtTxtCivilID = findViewById(R.id.edtTxtCivilID);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnPayGuest = findViewById(R.id.payGuest);

        payOnline_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        payOnline_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ClearSharePrefrence(PayOnlineMainActivity.this);
                Toast.makeText(PayOnlineMainActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnPayGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), GuestPayActivity.class);
                startActivity(in);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    new callVerifyUser().execute();
                }
            }
        });
        //  edtTxtCivilID.setText(Utils.ReadSharePrefrence(PayOnlineMainActivity.this, Constant.EMAIL));
    }

    public boolean isValid() {

        boolean isvalid = true;

        if (TextUtils.isEmpty(edtTxtCivilID.getText().toString())) {
            edtTxtCivilID.setError("Please Enter Valid Civil ID");
            isvalid = false;
        }

        return isvalid;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    @SuppressLint("StaticFieldLeak")
    public class callVerifyUser extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(PayOnlineMainActivity.this);
            pd.setMessage("Loading");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();

            data.put("civil_id", edtTxtCivilID.getText().toString());
            data.put("type", "ajax");
            data.put("action", "verifyuser");
            data.put("view", "json");
            Log.e("TAG", "" + Constant.NEW_BASE_URL + "/?" + data);
            String res = new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/?", data);
            Log.e("RESPONSE", "" + res);
            return res;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            pd.dismiss();
//            Log.e("RESPONSE",""+s);
            try {

                if (response.contains("error")) {
                    Snackbar.make(payonline_llMain, "Please Enter valid Civil id", Snackbar.LENGTH_SHORT).show();
                } else {
                    JSONArray jsonArray = new JSONArray(response);
                    ArrayList<verifyUserData> verifyUserDatas = new ArrayList<>();
                    JSONObject object = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        object = jsonArray.getJSONObject(i);

                        verifyUserData verifyUserDataa = new verifyUserData();
                        verifyUserDataa.setFees_id(object.getString("fees_id"));
                        verifyUserDataa.setFees(object.getString("fees"));
                        verifyUserDataa.setWaterbill_id(object.getString("waterbill_id"));
                        verifyUserDataa.setInstallment_id(object.getString("installment_id"));
                        verifyUserDataa.setBuilding_id(object.getString("building_id"));
                        verifyUserDataa.setBuilding_name(object.getString("building_name"));
                        verifyUserDataa.setFloor_name(object.getString("floor_name"));
                        verifyUserDataa.setFlat_name(object.getString("flat_name"));
                        verifyUserDataa.setTotal_amount(object.getString("total_amount"));
                        verifyUserDataa.setWater_bil(object.getString("water_bil"));
                        verifyUserDataa.setProject_name(object.getString("project_name"));
                        verifyUserDataa.setFirstname(object.getString("firstname"));
                        verifyUserDataa.setLastname(object.getString("lastname"));
                        verifyUserDatas.add(verifyUserDataa);
                    }

                    Utils.WriteSharePrefrence(PayOnlineMainActivity.this, Constant.CIVIT_ID, edtTxtCivilID.getText().toString());

                    Intent in = new Intent(getApplicationContext(), PayOnlineActivity.class);
                    in.putExtra("projectlist", verifyUserDatas);
                    in.putExtra("civil_id", edtTxtCivilID.getText().toString());
                    if (object != null) {
                        in.putExtra("name", object.getString("firstname") + object.getString("lastname"));
                    }
                    startActivity(in);
                    overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


