package com.bavaria.group.Activity.myAccount;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateProfileActivity extends BaseAppCompactActivity implements OnClickListener {

    EditText etEmail, etPhonenumber;
    TextView tvSave, tvChngePass, tvBack, tvUname;
    ImageView ivLogout;
    String strPhoneNo, strEmail;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        tvUname = findViewById(R.id.user_name);
        etEmail = findViewById(R.id.updateprofile_email);
        etPhonenumber = findViewById(R.id.updateprofile_phonenumber);
        tvSave = findViewById(R.id.updateprofile_save);
        tvChngePass = findViewById(R.id.updateprofile_chngepass);
        tvBack = findViewById(R.id.updateprofile_btnBack);
        ivLogout = findViewById(R.id.updateprofile_logout);

        tvSave.setOnClickListener(this);
        tvChngePass.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        ivLogout.setOnClickListener(this);
        tvUname.setText("Hello," + Utils.ReadSharePrefrence(UpdateProfileActivity.this, Constant.USERNAME));
        // callUserProfile();
        new GetUserProfile().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateprofile_btnBack:
                finish();
                break;

            case R.id.updateprofile_logout:
                Utils.getPopupWindow(UpdateProfileActivity.this);

                /*Utils.ClearSharePrefrence(UpdateProfileActivity.this);
                Toast.makeText(UpdateProfileActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(UpdateProfileActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();*/
                break;

            case R.id.updateprofile_chngepass:
                Intent chngepassIntent = new Intent(UpdateProfileActivity.this, ChangePasswordActivity.class);
                startActivity(chngepassIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

            case R.id.updateprofile_save:
                strEmail = etEmail.getText().toString();
                strPhoneNo = etPhonenumber.getText().toString();
                new GetUpdateProfile().execute();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    @SuppressLint("StaticFieldLeak")
    public class GetUpdateProfile extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(UpdateProfileActivity.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();

            data.put("view", "raw");
            data.put("iaction", "update_profile");
            data.put("civil_id", Utils.ReadSharePrefrence(UpdateProfileActivity.this, Constant.CIVIT_ID));
            data.put("phone_no", etPhonenumber.getText().toString());
            data.put("email", etEmail.getText().toString());

//            https://www.bavariagroup.net/index.php?view=raw&iaction=user_profile&civil_id=90075698
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
//            Log.e("RESPONSE",""+s);
            Toast.makeText(UpdateProfileActivity.this, "Your profile has been update", Toast.LENGTH_SHORT).show();

        }
    }

    @SuppressLint("StaticFieldLeak")
    public class GetUserProfile extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(UpdateProfileActivity.this);
            pd.setMessage("Loading");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();

            data.put("view", "raw");
            data.put("iaction", "user_profile");
            data.put("civil_id", Utils.ReadSharePrefrence(UpdateProfileActivity.this, Constant.CIVIT_ID));

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
                if (object.getString("successful").equalsIgnoreCase("true")) {

                    etEmail.setText(object.getString("email"));
                    etPhonenumber.setText(object.getString("phone_no"));
                    tvUname.setText(object.getString("name"));

                } else {
                    Toast.makeText(UpdateProfileActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
