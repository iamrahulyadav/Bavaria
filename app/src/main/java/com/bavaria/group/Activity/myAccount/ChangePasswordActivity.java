package com.bavaria.group.Activity.myAccount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Activity.Login;
import com.bavaria.group.Activity.MyAccountActivity;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;
import com.bavaria.group.retrofit.ApiClient;
import com.bavaria.group.retrofit.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bavaria.group.Constant.Constant.ID;

public class ChangePasswordActivity extends BaseAppCompactActivity {

    EditText etOldPass, etNewPass, etConfirmPass;
    TextView tvUpdate, tvBack;
    ImageView tvLogout;
    String strOldPassword, strNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etOldPass = (EditText) findViewById(R.id.chngepass_oldPass);
        etNewPass = (EditText) findViewById(R.id.chngepass_newPass);
        etConfirmPass = (EditText) findViewById(R.id.chngepass_confirmPass);
        tvUpdate = (TextView) findViewById(R.id.chngepass_update);
        tvBack = (TextView) findViewById(R.id.chngePass_btnBack);
        tvLogout = (ImageView) findViewById(R.id.chngePass_logout);

        tvUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(etOldPass.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter Old Password.", Toast.LENGTH_SHORT).show();
                }
                else if(etNewPass.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter New Password.", Toast.LENGTH_SHORT).show();
                }
                else if(etConfirmPass.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter Confirm Password.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    strOldPassword = etOldPass.getText().toString();
                    strNewPassword = etNewPass.getText().toString();
                    new GetChangePassword().execute();
                }
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);

            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getPopupWindow(ChangePasswordActivity.this);

                /*Utils.ClearSharePrefrence(ChangePasswordActivity.this);
                Toast.makeText(ChangePasswordActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();*/
            }
        });
    }

    public void callChangePassword()
    {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("view", "raw");
        data.put("iaction", "cp");
        data.put("civil_id", Utils.ReadSharePrefrence(ChangePasswordActivity.this, ID));
        data.put("opassword", etOldPass.getText().toString());
        data.put("password", etNewPass.getText().toString());
        Call<JsonObject> loginCall = apiInterface.chngePassword(data);
        loginCall.enqueue(new Callback<JsonObject>()
        {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {

                if (response.body() != null)
                {
                    if (response.body().get("status").toString().equalsIgnoreCase("true"))
                    {
                        Toast.makeText(ChangePasswordActivity.this, response.body().get("msg").toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePasswordActivity.this, MyAccountActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                    }
                    else
                    {
                        Toast.makeText(ChangePasswordActivity.this, response.body().get("msg").toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    public class   GetChangePassword extends AsyncTask<String, String, String>
    {
        ProgressDialog pd;

        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();
            pd = new ProgressDialog(ChangePasswordActivity.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params)
        {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("view", "raw");
            data.put("iaction", "cp");
            data.put("civil_id", Utils.ReadSharePrefrence(ChangePasswordActivity.this, ID));
            data.put("opassword", strOldPassword);
            data.put("password", strNewPassword);
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/index.php?", data);
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("status").toString().equalsIgnoreCase("true")) {
                    Toast.makeText(ChangePasswordActivity.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();

                    Utils.ClearSharePrefrence(ChangePasswordActivity.this);
                    Intent intent = new Intent(ChangePasswordActivity.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                } else {
                    Toast.makeText(ChangePasswordActivity.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
