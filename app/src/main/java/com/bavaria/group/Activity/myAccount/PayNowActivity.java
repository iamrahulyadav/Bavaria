package com.bavaria.group.Activity.myAccount;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;

import static com.bavaria.group.Constant.Constant.CIVIT_ID;

public class PayNowActivity extends BaseAppCompactActivity {

    WebView web;
    Intent intent;
    String amount, bill_id, building_id, payment_type, name, phone_number, Email, project_name, floor_name, flat_name;
    Button paynow_btnBack;
    String url;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);


        intent = getIntent();
        amount = intent.getStringExtra("pay Amount");
        bill_id = intent.getStringExtra("bill id");
        building_id = intent.getStringExtra("building id");
        payment_type = intent.getStringExtra("payment type");
        name = intent.getStringExtra("name");
        phone_number = intent.getStringExtra("phoneNumber");
        Email = intent.getStringExtra("Email_id");
        project_name = intent.getStringExtra("project_name");
        floor_name = intent.getStringExtra("floor_name");
        flat_name = intent.getStringExtra("flat_name");
        paynow_btnBack = findViewById(R.id.paynow_btnBack);
        paynow_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        web = findViewById(R.id.webview);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //     progressBar = ProgressDialog.show(PayNowActivity.this, " ", "Loading...");

        // https://www.bavariagroup.net/pay-online/?civil_id=90075698&amount=20&action=payment&bill_id=155&payment_type=Yearly%20Maintenance&building_id=117
        url = ("https://www.bavariagroup.net/pay-online/?civil_id=" + Utils.ReadSharePrefrence(PayNowActivity.this, CIVIT_ID)
                + "&amount=" + amount + "&action=payment&bill_id=" + bill_id + "&payment_type="
                + payment_type.replaceAll("%20", " ") + "&building_id=" + building_id
                + "&name=" + name + "&phone_num=" + phone_number + "&email_id=" + Email + "&guest_email="
                + Email + "&project_name=" + project_name + "&floor_name=" + floor_name + "&flat_name=" + flat_name);

        Log.e("TAG", "" + url);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
                Log.d("CHECK", "onReceivedSslError");

                if (Utils.ReadSharePrefrence(PayNowActivity.this, Constant.SHRED_PR.IS_SHOWN).equalsIgnoreCase("true")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PayNowActivity.this);
                    AlertDialog alertDialog = builder.create();
               /* String message = "Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }*/
                    String message = " Do you want to continue?";
                    // alertDialog.setTitle("SSL Certificate");
                    alertDialog.setMessage(message);
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Ignore SSL certificate errors

                            handler.proceed();
                            Utils.WriteSharePrefrence(PayNowActivity.this, Constant.SHRED_PR.IS_SHOWN, "false");
                        }
                    });
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            handler.cancel();
                            onBackPressed();
                        }
                    });
                    alertDialog.show();

                }
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

/*
web.setWebViewClient(new WebViewClient() {
@Override
public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
        Log.d("CHECK", "onReceivedSslError");

//                AlertDialog.Builder builder = new AlertDialog.Builder(PayNowActivity.this);
//                AlertDialog alertDialog = builder.create();
//                String message = "Certificate error.";
//                switch (error.getPrimaryError()) {
//                    case SslError.SSL_UNTRUSTED:
//                        message = "The certificate authority is not trusted.";
//                        break;
//                    case SslError.SSL_EXPIRED:
//                        message = "The certificate has expired.";
//                        break;
//                    case SslError.SSL_IDMISMATCH:
//                        message = "The certificate Hostname mismatch.";
//                        break;
//                    case SslError.SSL_NOTYETVALID:
//                        message = "The certificate is not yet valid.";
//                        break;
//                }
//                message += " Do you want to continue anyway?";
//                alertDialog.setTitle("SSL Certificate Error");
//                alertDialog.setMessage(message);
//                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Ignore SSL certificate errors

        handler.proceed();
//                    }
//                });
//                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        handler.cancel();
//                    }
//                });
//                alertDialog.show();
        }
        });

        web.loadUrl(url);

*/
