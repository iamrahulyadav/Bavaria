package com.bavaria.group.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.ApiClient;
import com.bavaria.group.retrofit.ApiInterface;
import com.bavaria.group.retrofit.Model.verifyUserData;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetailActivity extends BaseAppCompactActivity implements View.OnClickListener {

    TextView tvNext, tvBack, tvpaymentamt;
    Spinner tvProject, tvPaymentDue;
    EditText totalPayment;
    ArrayList<verifyUserData> verifyUserDataa;
    ArrayList<String> stringArr;
    ArrayList<String> dueArr;
    ListView lv, lv1;
    String[] newItem;
    Intent intent;
    String civilid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        intent = getIntent();
        civilid = intent.getStringExtra("civil_id");

        stringArr = new ArrayList<>();

        dueArr = new ArrayList<>();
        dueArr.add("Yearly Membership");
        dueArr.add("WaterBill");
        dueArr.add("Installment");

        tvNext = findViewById(R.id.btnNext_paymentOnline);
        tvBack = findViewById(R.id.btnBack_paymentDetails);
        tvProject = findViewById(R.id.paymentDetail_tvProject);
        tvPaymentDue = findViewById(R.id.paymentDetail_tvPAymentDue);
        totalPayment = findViewById(R.id.total_payment);
        tvpaymentamt = findViewById(R.id.payment_due);

        lv = findViewById(R.id.loop_view);
        lv1 = findViewById(R.id.loop_view1);

        tvNext.setOnClickListener(this);
//        tvProject.setOnClickListener(this);
//        tvPaymentDue.setOnClickListener(this);
        tvBack.setOnClickListener(this);

        if (Utils.isOnline(getApplicationContext())) {
            callVerifyUser();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PaymentDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, dueArr);
        tvPaymentDue.setAdapter(arrayAdapter);


//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                tvProject.setText(parent.getItemAtPosition(position).toString());
//                lv.setVisibility(View.GONE);
//            }
//        });

        tvPaymentDue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newItem = tvProject.getSelectedItem().toString().replaceAll(" ", "").split("-");

                for (int i = 0; i < verifyUserDataa.size(); i++) {

                    if (verifyUserDataa.get(i).getProject_name().equalsIgnoreCase(newItem[0])) {

                        tvpaymentamt.setText(verifyUserDataa.get(i).getFees());

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        tvPaymentDue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                tvPaymentDue.setText(parent.getItemAtPosition(position).toString());
//                lv1.setVisibility(View.GONE);
//
//                newItem = tvProject.getText().toString().replaceAll(" ", "").split("-");
//
//                for (int i = 0; i < verifyUserDataa.size(); i++) {
//
//                    if (verifyUserDataa.get(i).getProject_name().equalsIgnoreCase(newItem[0])) {
//
//                        tvpaymentamt.setText(verifyUserDataa.get(i).getFees());
//                        totalPayment.setText(verifyUserDataa.get(i).getTotal_amount());
//
//                    }
//                }
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext_paymentOnline:
                if (isValid()) {

                    int value = Integer.parseInt(totalPayment.getText().toString());
                    int value1 = Integer.parseInt(tvpaymentamt.getText().toString());
                    if (value > value1) {
                        Toast.makeText(this, "Amount must be less than payment due", Toast.LENGTH_SHORT).show();
                    } else {
                        newItem = tvProject.getSelectedItem().toString().replaceAll(" ", "").split("-");

                        Intent intent = new Intent(PaymentDetailActivity.this, ConfirmPaymentActivity.class);
                        intent.putExtra("project name", newItem[0]);
                        intent.putExtra("Building", newItem[1]);
                        intent.putExtra("floor", newItem[2]);
                        intent.putExtra("flat", newItem[3]);
                        intent.putExtra("payment type", tvPaymentDue.getSelectedItem().toString());
                        intent.putExtra("payment due", tvpaymentamt.getText().toString());
                        intent.putExtra("total payment", totalPayment.getText().toString());
                        startActivity(intent);
                        overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                    }
//                    ArrayList<String> newData = new ArrayList<>();
                }
                break;

            case R.id.btnBack_paymentDetails:
                finish();
                break;

//            case R.id.paymentDetail_tvProject:
//
//                lv.setVisibility(View.VISIBLE);
//                lv1.setVisibility(View.GONE);

//                TimePickerPopWin timePickerPopWin=new TimePickerPopWin.Builder(PaymentDetailActivity.this, new TimePickerPopWin.OnTimePickListener() {
//                    @Override
//                    public void onTimePickCompleted(String time) {
//                        tvProject.setText(time);
//                    }
//                }).textConfirm("OK")
//                        .passArr(stringArr)
//                        .btnTextSize(16)
//                        .viewTextSize(20)
//                        .colorConfirm(Color.parseColor("#009900"))
//                        .build();
//                timePickerPopWin.showPopWin(PaymentDetailActivity.this);

//                break;
//
//            case R.id.paymentDetail_tvPAymentDue:

//                lv1.setVisibility(View.VISIBLE);
//                lv.setVisibility(View.GONE);

//                TimePickerPopWin timePickerPopWin1=new TimePickerPopWin.Builder(PaymentDetailActivity.this, new TimePickerPopWin.OnTimePickListener() {
//                    @Override
//                    public void onTimePickCompleted(String time) {
//                        tvPaymentDue.setText(time);
//                    }
//                }).textConfirm("OK")
//                        .passArr(dueArr)
//                        .btnTextSize(16)
//                        .viewTextSize(20)
//                        .colorConfirm(Color.parseColor("#009900"))
//                        .build();
//                timePickerPopWin1.showPopWin(PaymentDetailActivity.this);
//
//                tvpaymentamt.setText(amountDue);
//                break;
        }
    }

    public void callVerifyUser() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<>();
        data.put("civil_id", civilid);
        data.put("type", "ajax");
        data.put("action", "verifyuser");
        data.put("view", "json");

        Call<ArrayList<verifyUserData>> loginCall = apiInterface.verifyUser(data);
        loginCall.enqueue(new Callback<ArrayList<verifyUserData>>() {

            int i;

            @Override
            public void onResponse(Call<ArrayList<verifyUserData>> call, Response<ArrayList<verifyUserData>> response) {

                if (response.body() != null) {

                    verifyUserDataa = response.body();

                    if (verifyUserDataa != null) {
                        for (i = 0; i < verifyUserDataa.size(); i++) {

                            stringArr.add(verifyUserDataa.get(i).getProject_name() + " - " + verifyUserDataa.get(i).getBuilding_name() + " - " + verifyUserDataa.get(i).getFlat_name() + " - " + verifyUserDataa.get(i).getFloor_name());

                            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(PaymentDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, stringArr);
                            tvProject.setAdapter(arrayAdapter1);

//                            String fees, totalamt;


//                            fees = verifyUserDataa.get(i).getFees();
//                            totalamt = verifyUserDataa.get(i).getTotal_amount();

                            //                        projectNm = verifyUserDataa.get(i).getProject_name();
                            //                        buildingNm = verifyUserDataa.get(i).getBuilding_name();
                            //                        floorNm = verifyUserDataa.get(i).getFloor_name();
                            //                        flatNm = verifyUserDataa.get(i).getFlat_name();
                            //                        amountDue = verifyUserDataa.get(i).getFees();
                            //                        totalAmt = verifyUserDataa.get(i).getTotal_amount();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<verifyUserData>> call, Throwable t) {
                Toast.makeText(PaymentDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isValid() {

        boolean isvalid = true;

//        if (TextUtils.isEmpty(tvProject.getText().toString())) {
//            tvProject.setError("Please select project name");
//            isvalid = false;
//        }
//
//        if (TextUtils.isEmpty(tvPaymentDue.getText().toString())) {
//            tvPaymentDue.setError("Please select payment type");
//            isvalid = false;
//        }

        if (TextUtils.isEmpty(totalPayment.getText().toString()) && totalPayment.getText().toString().equalsIgnoreCase("0")) {
            totalPayment.setError("Please Enter total amount");
            isvalid = false;
        }
        return isvalid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }
//
//    public class callVerifyUser extends AsyncTask<String, String, String> {
//        ProgressDialog pd;
//
//        @Override
//        protected void onPreExecute() {
//
//            super.onPreExecute();
//            pd = new ProgressDialog(PaymentDetailActivity.this);
//            pd.setMessage("Loading");
//            pd.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            HashMap<String, String> data = new HashMap<>();
//
//            data.put("civil_id", civilid);
//            data.put("type", "ajax");
//            data.put("action", "verifyuser");
//            data.put("view", "json");
//            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/?", data);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            pd.dismiss();
//
//            int i;
////            Log.e("RESPONSE",""+s);
//            try {
//                JSONObject object = new JSONObject(s);
//                if (object.getString("status").equalsIgnoreCase("true")) {
//                    Toast.makeText(PaymentDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
//
//                    ArrayList<verifyUserData> verifyUserDatas = new ArrayList<>();
//                    verifyUserData verifyUserDataa = new verifyUserData();
//                    verifyUserDataa.setFees_id(object.getString("fees_id"));
//                    verifyUserDataa.setFees(object.getString("fees"));
//                    verifyUserDataa.setWaterbill_id(object.getString("waterbill_id"));
//                    verifyUserDataa.setInstallment_id(object.getString(""));
//                    verifyUserDataa.setInstallment_id(object.getString("installment_id"));
//                    verifyUserDataa.setBuilding_id(object.getString("building_id"));
//                    verifyUserDataa.setBuilding_name(object.getString("building_name"));
//                    verifyUserDataa.setFloor_name(object.getString("floor_name"));
//                    verifyUserDataa.setFlat_name(object.getString("flat_name"));
//                    verifyUserDataa.setTotal_amount(object.getString("total_amount"));
//                    verifyUserDataa.setWater_bil(object.getString("water_bil"));
//                    verifyUserDataa.setProject_name(object.getString("project_name"));
//
//                    verifyUserDatas.add(verifyUserDataa);
//
//                    for (i = 0; i < verifyUserDatas.size(); i++) {
//
//                        stringArr.add(verifyUserDatas.get(i).getProject_name() + " - " + verifyUserDatas.get(i).getBuilding_name() + " - " + verifyUserDatas.get(i).getFlat_name() + " - " + verifyUserDatas.get(i).getFloor_name());
//
//                        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(PaymentDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, stringArr);
//                        tvProject.setAdapter(arrayAdapter1);
//
////                        String fees, totalamt;
//
//
////                        fees = verifyUserDatas.get(i).getFees();
////                        totalamt = verifyUserDatas.get(i).getTotal_amount();
//
////                        projectNm = verifyUserDataa.get(i).getProject_name();
////                        buildingNm = verifyUserDataa.get(i).getBuilding_name();
////                        floorNm = verifyUserDataa.get(i).getFloor_name();
////                        flatNm = verifyUserDataa.get(i).getFlat_name();
////                        amountDue = verifyUserDataa.get(i).getFees();
////                        totalAmt = verifyUserDataa.get(i).getTotal_amount();
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
////                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}
