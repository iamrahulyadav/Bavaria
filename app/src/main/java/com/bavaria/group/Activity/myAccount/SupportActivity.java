package com.bavaria.group.Activity.myAccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bavaria.group.Adapter.SupportContactAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.Utils;

public class SupportActivity extends BaseAppCompactActivity implements View.OnClickListener {

    TextView tvCallus, tvBack, tvCancel;
    LinearLayout llChat, llOpenTicket, llMyTicket, llFaq;
    ImageView ivLogout;
    TextView myAcc_tvName, myAcc_tvEmail;

    RelativeLayout llBottom;
    RecyclerView contactRecycler;
    SupportContactAdapter supportContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        tvCallus = findViewById(R.id.support_callus);
        llChat = findViewById(R.id.support_llChat);
        llOpenTicket = findViewById(R.id.support_llOpenTicket);
        llMyTicket = findViewById(R.id.support_llMyTicket);
        llFaq = findViewById(R.id.support_llFaq);
        contactRecycler = findViewById(R.id.openticket_contactlist);
        tvCancel = findViewById(R.id.openticket_btnCancel);
        llBottom = findViewById(R.id.llBottom);

        tvBack = findViewById(R.id.support_btnBack);
        ivLogout = findViewById(R.id.support_logout);
        myAcc_tvName = findViewById(R.id.myAcc_tvName);
        myAcc_tvEmail = findViewById(R.id.myAcc_tvEmail);
        contactRecycler.setLayoutManager(new LinearLayoutManager(SupportActivity.this, LinearLayoutManager.VERTICAL, false));
        supportContactAdapter = new SupportContactAdapter(SupportActivity.this);
        contactRecycler.setAdapter(supportContactAdapter);

        tvCallus.setOnClickListener(this);
        llChat.setOnClickListener(this);
        llOpenTicket.setOnClickListener(this);
        llMyTicket.setOnClickListener(this);
        llFaq.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        ivLogout.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        myAcc_tvName.setText(Utils.ReadSharePrefrence(SupportActivity.this, Constant.USERNAME));
        myAcc_tvEmail.setText(Utils.ReadSharePrefrence(SupportActivity.this, Constant.EMAIL));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.support_callus:
                llBottom.setVisibility(View.VISIBLE);
                break;

            case R.id.support_llChat:
                Intent in = new Intent(SupportActivity.this, ChatActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.support_llOpenTicket:
                Intent openTicketIntent = new Intent(SupportActivity.this, OpenTicketActivity.class);
                startActivity(openTicketIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.support_llMyTicket:
                Intent myTicketIntent = new Intent(SupportActivity.this, MyTicketActivity.class);
                startActivity(myTicketIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.support_llFaq:
                Intent faqIntent = new Intent(SupportActivity.this, FaqActivity.class);
                startActivity(faqIntent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.support_btnBack:
                finish();
                break;

            case R.id.support_logout:
                Utils.getPopupWindow(SupportActivity.this);

               /* Utils.ClearSharePrefrence(SupportActivity.this);
                Toast.makeText(SupportActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(SupportActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();*/
                break;

            case R.id.openticket_btnCancel:
                llBottom.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }
}
