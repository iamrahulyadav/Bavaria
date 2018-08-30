package com.bavaria.group.Activity.myAccount;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bavaria.group.Adapter.MyTicketsAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.Model.MyTicketPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Archirayan on 17-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */

public class MyTicketActivity extends BaseAppCompactActivity {

    RecyclerView recyclerView;
    MyTicketsAdapter mytcktAdapter;
    TextView ivBack;
    EditText ediTxtTicket;
    ImageView ivLogout;
    ArrayList<MyTicketPojo> viewticketdata;
    ArrayList<MyTicketPojo> searchedArraylist;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_myticket);
        initViews();

        new callViewTicket().execute();
    }

    private void initViews() {

        recyclerView = findViewById(R.id.myTicket_recyclerview);
        ivBack = findViewById(R.id.myTcket_btnBack);
        ediTxtTicket = findViewById(R.id.ediTxtTicket);
        ivLogout = findViewById(R.id.myTicket_logout);
        searchedArraylist = new ArrayList<>();

        ediTxtTicket.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (viewticketdata != null) {
                    String searchFor = ediTxtTicket.getText().toString();
                    searchedArraylist = new ArrayList<>();

                    for (int j = 1; j < viewticketdata.size(); j++) {
                        if (viewticketdata.get(j).getSubject().contains(searchFor.toLowerCase()) || viewticketdata.get(j).getId().toLowerCase().contains(searchFor.toLowerCase())) {
                            searchedArraylist.add(viewticketdata.get(j));
                        }
                    }

                    mytcktAdapter = new MyTicketsAdapter(MyTicketActivity.this, searchedArraylist);
                    recyclerView.setAdapter(mytcktAdapter);
                    mytcktAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ivLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.getPopupWindow(MyTicketActivity.this);

               /* Utils.ClearSharePrefrence(MyTicketActivity.this);
                Toast.makeText(MyTicketActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(MyTicketActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();*/
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

//    public void callViewTicket() {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        HashMap<String, String> data = new HashMap<String, String>();
//        data.put("view", "raw");
//        data.put("page", "ticketlist");
//        data.put("iview", "json");
//        data.put("id", Utils.ReadSharePrefrence(MyTicketActivity.this, Constant.CIVIT_ID));
//
//        Call<ArrayList<MyTicketPojo>> loginCall = apiInterface.viewTicket(data);
//        loginCall.enqueue(new Callback<ArrayList<MyTicketPojo>>() {
//            @Override
//            public void onResponse(Call<ArrayList<MyTicketPojo>> call, Response<ArrayList<MyTicketPojo>> response) {
//
//                if (response.body() != null) {
//                    viewticketdata = response.body();
//                    viewTicket = new ArrayList<>();
//                    for (int i = 1; i < viewticketdata.size(); i++) {
//                        viewTicket.add(viewticketdata.get(i));
//                    }
//                    recyclerView.setLayoutManager(new LinearLayoutManager(MyTicketActivity.this, LinearLayoutManager.VERTICAL, false));
//                    mytcktAdapter = new MyTicketsAdapter(MyTicketActivity.this, viewTicket);
//                    recyclerView.setAdapter(mytcktAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<MyTicketPojo>> call, Throwable t) {
//                Toast.makeText(MyTicketActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @SuppressLint("StaticFieldLeak")
    private class callViewTicket extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            viewticketdata = new ArrayList<>();
            pd = new ProgressDialog(MyTicketActivity.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();

            data.put("view", "raw");
            data.put("page", "ticketlist");
            data.put("iview", "json");
            data.put("id", Utils.ReadSharePrefrence(MyTicketActivity.this, Constant.CIVIT_ID));

            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
//            Log.e("RESPONSE",""+s);
            try {
                JSONArray jsonArray = new JSONArray(s);
//                if (object.getString("status").toString().equalsIgnoreCase("true")) {
//                    Toast.makeText(MyTicketActivity.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();

                JSONObject object;
                for (int i = 1; i < jsonArray.length(); i++) {
                    object = jsonArray.getJSONObject(i);

                    MyTicketPojo myTicketPojo = new MyTicketPojo();
                    myTicketPojo.setId(object.getString("id"));
                    myTicketPojo.setDate(object.getString("date"));
                    myTicketPojo.setDep(object.getString("dep"));
                    myTicketPojo.setSubject(object.getString("subject"));
                    myTicketPojo.setStatus(object.getString("status"));
                    viewticketdata.add(myTicketPojo);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyTicketActivity.this, LinearLayoutManager.VERTICAL, false));
                    mytcktAdapter = new MyTicketsAdapter(MyTicketActivity.this, viewticketdata);
                    recyclerView.setAdapter(mytcktAdapter);
                }

//                } else {
//                    Toast.makeText(MyTicketActivity.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();
//                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
