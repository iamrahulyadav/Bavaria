package com.bavaria.group.Activity.myAccount;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;

public class FaqViewActivity extends BaseAppCompactActivity
{

    TextView ivBack, tvPinningadsQue, tvPinningadsAns;
    ImageView faq_logout;

    Intent intent;
    String postTitle, postContent;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_view);
        intent = getIntent();
        postTitle = intent.getStringExtra("title");
        postContent = intent.getStringExtra("content");
        initViews();
    }

    private void initViews()
    {
        ivBack = (TextView) findViewById(R.id.faqView_btnBack);
        tvPinningadsQue = (TextView) findViewById(R.id.tvPinningadsQue);
        tvPinningadsAns = (TextView) findViewById(R.id.tvPinningadsAns);
        faq_logout = (ImageView) findViewById(R.id.faqView_logout);
        tvPinningadsQue.setText(postTitle);
        tvPinningadsAns.setText(postContent);
        ivBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        faq_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Utils.getPopupWindow(FaqViewActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }
}
