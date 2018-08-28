package com.bavaria.group.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Activity.myAccount.PayNowActivity;
import com.bavaria.group.Adapter.GuestPayAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.ApiClient;
import com.bavaria.group.retrofit.ApiInterface;
import com.bavaria.group.retrofit.Model.AvailabilityCheckData;
import com.bavaria.group.retrofit.Model.GetOthersNameClass;
import com.bavaria.group.retrofit.Model.guestPayData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bavaria.group.Constant.Constant.CHECK_CLICK;
import static com.bavaria.group.Constant.Constant.CIVIT_ID;

public class GuestPayActivity extends AppCompatActivity {

    public static ArrayAdapter<String> projectAdapter, buildingAdapter, flatAdapter, paymentAdapter;
    public static ArrayAdapter<String> othersnameAdapter;
    ArrayList<String> projects;
    ArrayList<String> buildings;
    String[] paymentTowards = {"Select Paymemt towards..", "Yearly Maintenance", "Water Bill", "Others"};
    String strPaymentType, building_id, getFloor, getProject, getBuilding, getFlat;
    Spinner spPaymentType, spProjects, spBuildings, spFloor, spFlat, spPaymentTowards;
    Button btnBack;
    TextView btnPay;
    int check = 0;
    LinearLayout payonline_llpaymentType, payonline_llpaymentTotal;
    ArrayList<String> othersNameArraylist;
    private GuestPayAdapter guestPayAdapter;
    private EditText etPhone, etName, etEmail, etAmount, etCivilid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_pay);

        spProjects = findViewById(R.id.guestway_spProjects);
        spBuildings = findViewById(R.id.guestway_spBuildings);
        spFloor = findViewById(R.id.guestway_spFloor);
        spFlat = findViewById(R.id.guestway_spFlat);
        spPaymentTowards = findViewById(R.id.guestway_spPaymenttowards);
        btnBack = findViewById(R.id.guestpay_btnBack);
        btnPay = findViewById(R.id.guestpay_btnSubmit);
        etCivilid = findViewById(R.id.guestpay_etCivilID);
        etAmount = findViewById(R.id.guestpay_etAmount);
        payonline_llpaymentType = findViewById(R.id.guestll_paymentType);
        payonline_llpaymentTotal = findViewById(R.id.guestll_paymentTotal);
        spPaymentType = findViewById(R.id.guestpay_spPaymentType);
        etEmail = findViewById(R.id.guestpay_btnEmail);
        etName = findViewById(R.id.guestpay_btnName);
        etPhone = findViewById(R.id.guestpay_btnPhoneno);

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new getProjectCategory().execute();
                }
            }
        });

        etCivilid.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            new getProjectCategory().execute();
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });

        othersNameArraylist = new ArrayList<>();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callGuestDataSubmit();

                Utils.WriteSharePrefrence(GuestPayActivity.this, CHECK_CLICK, "false");
                Utils.WriteSharePrefrence(GuestPayActivity.this, CIVIT_ID, etCivilid.getText().toString());
                Intent intent = new Intent(GuestPayActivity.this, PayNowActivity.class);
                intent.putExtra("civil_id", etCivilid.getText().toString());
                intent.putExtra("pay Amount", etAmount.getText().toString());
                intent.putExtra("bill id", "");
                intent.putExtra("building id", building_id);
                intent.putExtra("payment type", strPaymentType);
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("phoneNumber", etPhone.getText().toString());
                intent.putExtra("Email_id", etEmail.getText().toString());
                intent.putExtra("project_name", getProject);
                intent.putExtra("floor_name", getFloor);
                intent.putExtra("flat_name", getFlat);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
            }
        });

        spPaymentTowards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                if (spPaymentTowards.getSelectedItem().toString().equalsIgnoreCase("Yearly Maintenance")) {
//
//                    payonline_llpaymentTotal.setVisibility(View.VISIBLE);
//                    payonline_llpaymentType.setVisibility(View.GONE);
//
//                    tvTotal.setText(verifyUserDataa.get(pos1).getTotal_amount());
//                    othersNameArraylist.clear();
//                    strPaymentType = spPaymentTowards.getSelectedItem().toString();
//                    othersnameAdapter = new ArrayAdapter<String>(GuestPayActivity.this, R.layout.spinner_item_layout, R.id.txt, othersNameArraylist);
//                    spPaymentType.setAdapter(othersnameAdapter);
//
//                } else if (spPaymentTowards.getSelectedItem().toString().equalsIgnoreCase("Water Bill")) {
//
//                    payonline_llpaymentTotal.setVisibility(View.VISIBLE);
//                    payonline_llpaymentType.setVisibility(View.GONE);
//
//                    tvTotal.setText(verifyUserDataa.get(pos1).getWater_bil());
//                    othersNameArraylist.clear();
//                    strPaymentType = spPaymentTowards.getSelectedItem().toString();
//
//                    othersnameAdapter = new ArrayAdapter<String>(GuestPayActivity.this, R.layout.spinner_item_layout, R.id.txt, othersNameArraylist);
//                    spPaymentType.setAdapter(othersnameAdapter);

                if (spPaymentTowards.getSelectedItem().toString().equalsIgnoreCase("Others")) {
                    payonline_llpaymentTotal.setVisibility(View.GONE);
                    payonline_llpaymentType.setVisibility(View.VISIBLE);
                    //  tvTotal.setText(verifyUserDataa.get(pos1).getFees());
                    new GetOtherName().execute();

                } else if (spPaymentTowards.getSelectedItem().toString().equalsIgnoreCase("Yearly Maintenance")) {
                    payonline_llpaymentType.setVisibility(View.GONE);
                    strPaymentType = "membership";//2

                } else if (spPaymentTowards.getSelectedItem().toString().equalsIgnoreCase("Water Bill")) {
                    payonline_llpaymentType.setVisibility(View.GONE);
                    strPaymentType = "water_bill";//1
                } else {
                    payonline_llpaymentType.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new getProjectCategory().execute();

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

        paymentAdapter = new ArrayAdapter<>(GuestPayActivity.this, R.layout.spinner_row, R.id.tvItemName, paymentTowards);
        spPaymentTowards.setAdapter(paymentAdapter);


        spProjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                getProject = parent.getSelectedItem().toString();
                callAvailabilityCheck(getProject);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spBuildings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                getBuilding = parent.getSelectedItem().toString();
                callFloor(getProject, getBuilding);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                getFloor = parent.getSelectedItem().toString();
                callFlat(getProject, getBuilding, getFloor);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spFlat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                getFlat = guestPayAdapter.getItem(pos).get(pos).getFlat_name();
                building_id = guestPayAdapter.getItem(pos).get(pos).getBuilding_id();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void callGuestDataSubmit() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<>();
        data.put("view", "raw");
        data.put("page", "projectcheck");
        data.put("iview", "json");

        data.put("name", etName.getText().toString());
        data.put("phno", etPhone.getText().toString());
        data.put("civilid", etCivilid.getText().toString());
        data.put("email", etEmail.getText().toString());
        data.put("project", spProjects.getSelectedItem().toString());
        data.put("building", spBuildings.getSelectedItem().toString());
        data.put("floor", spFloor.getSelectedItem().toString());
        data.put("flat", spFlat.getSelectedItem().toString());
        data.put("payment", spPaymentTowards.getSelectedItem().toString());
        data.put("amount", etAmount.getText().toString());

        Call<JsonObject> loginCall = apiInterface.submitGuestData(data);
        loginCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.body() != null) {


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("data", "value");
                //  Toast.makeText(ProjectDetailsActivity.this, R.string.datanotfound, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callAvailabilityCheck(String strName) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<>();
        data.put("view", "raw");
        data.put("page", "projectcheck");
        data.put("iview", "json");
        data.put("project_name", strName);

        Call<ArrayList<AvailabilityCheckData>> loginCall = apiInterface.availabilityCheck(data);
        loginCall.enqueue(new Callback<ArrayList<AvailabilityCheckData>>() {
            @Override
            public void onResponse(Call<ArrayList<AvailabilityCheckData>> call, Response<ArrayList<AvailabilityCheckData>> response) {

                if (response.body() != null) {

                    ArrayList<AvailabilityCheckData> newarr = response.body();

                    buildings = new ArrayList<>();
                    buildings.add("Select Building..");
                    if (newarr != null) {
                        for (int i = 0; i < newarr.size(); i++) {

                            buildings.add(newarr.get(i).getBuilding_name());
                        }
                    }

                    buildingAdapter = new ArrayAdapter<>(GuestPayActivity.this, R.layout.spinner_row, R.id.tvItemName, buildings);
                    spBuildings.setAdapter(buildingAdapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<AvailabilityCheckData>> call, Throwable t) {
                Log.i("data", "value");
                //  Toast.makeText(ProjectDetailsActivity.this, R.string.datanotfound, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callFloor(String strProjectname, String strBuildingname) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<>();
        data.put("view", "raw");
        data.put("page", "flatlist");
        data.put("iview", "json");
        data.put("project_name", strProjectname);
        data.put("building_name", strBuildingname);

        Call<JsonObject> loginCall = apiInterface.getFloorFlat(data);
        loginCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.body() != null) {

                    JsonObject responseObj = response.body();

                    JsonArray newArr = null;
                    if (responseObj != null) {
                        if (responseObj.has("yes")) {
                            newArr = responseObj.get("yes").getAsJsonArray();
                        } else if (responseObj.has("no")) {
                            newArr = responseObj.get("no").getAsJsonArray();
                        }
                    }

                    ArrayList<String> guestPayData = new ArrayList<>();
                    guestPayData.add("Select Floor..");
                    if (newArr != null) {
                        for (int i = 0; i < newArr.size(); i++) {
                            //                        if (newArr.get(i).getAsJsonObject().get("Available").getAsString().equals("yes")) {

                            if (guestPayData.size() > 0) {
                                for (int j = 0; j < guestPayData.size(); j++) {
                                    if (guestPayData.get(j).equals(newArr.get(i).getAsJsonObject().get("floor_name").getAsString())) {
                                        check = 1;
                                        break;
                                    } else {
                                        check = 0;
                                    }
                                }
                            }

                            if (check == 0) {
                                guestPayData.add(newArr.get(i).getAsJsonObject().get("floor_name").getAsString());
                            }

                        }
                    }

                    if (guestPayData.size() > 1) {

                        flatAdapter = new ArrayAdapter<>(GuestPayActivity.this, R.layout.spinner_row, R.id.tvItemName, guestPayData);
                        spFloor.setAdapter(flatAdapter);
                    } else {

                        ArrayList<guestPayData> guestPayData1 = new ArrayList<>();
                        ArrayList<String> guestPayData2 = new ArrayList<>();

                        guestPayData2.add("Select Floor..");

                        guestPayData guestPayDataa = new guestPayData();
                        guestPayDataa.setFlat_name("Select Flat..");
                        guestPayDataa.setBuilding_id("0");
                        guestPayData1.add(guestPayDataa);

                        flatAdapter = new ArrayAdapter<>(GuestPayActivity.this, R.layout.spinner_row, R.id.tvItemName, guestPayData2);
                        spFloor.setAdapter(flatAdapter);

                        guestPayAdapter = new GuestPayAdapter(GuestPayActivity.this, guestPayData1);
                        spFlat.setAdapter(guestPayAdapter);

                        Toast.makeText(GuestPayActivity.this, "No Floor Available. Please Select Another Building.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ArrayList<String> guestPayData = new ArrayList<>();
                guestPayData.add("Select Floor..");
                paymentAdapter = new ArrayAdapter<>(GuestPayActivity.this, R.layout.spinner_row, R.id.tvItemName, guestPayData);
                spFloor.setAdapter(paymentAdapter);

                Log.i("data", "value");
                //  Toast.makeText(ProjectDetailsActivity.this, R.string.datanotfound, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callFlat(String strProjectname, String strBuildingname, final String strFlatname) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<>();
        data.put("view", "raw");
        data.put("page", "flatlist");
        data.put("iview", "json");
        data.put("project_name", strProjectname);
        data.put("building_name", strBuildingname);

        Call<JsonObject> loginCall = apiInterface.getFloorFlat(data);
        loginCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.body() != null) {

                    JsonObject responseObj = response.body();

                    JsonArray newArr = null;
                    if (responseObj != null) {
                        if (responseObj.has("yes")) {
                            newArr = responseObj.get("yes").getAsJsonArray();
                        } else if (responseObj.has("no")) {
                            newArr = responseObj.get("no").getAsJsonArray();
                        }
                    }

                    ArrayList<guestPayData> guestPayData1 = new ArrayList<>();

                    guestPayData guestPayDataa = new guestPayData();
                    guestPayDataa.setFlat_name("Select Flat..");
                    guestPayDataa.setBuilding_id("0");
                    guestPayData1.add(guestPayDataa);

                    if (newArr != null) {
                        if (newArr.size() > 1) {
                            for (int i = 0; i < newArr.size(); i++) {
                                //if (newArr.get(i).getAsJsonObject().get("Available").getAsString().equals("yes")) {

                                guestPayData guestPayData = new guestPayData();

                                if (newArr.get(i).getAsJsonObject().get("floor_name").getAsString().equals(strFlatname)) {
                                    guestPayData.setFlat_name(newArr.get(i).getAsJsonObject().get("flat_name").getAsString());
                                    guestPayData.setBuilding_id(newArr.get(i).getAsJsonObject().get("building_id").getAsString());
                                    guestPayData1.add(guestPayData);
                                }

                            }
                        } else {
                            Toast.makeText(GuestPayActivity.this, "No flat Available.", Toast.LENGTH_SHORT).show();
                        }
                    }


                    guestPayAdapter = new GuestPayAdapter(GuestPayActivity.this, guestPayData1);
                    spFlat.setAdapter(guestPayAdapter);


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                ArrayList<String> guestPayData = new ArrayList<>();
//                guestPayData.add("Select Flat..");
//                paymentAdapter = new ArrayAdapter<>(GuestPayActivity.this, R.layout.spinner_row, R.id.tvItemName, guestPayData);
//                spFloor.setAdapter(paymentAdapter);

                Log.i("data", "value");
                //  Toast.makeText(ProjectDetailsActivity.this, R.string.datanotfound, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    @SuppressLint("StaticFieldLeak")
    private class getProjectCategory extends AsyncTask<String, String, String> {
//        Dialog dialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
//            dialog = new Dialog(GuestPayActivity.this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.loader_layout);
//            dialog.setCancelable(false);
//            ImageView loader = dialog.findViewById(R.id.loader_layout_image);
//            ((Animatable) loader.getDrawable()).start();
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?view=raw&page=projects&iview=json");
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "?civil_id=" + etCivilid.getText().toString() + "&type=ajax&action=verifyuser&view=json");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            dialog.dismiss();
            Log.e("Response", "" + s);
            projects = new ArrayList<>();
            projects.add("Select Projects...");
            if (s.contains("{\"error\":1}")) {
                new getProjectCategorynonCivil().execute();
            } else {
                try {
//                arrayList = new ArrayList<>();

//                projects.add("Select Project..");
                    JSONArray jArray = new JSONArray(s);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObj = jArray.getJSONObject(i);
                        projects.add(jObj.getString("project_name"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

//            if(projects.size()>0) {

            projectAdapter = new ArrayAdapter<>(GuestPayActivity.this, R.layout.spinner_row, R.id.tvItemName, projects);
            spProjects.setAdapter(projectAdapter);
//            }
//            else {
//                new getProjectCategorynonCivil().execute();
//            }
        }
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
                        othersnameAdapter = new ArrayAdapter<>(GuestPayActivity.this, R.layout.spinner_row, R.id.tvItemName, othersNameArraylist);
                        spPaymentType.setAdapter(othersnameAdapter);

                    }

                } else {
                    Toast.makeText(GuestPayActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getProjectCategorynonCivil extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?view=raw&page=projects&iview=json");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Response", "" + s);
            try {
                JSONArray jArray = new JSONArray(s);
                projects = new ArrayList<>();
                projects.add("Select Projects...");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    projects.add(jObj.getString("category_name"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            projectAdapter = new ArrayAdapter<>(GuestPayActivity.this, R.layout.spinner_row, R.id.tvItemName, projects);
            spProjects.setAdapter(projectAdapter);

        }
    }

}
