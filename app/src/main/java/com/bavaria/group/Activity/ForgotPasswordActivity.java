package com.bavaria.group.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends BaseAppCompactActivity implements View.OnClickListener {

    public TextView backImage, btnForgot;
    public EditText userNameEdt;
    public Utils utils;
    public String str_CivilId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
        backImage.setOnClickListener(this);
        btnForgot.setOnClickListener(this);

    }

    private void init() {
        backImage = (TextView) findViewById(R.id.activity_login_back);
        userNameEdt = (EditText) findViewById(R.id.activity_login_username);
        btnForgot = (TextView) findViewById(R.id.activity_btnforgotpsw);
        utils=new Utils();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_btnforgotpsw:
                forgotPassword();
                break;

            case R.id.activity_login_back:
                finish();
                break;
        }
    }

    private void forgotPassword()
    {
            if(userNameEdt.getText().toString().trim().equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Please insert Civil Id", Toast.LENGTH_SHORT).show();
            }
            else
            {
                new forgetpasswordApi().execute();
            }
    }

    private class forgetpasswordApi extends AsyncTask<String,String,String>
    {
        public ProgressDialog pd;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd=new ProgressDialog(ForgotPasswordActivity.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            str_CivilId=userNameEdt.getText().toString().trim();
            return new MakeServiceCall().MakeServiceCall("https://www.bavariagroup.net/index.php?view=raw&iaction=forget&civil_id="+str_CivilId);
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject jsonObject=new JSONObject(s.toString());
                if(jsonObject.getString("successful").equalsIgnoreCase("true"))
                {
                    Toast.makeText(ForgotPasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(ForgotPasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}