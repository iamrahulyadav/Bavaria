package com.bavaria.group.Activity.myAccount;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;

import static android.R.attr.digits;
import static com.bavaria.group.Constant.Constant.CIVIT_ID;

public class PayNowActivity extends BaseAppCompactActivity {

    WebView web;
    Intent intent;
    String amount, bill_id, building_id, payment_type, payment_othername;
    ProgressDialog progressBar;
    String digits;
    Button paynow_btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);

        intent = getIntent();
        amount = intent.getStringExtra("pay Amount");
        bill_id = intent.getStringExtra("bill id");
        building_id = intent.getStringExtra("building id");
        payment_type = intent.getStringExtra("payment type");
        //     payment_othername = intent.getStringExtra("payment othername");
        paynow_btnBack = (Button) findViewById(R.id.paynow_btnBack);
        paynow_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        web = (WebView) findViewById(R.id.webview);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //     progressBar = ProgressDialog.show(PayNowActivity.this, " ", "Loading...");

       // https://www.bavariagroup.net/pay-online/?civil_id=90075698&amount=20&action=payment&bill_id=155&payment_type=Yearly%20Maintenance&building_id=117
        String url = ("https://www.bavariagroup.net/pay-online/?civil_id=" + Utils.ReadSharePrefrence(PayNowActivity.this, CIVIT_ID) + "&amount=" + amount + "&action=payment&bill_id=" + bill_id + "&payment_type=" + payment_type.replaceAll("%20"," ") + "&building_id=" + building_id);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        web.loadUrl(url);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }
}