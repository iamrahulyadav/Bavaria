package com.bavaria.group.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;

/**
 * Created by archirayan4 on 5/6/2016.
 */

public class WebsiteActivity extends BaseAppCompactActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        webView = (WebView) findViewById(R.id.activity_website_Wv);
        webView.loadUrl("http://www.bavariagroup.net");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }
}
