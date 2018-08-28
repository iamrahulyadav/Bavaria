package com.bavaria.group.Activity.myAccount;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Adapter.FaqAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.OnItemClickListener;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.Model.FaqPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FaqActivity extends BaseAppCompactActivity implements OnItemClickListener {

    RecyclerView faq_recycler_view;
    FaqAdapter faqAdapter;
    TextView ivBack;
    ImageView faq_logout;
    ArrayList<FaqPojo> faqPojos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_faq);
        initViews();
        new getFaq().execute();
    }

    private void initViews() {
        faq_recycler_view = findViewById(R.id.Faq_recycler_view);
        ivBack = findViewById(R.id.faq_btnBack);
        faq_logout = findViewById(R.id.faq_logout);

        faq_recycler_view.setLayoutManager(new LinearLayoutManager(FaqActivity.this, LinearLayoutManager.VERTICAL, false));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        faq_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getPopupWindow(FaqActivity.this);

               /* Utils.ClearSharePrefrence(FaqActivity.this);
                Toast.makeText(FaqActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(FaqActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();*/
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }


    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(FaqActivity.this, FaqViewActivity.class);
        intent.putExtra("title", faqPojos.get(position).getComment_count());
        intent.putExtra("content", faqPojos.get(position).getPost_content());
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
    }

    @SuppressLint("StaticFieldLeak")
    public class getFaq extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            faqPojos = new ArrayList<>();
            pd = new ProgressDialog(FaqActivity.this);
            pd.setMessage("Loading");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();
            data.put("view", "json");
            data.put("page", "faq");
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
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
                    FaqPojo faqPojo = new FaqPojo();
                    faqPojo.setComment_count(object.getString("post_title"));
                    faqPojo.setPost_content(object.getString("post_content"));
                    faqPojos.add(faqPojo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (faqPojos != null) {
                faqAdapter = new FaqAdapter(FaqActivity.this, faqPojos, FaqActivity.this);
                faq_recycler_view.setAdapter(faqAdapter);
            } else {
                Toast.makeText(FaqActivity.this, R.string.datanotfound, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
