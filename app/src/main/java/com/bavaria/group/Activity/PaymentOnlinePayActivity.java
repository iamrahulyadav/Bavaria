package com.bavaria.group.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;

import static com.bavaria.group.Constant.Constant.CIVIT_ID;

/**
 * Created by Archirayan on 08-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */


public class PaymentOnlinePayActivity extends BaseAppCompactActivity {

    WebView web;

    Intent intent;
    String amount, digits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);

        intent = getIntent();

        amount = intent.getStringExtra("amt");
        digits = amount.replaceAll("[^0-9]", "");

        web = (WebView) findViewById(R.id.webview);
        web.loadUrl("https://www.bavariagroup.net/pay-online/?civil_id=" + Utils.ReadSharePrefrence(PaymentOnlinePayActivity.this, CIVIT_ID) + "&amount=" + digits + "&action=payment");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

}
