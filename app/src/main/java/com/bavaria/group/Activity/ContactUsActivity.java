package com.bavaria.group.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bavaria.group.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by archirayan4 on 5/5/2016.
 */
public class ContactUsActivity extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback {

    TextView liveChatTxt, emailTxt, websiteTxt;
    //    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    ImageView ivBack, ivFb, ivTwitter, ivInstagram, ivYouTube;
    Button btnAppStoteApple, btnPlayStore, btnSharelocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        init();

    }

    private void init() {
        liveChatTxt = findViewById(R.id.activity_contactup_live_chat_Txt);
        emailTxt = findViewById(R.id.activity_contactup_email_Txt);
        websiteTxt = findViewById(R.id.activity_contactup_website_Txt);
        ivBack = findViewById(R.id.ivBack_ContactUsActivity);
        ivFb = findViewById(R.id.ivFacebook_AboutusActivity);
        ivTwitter = findViewById(R.id.ivTwiiter_AboutusActivity);
        ivInstagram = findViewById(R.id.ivInstagram_AboutusActivity);
        ivYouTube = findViewById(R.id.ivYouTube_AboutusActivity);
        // ivClose = (ImageView) findViewById(R.id.ivCloseContactus);
//        FrameLayout flMapZoom = findViewById(R.id.flMapView);
        btnAppStoteApple = findViewById(R.id.btnappstoreapple);
        btnPlayStore = findViewById(R.id.btngoogleplaystore);
        btnSharelocation = findViewById(R.id.btn_sharelocation);


        emailTxt.setOnClickListener(this);
        liveChatTxt.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivYouTube.setOnClickListener(this);
        ivFb.setOnClickListener(this);
        ivInstagram.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
//        ivClose.setOnClickListener(this);
        btnAppStoteApple.setOnClickListener(this);
        btnPlayStore.setOnClickListener(this);
        btnSharelocation.setOnClickListener(this);


    }


/*
    @Override
    public void onMapReady(GoogleMap map) {
        map = map;
        mapZoom = map;
        //LatLng sydney = new LatLng(29.375859, 47.977405);
        LatLng sydney = new LatLng(23.0178047, 72.5549591);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Kuwait")
                .snippet("Bavaria Group")
                .position(sydney));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                flMapZoom.setVisibility(View.VISIBLE);
                showNewMapWithZoom();
            }
        });

    }*/

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
           /* case R.id.ivCloseContactus:
                flMapZoom.setVisibility(View.GONE);
                break;*/
            case R.id.ivBack_ContactUsActivity:
                intent = new Intent(ContactUsActivity.this, MainActivity.class);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                startActivity(intent);
                finish();
                break;

            case R.id.activity_contactup_email_Txt:
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse("Support@bavariagroup.net"));
                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Support@bavariagroup.net"});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "A Question from Bavaria");
                //   sendIntent.putExtra(Intent.EXTRA_TEXT, "hello. this is a message sent from my demo app :-)");
                startActivity(sendIntent);
                break;

            case R.id.activity_contactup_live_chat_Txt:
                intent = new Intent(ContactUsActivity.this, LiveChatActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.activity_contactup_website_Txt:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bavariagroup.net"));
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.ivFacebook_AboutusActivity:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bavari.grop"));
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.ivTwiiter_AboutusActivity:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/bavariakuwait"));
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.ivInstagram_AboutusActivity:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/bavariagroup/?hl=en"));
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.ivYouTube_AboutusActivity:
//                https://www.youtube.com/watch?v=T6ielvSc7DU&list=UUrcfXe9vn1VW58Y-Dy0Y7Sw
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=T6ielvSc7DU&list=UUrcfXe9vn1VW58Y-Dy0Y7Sw"));
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.btnappstoreapple:
                sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Bavaria iPhone App: https://itunes.apple.com/us/app/bavaria/id1013155091?mt=8");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);


            case R.id.btngoogleplaystore:
                sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Bavaria Android App: https://play.google.com/store/apps/details?id=com.bavaria.group&hl=en");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;


            case R.id.btn_sharelocation:

                sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Bavaria's location: https://www.google.co.in/maps?q=loc:29.3758593,47.977405");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //mycoordinate.latitude =29.375859;    mycoordinate.longitude =47.977405;
        LatLng latlong = new LatLng(29.375859, 47.977405);
        googleMap.addMarker(new MarkerOptions().position(latlong).title(""));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 9.0f));
    }
}
