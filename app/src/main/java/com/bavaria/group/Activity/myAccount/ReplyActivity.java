package com.bavaria.group.Activity.myAccount;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.bavaria.group.Constant.Constant.CIVIT_ID;

public class ReplyActivity extends BaseAppCompactActivity {

    public TextView reply_msg;
    TextView ediTxtSubject, ediTxtDepartment, ediTxtSubmited, ediTxttTicketStatus,
            ediTxtPriority, editTxtDescription;
    RecyclerView rlMessage;
    TextView reply_btnBack, txtTicketNo;
    Button btnReply, btnCloseTicket;
    ImageView reply_logout;
    ReplyAdapter replyAdapter;
    Intent intent;
    String ticket_id, strEtDesc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_reply);

        intent = getIntent();
        ticket_id = intent.getStringExtra("ticket_id");

        initViews();

        new callViewTicketDetail(ticket_id).execute();
    }

    private void initViews() {

        reply_btnBack = findViewById(R.id.reply_btnBack);
        txtTicketNo = findViewById(R.id.txtVwTcktNo);

        ediTxtSubject = findViewById(R.id.ediTxtSubject);
        ediTxtDepartment = findViewById(R.id.ediTxtDepartment);
        ediTxtSubmited = findViewById(R.id.ediTxtSubmited);
        ediTxttTicketStatus = findViewById(R.id.ediTxttTicketStatus);
        ediTxtPriority = findViewById(R.id.ediTxtPriority);
        rlMessage = findViewById(R.id.rlMessage);
        editTxtDescription = (EditText) findViewById(R.id.editTxtDescription);
        reply_msg = findViewById(R.id.reply_msg);
        reply_logout = findViewById(R.id.reply_logout);
        btnReply = findViewById(R.id.btnReply);
        btnCloseTicket = findViewById(R.id.btnCloseTicket);

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
                    strEtDesc = editTxtDescription.getText().toString();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    @SuppressLint("StaticFieldLeak")
    public class callViewTicketDetail extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String ticket_id;

        callViewTicketDetail(String ticket_id) {
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
            HashMap<String, String> data = new HashMap<>();

            data.put("view", "raw");
            data.put("page", "ticketdetail");
            data.put("iview", "json");
            data.put("id", ticket_id);
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //pd.dismiss();
            Log.e("RESPONSE", "" + s);
            try {
                JSONArray jsonArray = new JSONArray(s);

                JSONObject object = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    object = jsonArray.getJSONObject(i);
                }

                if (object != null && !object.getString("status").equalsIgnoreCase("Closed")) {
                    btnCloseTicket.setVisibility(View.VISIBLE);
                    btnReply.setVisibility(View.VISIBLE);
                    editTxtDescription.setVisibility(View.VISIBLE);
                }

                if (object != null) {
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

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class callReply extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String ticket_id;

        callReply(String ticket_id) {
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
            HashMap<String, String> data = new HashMap<>();

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
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {
                    //   Toast.makeText(ReplyActivity.this, "Your reply has been sent", Toast.LENGTH_LONG).show();
                    new callViewTicketDetail(ticket_id).execute();
                    //replyAdapter.notifyDataSetChanged();
                    editTxtDescription.setText("");
                } else {
                    Toast.makeText(ReplyActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class callCloseTicket extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String ticket_id;

        callCloseTicket(String ticket_id) {
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
            HashMap<String, String> data = new HashMap<>();

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
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {
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
