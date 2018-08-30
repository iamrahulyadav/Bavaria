package com.bavaria.group.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Activity.myAccount.PaymenyHistoryActivity;
import com.bavaria.group.Activity.myAccount.SupportActivity;
import com.bavaria.group.Activity.myAccount.UpdateProfileActivity;
import com.bavaria.group.Activity.myAccount.WaterbillActivity;
import com.bavaria.group.Activity.myAccount.YearlyMembershipActivity;
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

/**
 * Created by archirayan1 on 3/4/2016.
 */
public class MyAccountActivity extends BaseAppCompactActivity implements View.OnClickListener {
    ImageView tvSignout;
    LinearLayout llYearlyMembership, llInstallment, llWaterBill, llPaymenyHistory, llOpenTicket, llViewTicket;
    //    https://bavariagroup.net/en/index.php/en/component/users/?view=login
    private TextView tvName;
    String strCivilId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        tvName = findViewById(R.id.myAcc_tvName);
        TextView tvEmail = findViewById(R.id.myAcc_tvEmail);
        llYearlyMembership = findViewById(R.id.myAcc_llYearlyMembership);
        llInstallment = findViewById(R.id.myAcc_llInstallment);
        llWaterBill = findViewById(R.id.myAcc_llWaterBill);
        llPaymenyHistory = findViewById(R.id.myAcc_llPaymentHistory);
        llOpenTicket = findViewById(R.id.myAcc_llSupport);
        llViewTicket = findViewById(R.id.myAcc_llChangePassword);
        TextView tvBack = findViewById(R.id.myAcc_btnBack);

        tvSignout = findViewById(R.id.myAcc_logout);
        tvName.setText(Utils.ReadSharePrefrence(MyAccountActivity.this, Constant.USERNAME));
        tvEmail.setText(Utils.ReadSharePrefrence(MyAccountActivity.this, Constant.EMAIL));

        llYearlyMembership.setOnClickListener(this);
        llInstallment.setOnClickListener(this);
        llWaterBill.setOnClickListener(this);
        llPaymenyHistory.setOnClickListener(this);
        llOpenTicket.setOnClickListener(this);
        llViewTicket.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        tvSignout.setOnClickListener(this);

        strCivilId = Utils.ReadSharePrefrence(MyAccountActivity.this, Constant.EMAIL);
//        new callUserProfile().execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myAcc_llYearlyMembership:
                Intent membershipIntent = new Intent(MyAccountActivity.this, YearlyMembershipActivity.class);
                startActivity(membershipIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.myAcc_llInstallment:
              /*  Intent installmentIntent = new Intent(MyAccountActivity.this, PayOnlineActivity.class);
                startActivity(installmentIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);*/
                new callVerifyUser().execute();

                break;

            case R.id.myAcc_llWaterBill:
                Intent waterbillIntent = new Intent(MyAccountActivity.this, WaterbillActivity.class);
                startActivity(waterbillIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.myAcc_llPaymentHistory:
                Intent paymenyhistoryIntent = new Intent(MyAccountActivity.this, PaymenyHistoryActivity.class);
                startActivity(paymenyhistoryIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.myAcc_llSupport:
                Intent openticketIntent = new Intent(MyAccountActivity.this, SupportActivity.class);
                startActivity(openticketIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.myAcc_llChangePassword:
                Intent chngePassIntent = new Intent(MyAccountActivity.this, UpdateProfileActivity.class);
                startActivity(chngePassIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.myAcc_btnBack:
                Intent backIntent = new Intent(MyAccountActivity.this, MainActivity.class);
                startActivity(backIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.myAcc_logout:
                Utils.getPopupWindow(MyAccountActivity.this);

                /*Utils.ClearSharePrefrence(MyAccountActivity.this);

                Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
                finish();*/

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    @SuppressLint("StaticFieldLeak")
    public class callUserProfile extends AsyncTask<String, String, String> {
        Dialog dialog;
        String id;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new Dialog(MyAccountActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            ImageView loader = dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();

        }


        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();
            data.put("view", "raw");
            data.put("iaction", "user_profile");
            data.put("civil_id", Utils.ReadSharePrefrence(MyAccountActivity.this, Constant.CIVIT_ID));

            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            Log.e("Response", "" + s);
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("successful").equalsIgnoreCase("true")) {

                    //     tvEmail.setText(object.getString("email"));
                    tvName.setText(object.getString("display_name"));
                } else {
                    Toast.makeText(MyAccountActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class callVerifyUser extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MyAccountActivity.this);
            pd.setMessage("Loading");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();

            data.put("civil_id", Utils.ReadSharePrefrence(MyAccountActivity.this, Constant.CIVIT_ID));
            data.put("type", "ajax");
            data.put("action", "verifyuser");
            data.put("view", "json");
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
//            Log.e("RESPONSE",""+s);
            try {
                JSONArray jsonArray = new JSONArray(s);
//                if (object.getString("status").toString().equalsIgnoreCase("true")) {
//                    Toast.makeText(PayOnlineMainActivity.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();
//
                ArrayList<verifyUserData> verifyUserDatas = new ArrayList<>();
                JSONObject object;
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

                    verifyUserDatas.add(verifyUserDataa);

                }

                Intent in = new Intent(getApplicationContext(), PayOnlineActivity.class);
                in.putExtra("projectlist", verifyUserDatas);
                in.putExtra("civil_id", strCivilId);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
//                } else {
//                    Toast.makeText(PayOnlineMainActivity.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();
//                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
