package com.bavaria.group.Activity.myAccount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Adapter.ReplyAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.ApiClient;
import com.bavaria.group.retrofit.ApiInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bavaria.group.Constant.Constant.CIVIT_ID;

public class ReplyActivity extends BaseAppCompactActivity {

    public TextView reply_msg;
    public String str_MessageView;
    TextView ediTxtSubject, ediTxtDepartment, ediTxtSubmited, ediTxttTicketStatus,
            ediTxtPriority, editTxtDescription;
    RecyclerView rlMessage;
    TextView txtVwAttchmentFileName, txtVwFileChosenName, reply_btnBack, txtTicketNo;
    Button btnChooseFile, btnReply, btnCloseTicket;
    ImageView reply_logout;
    ReplyAdapter replyAdapter;
    Intent intent;
    String ticket_id,strEtDesc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_reply);

        intent = getIntent();
        ticket_id = intent.getStringExtra("ticket_id");

        initViews();

        new callViewTicketDetail(ticket_id).execute();
    }

    private void initViews() {

        reply_btnBack = (TextView) findViewById(R.id.reply_btnBack);
        txtTicketNo = (TextView) findViewById(R.id.txtVwTcktNo);

        ediTxtSubject = (TextView) findViewById(R.id.ediTxtSubject);
        ediTxtDepartment = (TextView) findViewById(R.id.ediTxtDepartment);
        ediTxtSubmited = (TextView) findViewById(R.id.ediTxtSubmited);
        ediTxttTicketStatus = (TextView) findViewById(R.id.ediTxttTicketStatus);
        ediTxtPriority = (TextView) findViewById(R.id.ediTxtPriority);
        rlMessage = (RecyclerView) findViewById(R.id.rlMessage);
        editTxtDescription = (EditText) findViewById(R.id.editTxtDescription);
        reply_msg = (TextView) findViewById(R.id.reply_msg);
        reply_logout = (ImageView) findViewById(R.id.reply_logout);
        btnReply = (Button) findViewById(R.id.btnReply);
        btnCloseTicket = (Button) findViewById(R.id.btnCloseTicket);

        reply_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reply_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getPopupWindow(ReplyActivity.this);

               /* Utils.ClearSharePrefrence(ReplyActivity.this);
                Toast.makeText(ReplyActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(ReplyActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();*/
            }
        });

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTxtDescription.getText().toString().trim().length() > 0) {
                    editTxtDescription.setVisibility(View.VISIBLE);
                    strEtDesc=editTxtDescription.getText().toString();
                    new callReply(ticket_id).execute();
                } else {
                    Toast.makeText(ReplyActivity.this, "Please Enter Message.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCloseTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new callCloseTicket(ticket_id).execute();
            }
        });
    }

    public void callViewTicketDetail(String TicketId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("view", "raw");
        data.put("page", "ticketdetail");
        data.put("iview", "json");
        data.put("id", TicketId);

        Call<JsonArray> loginCall = apiInterface.viewTicketDetail(data);
        loginCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.body() != null) {

                    if (!response.body().getAsJsonArray().get(0).getAsJsonObject().get("status").getAsString().equalsIgnoreCase("Closed")) {
                        btnCloseTicket.setVisibility(View.VISIBLE);
                        btnReply.setVisibility(View.VISIBLE);
                    }
                    txtTicketNo.setText("Ticket No #" + response.body().getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString());
                    ediTxtSubject.setText(response.body().getAsJsonArray().get(0).getAsJsonObject().get("subject").getAsString());
                    ediTxtDepartment.setText(response.body().getAsJsonArray().get(0).getAsJsonObject().get("dep").getAsString());
                    ediTxtSubmited.setText(response.body().getAsJsonArray().get(0).getAsJsonObject().get("date").getAsString());
                    ediTxttTicketStatus.setText(response.body().getAsJsonArray().get(0).getAsJsonObject().get("status").getAsString());
                    ediTxtPriority.setText(response.body().getAsJsonArray().get(0).getAsJsonObject().get("priority").getAsString());

                    if (!response.body().getAsJsonArray().get(0).getAsJsonObject().get("reply").toString().equalsIgnoreCase("null")) {

                        JsonArray repliedBy = response.body().getAsJsonArray().get(0).getAsJsonObject().get("reply").getAsJsonArray();
                        rlMessage.setLayoutManager(new LinearLayoutManager(ReplyActivity.this, LinearLayoutManager.VERTICAL, false));
                    } else {
                        Toast.makeText(ReplyActivity.this, "No Reply Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(ReplyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callReply(String TicketId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("view", "raw");
        data.put("iaction", "reply");
        data.put("message", editTxtDescription.getText().toString());
        data.put("user_id", Utils.ReadSharePrefrence(ReplyActivity.this, CIVIT_ID));
        data.put("id", TicketId);

        Call<JsonObject> loginCall = apiInterface.reply(data);
        loginCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.body() != null) {

                    if (response.body().get("status").toString().replaceAll("\"", "").equalsIgnoreCase("true")) {
                        Toast.makeText(ReplyActivity.this, "Your reply has been sent", Toast.LENGTH_LONG).show();
                        new callViewTicketDetail(ticket_id).execute();
                        editTxtDescription.setText("");

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ReplyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callCloseTicket(String TicketId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("view", "raw");
        data.put("iaction", "close");
        data.put("id", TicketId);

        Call<JsonObject> loginCall = apiInterface.closeTicket(data);
        loginCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.body() != null) {

                    if (response.body().get("status").toString().equalsIgnoreCase("true")) {
                        Toast.makeText(ReplyActivity.this, "Ticket Close", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ReplyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    public class callViewTicketDetail extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String ticket_id;

        public callViewTicketDetail(String ticket_id) {
            this.ticket_id = ticket_id;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            /*pd = new ProgressDialog(ReplyActivity.this);
            pd.setMessage("Loading");
            pd.show();*/

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("view", "raw");
            data.put("page", "ticketdetail");
            data.put("iview", "json");
            data.put("id", ticket_id);
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //pd.dismiss();
            Log.e("RESPONSE",""+s);
            try {
                JSONArray jsonArray = new JSONArray(s.toString());

                JSONObject object = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    object = jsonArray.getJSONObject(i);
                }

                if (!object.getString("status").equalsIgnoreCase("Closed")) {
                    btnCloseTicket.setVisibility(View.VISIBLE);
                    btnReply.setVisibility(View.VISIBLE);
                    editTxtDescription.setVisibility(View.VISIBLE);
                }

                txtTicketNo.setText("Ticket No #" + object.getString("id"));
                ediTxtSubject.setText(object.getString("subject"));
                ediTxtDepartment.setText(object.getString("dep"));
                ediTxtSubmited.setText(object.getString("date"));
                ediTxttTicketStatus.setText(object.getString("status"));
                ediTxtPriority.setText(object.getString("priority"));
                reply_msg.setText(object.getString("message"));

                if (!object.getString("reply").equalsIgnoreCase("null")) {

                    JSONArray repliedBy = object.getJSONArray("reply");
                    rlMessage.setLayoutManager(new LinearLayoutManager(ReplyActivity.this, LinearLayoutManager.VERTICAL, false));
                    replyAdapter = new ReplyAdapter(ReplyActivity.this, repliedBy);
                    rlMessage.setAdapter(replyAdapter);

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class callReply extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String ticket_id;

        public callReply(String ticket_id) {
            this.ticket_id = ticket_id;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            /*pd = new ProgressDialog(ReplyActivity.this);
            pd.setMessage("Loading");
            pd.show();*/

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("view", "raw");
            data.put("iaction", "reply");
            data.put("message", strEtDesc);
            data.put("user_id", Utils.ReadSharePrefrence(ReplyActivity.this, CIVIT_ID));
            data.put("id", ticket_id);
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // pd.dismiss();
//            Log.e("RESPONSE",""+s);
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("status").toString().equalsIgnoreCase("true")) {
                 //   Toast.makeText(ReplyActivity.this, "Your reply has been sent", Toast.LENGTH_LONG).show();
                    new callViewTicketDetail(ticket_id).execute();
                    //replyAdapter.notifyDataSetChanged();
                    editTxtDescription.setText("");
                } else {
                    Toast.makeText(ReplyActivity.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class callCloseTicket extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String ticket_id;

        public callCloseTicket(String ticket_id) {
            this.ticket_id = ticket_id;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(ReplyActivity.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("view", "raw");
            data.put("iaction", "close");
            data.put("id", ticket_id);

            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
//            Log.e("RESPONSE",""+s);
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("status").toString().equalsIgnoreCase("true")) {
                    Toast.makeText(ReplyActivity.this, "Ticket Close", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
