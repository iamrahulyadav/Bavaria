package com.bavaria.group.Activity.myAccount;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Adapter.MembershipAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.SQLiteDatabase.DbHelper;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.ApiClient;
import com.bavaria.group.retrofit.ApiInterface;
import com.bavaria.group.retrofit.Model.MembershipDataPojo;
import com.bavaria.group.retrofit.Model.MembershipPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YearlyMembershipActivity extends BaseAppCompactActivity {

    RecyclerView recyclerView;
    MembershipAdapter membershipAdapter;
    TextView ivBack;
    ImageView ivLogout;
    ArrayList<MembershipDataPojo> membershipDataPojos;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_membership);

        dbHelper = new DbHelper(YearlyMembershipActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.yearlyMembership_recyclerview);
        ivBack = (TextView) findViewById(R.id.membership_btnBack);
        ivLogout = (ImageView) findViewById(R.id.membership_logout);

        membershipDataPojos = new ArrayList<>();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.getPopupWindow(YearlyMembershipActivity.this);

               /* Utils.ClearSharePrefrence(YearlyMembershipActivity.this);
                Toast.makeText(YearlyMembershipActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(YearlyMembershipActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();*/
            }
        });

        new callMembership().execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    public void callMembership() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("view", "raw");
        data.put("page", "ym");
        data.put("iview", "json");
        data.put("id", Utils.ReadSharePrefrence(YearlyMembershipActivity.this, Constant.CIVIT_ID));

        Call<MembershipPojo> loginCall = apiInterface.yearlyMembership(data);
        loginCall.enqueue(new Callback<MembershipPojo>() {
            @Override
            public void onResponse(Call<MembershipPojo> call, Response<MembershipPojo> response) {

                if (response.body() != null) {

                    MembershipPojo membershipPojo = response.body();
                    membershipDataPojos = membershipPojo.getData();

                    if (membershipPojo.getStatus().equalsIgnoreCase("true")) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(YearlyMembershipActivity.this, LinearLayoutManager.VERTICAL, false));
                        membershipAdapter = new MembershipAdapter(YearlyMembershipActivity.this, membershipDataPojos);

                        recyclerView.setAdapter(membershipAdapter);
                    } else {
                        Toast.makeText(YearlyMembershipActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

//                    dbHelper.deleteMembershipData();
//                    dbHelper.addMembershipData(membershipData);
                }
            }

            @Override
            public void onFailure(Call<MembershipPojo> call, Throwable t) {
                Toast.makeText(YearlyMembershipActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class callMembership extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(YearlyMembershipActivity.this);
            pd.setMessage("Loading");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("view", "raw");
            data.put("page", "ym");
            data.put("iview", "json");
            data.put("id", Utils.ReadSharePrefrence(YearlyMembershipActivity.this, Constant.CIVIT_ID));

            //   https://www.bavariagroup.net/index.php?view=raw&iaction=user_profile&civil_id=90075698
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

                    JSONArray jsonArray = object.getJSONArray("data");
                    JSONObject explrObject = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        explrObject = jsonArray.getJSONObject(i);

                        MembershipDataPojo membershipDataPojo = new MembershipDataPojo();
                        membershipDataPojo.setProject_name(explrObject.getString("project_name"));
                        membershipDataPojo.setBuilding_name(explrObject.getString("building_name"));
                        membershipDataPojo.setFloor_name(explrObject.getString("floor_name"));
                        membershipDataPojo.setExpary_date(explrObject.getString("Expary_date"));
                        membershipDataPojo.setFlat_name(explrObject.getString("flat_name"));
                        membershipDataPojo.setBuilding_id(explrObject.getString("building_id"));
                        membershipDataPojo.setBill_id(explrObject.getString("bill_id"));
                        membershipDataPojo.setAmount(explrObject.getString("amount"));

                        membershipDataPojos.add(membershipDataPojo);

                        recyclerView.setLayoutManager(new LinearLayoutManager(YearlyMembershipActivity.this, LinearLayoutManager.VERTICAL, false));
                        membershipAdapter = new MembershipAdapter(YearlyMembershipActivity.this, membershipDataPojos);

                        recyclerView.setAdapter(membershipAdapter);

                    }


                } else {
                    Toast.makeText(YearlyMembershipActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
