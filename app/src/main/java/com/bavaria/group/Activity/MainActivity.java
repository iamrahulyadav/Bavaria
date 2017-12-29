package com.bavaria.group.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bavaria.group.Activity.myAccount.SupportActivity;
import com.bavaria.group.Adapter.MainScreenAdapter;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;

import static com.bavaria.group.Constant.Constant.IS_LOGIN;


public class MainActivity extends BaseAppCompactActivity implements View.OnClickListener {
    LinearLayout llMyAccount, llMaintenance, llPayOnline, llAboutUs, llProjects, llPhotoVideo;
    FrameLayout flLoginBottom, flMain;
    TextView tvInfo, tvLogin, tvSettings;
    MainScreenAdapter adapter;
    Button mContactBtn, mLoginBtn, btContactus;
    Animation slide_down, slide_up;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btContactus = (Button) findViewById(R.id.btContactus);
        mLoginBtn = (Button) findViewById(R.id.activity_main_login_Btn);
        llMaintenance = (LinearLayout) findViewById(R.id.llMaintenance_MainActivity);
        llPayOnline = (LinearLayout) findViewById(R.id.llPayOnline_MainActivity);
        llAboutUs = (LinearLayout) findViewById(R.id.llAboutUs_MainActivity);
        llProjects = (LinearLayout) findViewById(R.id.llProjects_MainActivity);
        llPhotoVideo = (LinearLayout) findViewById(R.id.llPhotoVideo_MainActivity);
        flLoginBottom = (FrameLayout) findViewById(R.id.flLogin_MainActivity);
        flMain = (FrameLayout) findViewById(R.id.flMain_MainActivity);
        llMyAccount = (LinearLayout) findViewById(R.id.llAccount_MyAccountActivity);
        tvInfo = (TextView) findViewById(R.id.tvInformation_MainActivity);
        tvSettings = (TextView) findViewById(R.id.tvSettings_MainActivity);
        tvLogin = (TextView) findViewById(R.id.tvLogin_MainActivity);
        mLoginBtn.setOnClickListener(this);
        btContactus.setOnClickListener(this);
        llMaintenance.setOnClickListener(this);
        llPayOnline.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llProjects.setOnClickListener(this);
        llPhotoVideo.setOnClickListener(this);
        tvInfo.setOnClickListener(this);
        tvSettings.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        llMyAccount.setOnClickListener(this);

        //Load animation
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        flMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        if (Utils.ReadSharePrefrence(getApplicationContext(), IS_LOGIN).equalsIgnoreCase("true")) {
            tvLogin.setText("My Account");
        } else {
            tvLogin.setText("Login");
        }

    }


    @Override
    public void onClick(View v) {
        Intent in;
        switch (v.getId())
        {
            case R.id.btContactus:
                in = new Intent(getApplicationContext(), ContactUsActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.llMaintenance_MainActivity:
                if (Utils.ReadSharePrefrence(MainActivity.this, IS_LOGIN).equalsIgnoreCase("true"))
                {
                    in = new Intent(getApplicationContext(), SupportActivity.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                } else {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                }

                break;

            case R.id.llPayOnline_MainActivity:
                in = new Intent(getApplicationContext(), PayOnlineMainActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.llAboutUs_MainActivity:
                in = new Intent(getApplicationContext(), AboutUsActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.llProjects_MainActivity:
                in = new Intent(getApplicationContext(), ProjectListaActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.llPhotoVideo_MainActivity:
                in = new Intent(getApplicationContext(), PhotoVideoActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;


            case R.id.tvInformation_MainActivity:
                flLoginBottom.startAnimation(slide_down);
                flLoginBottom.setVisibility(View.GONE);
                flMain.setVisibility(View.GONE);
                in = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.tvSettings_MainActivity:
                flLoginBottom.startAnimation(slide_down);
                flLoginBottom.setVisibility(View.GONE);
                flMain.setVisibility(View.GONE);
                in = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.tvLogin_MainActivity:
                flLoginBottom.startAnimation(slide_down);
                flLoginBottom.setVisibility(View.GONE);
                flMain.setVisibility(View.GONE);
                in = new Intent(MainActivity.this, Login.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.activity_main_login_Btn:

                Intent loginIntent = new Intent(MainActivity.this, Login.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                if (Utils.ReadSharePrefrence(MainActivity.this, IS_LOGIN).equalsIgnoreCase("true"))
                {
                    Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                }

                break;

            case R.id.llAccount_MyAccountActivity:
                if (Utils.ReadSharePrefrence(MainActivity.this, IS_LOGIN).equalsIgnoreCase("true")) {
                    Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                } else {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                }
                break;
        }
    }
}