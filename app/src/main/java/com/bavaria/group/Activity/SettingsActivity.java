package com.bavaria.group.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;

/**
 * Created by archirayan1 on 6/6/2016.
 */
public class SettingsActivity extends BaseAppCompactActivity implements View.OnClickListener {
    TextView uName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        uName = findViewById(R.id.tvUserName_SettingsActivity);
        if (Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_LOGGEDIN).equalsIgnoreCase("1")) {
            Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_UNAME);
            uName.setText(Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_UNAME));
        } else {
            uName.setText("Login");
        }

        uName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SettingsActivity.this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

}
