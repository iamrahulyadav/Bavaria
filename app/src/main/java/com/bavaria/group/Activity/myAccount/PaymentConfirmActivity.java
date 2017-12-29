package com.bavaria.group.Activity.myAccount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;

public class PaymentConfirmActivity extends BaseAppCompactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }
}
