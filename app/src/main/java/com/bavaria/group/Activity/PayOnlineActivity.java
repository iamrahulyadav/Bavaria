package com.bavaria.group.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Activity.myAccount.PayNowActivity;
import com.bavaria.group.Adapter.PayOnlineAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.Model.GetOthersNameClass;
import com.bavaria.group.retrofit.Model.verifyUserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.bavaria.group.Adapter.PayOnlineAdapter.pos1;
import static com.bavaria.group.Adapter.PayOnlineAdapter.verifyUserDataa;
import static com.bavaria.group.Constant.Constant.BUILDING_ID;
import static com.bavaria.group.Constant.Constant.CHECK_CLICK;
import static com.bavaria.group.Constant.Constant.FLAT_NAME;
import static com.bavaria.group.Constant.Constant.FLOOR_NAME;
import static com.bavaria.group.Constant.Constant.INSTALLMENT_ID;
import static com.bavaria.group.Constant.Constant.MEMBERSHIP_ID;
import static com.bavaria.group.Constant.Constant.PROJECT_NAME;
import static com.bavaria.group.Constant.Constant.WATERBILL_ID;

/**
 * Created by archirayan1 on 3/2/2016.
 */

// implements View.OnClickListener, AdapterView.OnItemSelectedListener
public class PayOnlineActivity extends BaseAppCompactActivity implements View.OnClickListener {

