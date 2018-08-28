package com.bavaria.group.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.MakeServiceCall;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by archirayan on 2/24/2016.
 */
public class Register extends BaseAppCompactActivity implements View.OnClickListener {
    EditText civilidEdt, phoneEdt;
    ImageView ivBack;
    String strCivilId, strPhoneNo;
    getSignUp object;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        civilidEdt = findViewById(R.id.activity_register_username);
        phoneEdt = findViewById(R.id.activity_register_phone);
//        passwordEdt = (EditText) findViewById(R.id.activity_register_password);
//        phonenumberEdt = (EditText) findViewById(R.id.activity_register_phno);
        ivBack = findViewById(R.id.activity_register_back);

        submit = findViewById(R.id.activity_register_submit);
        submit.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_register_submit:
                strCivilId = civilidEdt.getText().toString();
                strPhoneNo = phoneEdt.getText().toString();
                if (isValid()) {
                    new getSignUp().execute();
                    // callRegister();
                }

                break;

            case R.id.activity_register_back:
                finish();
                break;
        }
//            username = usernameEdt.getText().toString();
//            email = emailEdt.getText().toString();
//            password = passwordEdt.getText().toString();
//            phonenumber = phonenumberEdt.getText().toString();
//            if (Utils.isOnline(getApplicationContext())) {
////                if (username.length() > 3 && password.length() > 2) {
////                    object = new getSignUp();
////                    object.execute();
////                } else {
////                    Toast.makeText(Register.this, "Please Insert Valid Data", Toast.LENGTH_SHORT).show();
////                }
//            } else {
////                Toast.makeText(Register.this, "No internet connectivity found, Please check your internet connection", Toast.LENGTH_SHORT).show();
//            }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
//        if (object != null) {
//            object.cancel(true);
//        }
    }
//
//    public void callRegister() {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        HashMap<String, String> data = new HashMap<String, String>();
//        data.put("view", "raw");
//        data.put("iaction", "register");
//        data.put("civil_id", civilidEdt.getText().toString());
//        data.put("phone_no", phoneEdt.getText().toString());
//
//        Call<GetRegister> loginCall = apiInterface.register(data);
//        loginCall.enqueue(new Callback<GetRegister>() {
//            @Override
//            public void onResponse(Call<GetRegister> call, Response<GetRegister> response) {
//
//                if (response.body() != null) {
//
//                    Toast.makeText(Register.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetRegister> call, Throwable t) {
//                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public boolean isValid() {

        boolean isvalid = true;

        if (TextUtils.isEmpty(civilidEdt.getText().toString())) {
            civilidEdt.setError("Please Enter Valid Civil ID");
            isvalid = false;
        }

        if (TextUtils.isEmpty(phoneEdt.getText().toString())) {
            phoneEdt.setError("Please Enter Password");
            isvalid = false;
        }

        return isvalid;
    }

    @SuppressLint("StaticFieldLeak")
    public class getSignUp extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(Register.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();
            data.put("view", "raw");
            data.put("iaction", "register");
            data.put("civil_id", strCivilId);
            data.put("phone_no", strPhoneNo);
            // https://www.bavariagroup.net/?view=raw&iaction=register&civil_id=12232323&phone_no=1212121
            return new MakeServiceCall().MakeServiceCall(Constant.NEW_BASE_URL + "/?view=raw&iaction=register&civil_id=" + strCivilId + "&phone_no=" + strPhoneNo);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            Log.e("RESPONSE", "" + s);
            try {
                JSONObject object = new JSONObject(s);

                if (object.getString("error").equalsIgnoreCase("true")) {
                    Log.e("RESPONSE", "" + s);
                    final Dialog dialog = new Dialog(Register.this);
                    dialog.setContentView(R.layout.loginalert_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.CENTER);
                    Button yesDialogBtn = dialog.findViewById(R.id.btn_ok);
                    TextView register_txtVwTitle = dialog.findViewById(R.id.register_txtVwTitle);
                    String strMsg = object.getString("msg");
                    register_txtVwTitle.setText(strMsg);

                    yesDialogBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                } else if (object.getString("successful").equalsIgnoreCase("true")) {
//                    Toast.makeText(Register.this, "Sucessfully Register", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                    // Toast.makeText(Register.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
