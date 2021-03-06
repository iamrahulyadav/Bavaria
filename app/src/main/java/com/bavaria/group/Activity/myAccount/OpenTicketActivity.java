package com.bavaria.group.Activity.myAccount;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Adapter.OpenticketAdapter;
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

import static com.bavaria.group.Constant.Constant.BUILDING;

public class OpenTicketActivity extends BaseAppCompactActivity implements View.OnClickListener {

    TextView tvBack, tvSubmit;
    ImageView ivLogout;
    RecyclerView recyclerView;
    Spinner spPriority, spServiceType;
    EditText etMessage, spSubject;

    OpenticketAdapter openticketAdapter;
    String[] priority = {"Slow", "Medium", "High"};
    String[] service = {"Electrical", "A/C", "Plumbing", "General service"};
    ArrayList<verifyUserData> verifyUserDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_ticket);

        tvBack = findViewById(R.id.openticket_btnBack);
        ivLogout = findViewById(R.id.openticket_ivLogout);
        recyclerView = findViewById(R.id.openticket_recyclerview);
        spPriority = findViewById(R.id.openticket_spPriority);
        spServiceType = findViewById(R.id.openticket_spServiceType);
        spSubject = findViewById(R.id.openticket_spSubject);
        etMessage = findViewById(R.id.openticket_etMessage);
        tvSubmit = findViewById(R.id.openticket_tvSubmit);

        tvBack.setOnClickListener(this);
        ivLogout.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(OpenTicketActivity.this, R.layout.spinner_item_layout, R.id.txt, priority);
        spPriority.setAdapter(priorityAdapter);

        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(OpenTicketActivity.this, R.layout.spinner_item_layout, R.id.txt, service);
        spServiceType.setAdapter(serviceAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(OpenTicketActivity.this, LinearLayoutManager.HORIZONTAL, false));

        new callVerifyUser().execute();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.openticket_btnBack:
                finish();
                break;

            case R.id.openticket_ivLogout:
                Utils.getPopupWindow(OpenTicketActivity.this);

                /*Utils.ClearSharePrefrence(OpenTicketActivity.this);
                Toast.makeText(OpenTicketActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(OpenTicketActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();*/
                break;

            case R.id.openticket_tvSubmit:
                new callOpenTicket().execute();
                break;
        }
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
            verifyUserDatas = new ArrayList<>();
            pd = new ProgressDialog(OpenTicketActivity.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();
            data.put("civil_id", Utils.ReadSharePrefrence(OpenTicketActivity.this, Constant.CIVIT_ID));
            data.put("type", "ajax");
            data.put("action", "verifyuser");
            data.put("view", "json");
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject object;
                for (int i = 0; i < jsonArray.length(); i++) {
                    object = jsonArray.getJSONObject(i);
                    verifyUserData myTicketPojo = new verifyUserData();
                    myTicketPojo.setFees_id(object.getString("fees_id"));
                    myTicketPojo.setFees(object.getString("fees"));
                    myTicketPojo.setWaterbill_id(object.getString("waterbill_id"));
                    myTicketPojo.setInstallment_id(object.getString("installment_id"));
                    myTicketPojo.setBuilding_id(object.getString("building_id"));
                    myTicketPojo.setBuilding_name(object.getString("building_name"));
                    myTicketPojo.setFloor_name(object.getString("floor_name"));
                    myTicketPojo.setFlat_name(object.getString("flat_name"));
                    myTicketPojo.setTotal_amount(object.getString("total_amount"));
                    myTicketPojo.setWater_bil(object.getString("water_bil"));
                    myTicketPojo.setProject_name(object.getString("project_name"));
                    verifyUserDatas.add(myTicketPojo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (verifyUserDatas != null) {
                openticketAdapter = new OpenticketAdapter(OpenTicketActivity.this, verifyUserDatas);
                recyclerView.setAdapter(openticketAdapter);
            } else {
                Toast.makeText(OpenTicketActivity.this, R.string.datanotfound, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class callOpenTicket extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(OpenTicketActivity.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();

            data.put("view", "raw");
            data.put("page", "saveticket");
            data.put("iview", "json");
            data.put("id", Utils.ReadSharePrefrence(OpenTicketActivity.this, Constant.CIVIT_ID));
            data.put("subject", spSubject.getText().toString());
            data.put("dep", spServiceType.getSelectedItem().toString());
            data.put("priority", spPriority.getSelectedItem().toString());
            data.put("message", etMessage.getText().toString());
            data.put("meta_id", Utils.ReadSharePrefrence(OpenTicketActivity.this, BUILDING));
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
//            Log.e("RESPONSE",""+s);
            try {
                JSONObject object = new JSONObject(s);
                if (object.has("successful")) {
                    if (object.getString("successful").equalsIgnoreCase("true")) {
                        Toast.makeText(OpenTicketActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();

                        finish();
                    }
                } else if (object.has("error")) {
                    if (object.getString("error").equalsIgnoreCase("true")) {
                        Toast.makeText(OpenTicketActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OpenTicketActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