    public static ArrayAdapter<String> paymentAdapter;
    public static ArrayAdapter<String> othersnameAdapter;
    @SuppressLint("StaticFieldLeak")
    public static TextView tvPAyNow, tvTotal;
    @SuppressLint("StaticFieldLeak")
    public static Spinner spPaymentTowards, spPaymentType;
    Intent intent;
    ArrayList<verifyUserData> verifyUserData;
    String civilid;
    PayOnlineAdapter payOnlineAdapter;
    RecyclerView recyclerView;
    EditText etAmt;
    TextView tvBack;
    ImageView ivLogout;
    String bill_id = "";
    LinearLayout payonline_llMain, payonline_llpaymentType, payonline_llpaymentTotal;
    String name;
    String strBuildingId;
    String strPaymentType;
    String[] paymentTowards = {"Yearly Maintenance", "Water Bill", "Other"};
    ArrayList<String> othersNameArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payonline);

        intent = getIntent();
        verifyUserData = (ArrayList<verifyUserData>) intent.getSerializableExtra("projectlist");
        civilid = intent.getStringExtra("civil_id");
        name = intent.getStringExtra("name");

        etAmt = findViewById(R.id.payonline_etAmount);
        tvPAyNow = findViewById(R.id.payonline_tvSubmit);
        tvTotal = findViewById(R.id.payonline_tvTotalAmt);
        tvBack = findViewById(R.id.payonline_btnBack);
        ivLogout = findViewById(R.id.payonline_ivLogout);
        recyclerView = findViewById(R.id.payonline_recyclerview);
        spPaymentTowards = findViewById(R.id.payonline_spPriority);
        spPaymentType = findViewById(R.id.payonline_spPaymentType);
        payonline_llMain = findViewById(R.id.payonline_llMain);
        payonline_llpaymentType = findViewById(R.id.ll_paymentType);
        payonline_llpaymentTotal = findViewById(R.id.ll_paymentTotal);

        othersNameArraylist = new ArrayList<>();

        PayOnlineAdapter.lastCheckedPosition = -1;
        PayOnlineAdapter.lastCheckedPosition1 = -1;

        spPaymentTowards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (spPaymentTowards.getSelectedItem().toString().equalsIgnoreCase("Yearly Maintenance")) {

                    payonline_llpaymentTotal.setVisibility(View.VISIBLE);
                    payonline_llpaymentType.setVisibility(View.GONE);

                    tvTotal.setText(verifyUserDataa.get(pos1).getFees());
                    othersNameArraylist.clear();

                    strPaymentType = spPaymentTowards.getSelectedItem().toString();
                    othersnameAdapter = new ArrayAdapter<>(PayOnlineActivity.this, R.layout.spinner_item_layout, R.id.txt, othersNameArraylist);

                    spPaymentType.setAdapter(othersnameAdapter);

                } else if (spPaymentTowards.getSelectedItem().toString().equalsIgnoreCase("Water Bill")) {

                    payonline_llpaymentTotal.setVisibility(View.VISIBLE);
                    payonline_llpaymentType.setVisibility(View.GONE);

                    tvTotal.setText(verifyUserDataa.get(pos1).getWater_bil());
                    othersNameArraylist.clear();
                    strPaymentType = spPaymentTowards.getSelectedItem().toString();

                    othersnameAdapter = new ArrayAdapter<>(PayOnlineActivity.this, R.layout.spinner_item_layout, R.id.txt, othersNameArraylist);
                    spPaymentType.setAdapter(othersnameAdapter);

                } else if (spPaymentTowards.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                    payonline_llpaymentTotal.setVisibility(View.GONE);

                    payonline_llpaymentType.setVisibility(View.VISIBLE);
                    //  tvTotal.setText(verifyUserDataa.get(pos1).getFees());
                    new GetOtherName().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        strBuildingId = Utils.ReadSharePrefrence(PayOnlineActivity.this, BUILDING_ID);

        paymentAdapter = new ArrayAdapter<>(PayOnlineActivity.this, R.layout.spinner_item_layout, R.id.txt, paymentTowards);
        spPaymentTowards.setAdapter(paymentAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(PayOnlineActivity.this, LinearLayoutManager.HORIZONTAL, false));
        payOnlineAdapter = new PayOnlineAdapter(PayOnlineActivity.this, verifyUserData);
        recyclerView.setAdapter(payOnlineAdapter);

        ivLogout.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        tvPAyNow.setOnClickListener(this);

        spPaymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // String code1 = othersnameAdapter.getItem(position).toString();
                strPaymentType = spPaymentType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

//            case R.id.payOnline_btnBack:
//                onBackPressed();
//                break;

            case R.id.payonline_ivLogout:
                Utils.getPopupWindow(PayOnlineActivity.this);
                break;

            case R.id.payonline_btnBack:
                finish();
                break;

            case R.id.payonline_tvSubmit:
                String STR_CLICK = Utils.ReadSharePrefrence(PayOnlineActivity.this, CHECK_CLICK);
                if (!etAmt.getText().toString().equals("")) {
                    if (!etAmt.getText().toString().equals("0")) {
                        Log.e("Clicked", "button" + STR_CLICK);
                        if (STR_CLICK.equals("true")) {

                            if (spPaymentTowards.getSelectedItemPosition() == 0) {
                                bill_id = Utils.ReadSharePrefrence(PayOnlineActivity.this, INSTALLMENT_ID);
                            } else if (spPaymentTowards.getSelectedItemPosition() == 1) {
                                bill_id = Utils.ReadSharePrefrence(PayOnlineActivity.this, WATERBILL_ID);
                            } else if (spPaymentTowards.getSelectedItemPosition() == 2) {
                                bill_id = Utils.ReadSharePrefrence(PayOnlineActivity.this, MEMBERSHIP_ID);
                            }

                            Utils.WriteSharePrefrence(PayOnlineActivity.this, CHECK_CLICK, "false");
                            Intent intent = new Intent(PayOnlineActivity.this, PayNowActivity.class);
                            intent.putExtra("civil_id", civilid);
                            intent.putExtra("pay Amount", etAmt.getText().toString());
                            intent.putExtra("bill id", bill_id);
                            intent.putExtra("name", name);
                            intent.putExtra("Email_id", Utils.ReadSharePrefrence(PayOnlineActivity.this, Constant.EMAIL));
                            intent.putExtra("phoneNumber", Utils.ReadSharePrefrence(PayOnlineActivity.this, Constant.PHONENUMBER));
                            intent.putExtra("building id", strBuildingId);
                            intent.putExtra("payment type", strPaymentType);
                            intent.putExtra("project_name", Utils.ReadSharePrefrence(PayOnlineActivity.this, PROJECT_NAME));
                            intent.putExtra("floor_name", Utils.ReadSharePrefrence(PayOnlineActivity.this, FLOOR_NAME));
                            intent.putExtra("flat_name", Utils.ReadSharePrefrence(PayOnlineActivity.this, FLAT_NAME));
                            startActivity(intent);
                            overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                            break;

                        } else {
                            // Toast.makeText(this, "Please select your flat", Toast.LENGTH_SHORT).show();

                            Snackbar.make(payonline_llMain, "Please select your flat", Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        etAmt.setError("Please Enter Valid Amount");
                    }
                } else {
                    etAmt.setError("Please Enter Amount");
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // PayOnlineAdapter.lastCheckedPosition = -1;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(PayOnlineActivity.this,MyAccountActivity.class));
        PayOnlineAdapter.lastCheckedPosition = -1;
        PayOnlineAdapter.lastCheckedPosition1 = -1;
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    @SuppressLint("StaticFieldLeak")
    public class GetOtherName extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
           /* pd = new ProgressDialog(PayOnlineActivity.this);
            pd.setMessage("Loading");
            pd.show();*/
        }

        //   https://www.bavariagroup.net/index.php?view=raw&page=payment_type_list&iview=json
        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();

            data.put("view", "raw");
            data.put("page", "payment_type_list");
            data.put("iview", "json");
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {
                    othersNameArraylist.clear();

                    JSONArray jsonArray = object.getJSONArray("data");
                    JSONObject explrObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        explrObject = jsonArray.getJSONObject(i);
                        GetOthersNameClass objOthers = new GetOthersNameClass();
                        objOthers.setO_Name(explrObject.getString("name"));
                        objOthers.setO_Id(explrObject.getString("id"));
                        othersNameArraylist.add(objOthers.getO_Name());
                        othersnameAdapter = new ArrayAdapter<>(PayOnlineActivity.this, R.layout.spinner_item_layout, R.id.txt, othersNameArraylist);
                        spPaymentType.setAdapter(othersnameAdapter);

                    }

                } else {
                    Toast.makeText(PayOnlineActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}