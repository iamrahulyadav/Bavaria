package com.bavaria.group.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bavaria.group.R;

import java.lang.reflect.InvocationTargetException;

import im.delight.android.webview.AdvancedWebView;

/**
 * Created by archirayan4 on 5/17/2016.
 */
public class VideoWeb extends AppCompatActivity implements AdvancedWebView.Listener {
    final Activity activity = this;
    ImageView icBackImg;
    //    TextView proNameTxt, descTxt;
    String vid, proName, proDesc;
    AdvancedWebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoweb);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            vid = extras.getString("video");
        }
        if (extras != null) {
            proName = extras.getString("pro_name");
            proDesc = extras.getString("pro_small_desc");

        }

        mWebView = findViewById(R.id.wvVideo_VideoWeb);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
//        proNameTxt = (TextView) findViewById(R.id.activity_videoweb_pro_name_Txt);
//        descTxt = (TextView) findViewById(R.id.activity_videoweb_desc_Txt);
        icBackImg = findViewById(R.id.activity_videoweb_icBack_Img);
        icBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoWeb.this, VideoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setTitle("Loading...");
                activity.setProgress(progress * 100);
                if (progress == 100)
                    activity.setTitle(proName);
            }
        });

       /* mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle the error
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });*/

//        proNameTxt.setText("" + proName);
//        descTxt.setText("" + proDesc);
//        vwVideo.loadUrl("" + vid);
        mWebView.setListener(this, this);
        mWebView.setGeolocationEnabled(false);
        mWebView.setMixedContentAllowed(true);
        mWebView.setCookiesEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

        });
        mWebView.addHttpHeader("X-Requested-With", "");
        mWebView.loadUrl(vid);

    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(mWebView, (Object[]) null);

        } catch (ClassNotFoundException cnfe) {
            Log.e("EXCEPTION", "" + cnfe);
        } catch (NoSuchMethodException nsme) {
            Log.e("EXCEPTION", "" + nsme);
        } catch (InvocationTargetException ite) {
            Log.e("EXCEPTION", "" + ite);
        } catch (IllegalAccessException iae) {
            Log.e("EXCEPTION", "" + iae);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }


    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        mWebView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPageFinished(String url) {
        mWebView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        //Toast.makeText(ViewInvoiceActivity.this, "onPageError(errorCode = "+errorCode+",  description = "+description+",  failingUrl = "+failingUrl+")", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        //   Toast.makeText(ViewInvoiceActivity.this, "onDownloadRequested(url = "+url+",  suggestedFilename = "+suggestedFilename+",  mimeType = "+mimeType+",  contentLength = "+contentLength+",  contentDisposition = "+contentDisposition+",  userAgent = "+userAgent+")", Toast.LENGTH_LONG).show();

		/*if (AdvancedWebView.handleDownload(this, url, suggestedFilename)) {
            // download successfully handled
		}
		else {
			// download couldn't be handled because user has disabled download manager app on the device
		}*/
    }

    @Override
    public void onExternalPageRequest(String url) {
        //   Toast.makeText(ViewInvoiceActivity.this, "onExternalPageRequest(url = "+url+")", Toast.LENGTH_SHORT).show();
    }

}
