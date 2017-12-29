package com.bavaria.group.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;

public class ConfirmPaymentActivity extends BaseAppCompactActivity {

    TextView tvProjectnm, tvpaymenttype, tvPay, tvBack, tvBuilding, tvFLoor, tvFlat, tvAmt, tvTotal;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);

        intent = getIntent();

        tvProjectnm = (TextView) findViewById(R.id.project_nm);
        tvpaymenttype = (TextView) findViewById(R.id.paymenttype_nm);
        tvPay = (TextView) findViewById(R.id.btnPay_confirmPayment);
        tvTotal = (TextView) findViewById(R.id.confirmPayment_Amount);
        tvBack = (TextView) findViewById(R.id.btnBack_confirmPayment);
        tvBuilding = (TextView) findViewById(R.id.building_nm);
        tvFLoor = (TextView) findViewById(R.id.floor_nm);
        tvFlat = (TextView) findViewById(R.id.flat_nm);
        tvAmt = (TextView) findViewById(R.id.amount);

        tvProjectnm.setText(intent.getStringExtra("project name"));
        tvpaymenttype.setText(intent.getStringExtra("payment type"));
        tvBuilding.setText(intent.getStringExtra("Building"));
        tvFLoor.setText(intent.getStringExtra("floor"));
        tvFlat.setText(intent.getStringExtra("flat"));
        tvAmt.setText(intent.getStringExtra("payment due"));
        tvTotal.setText(intent.getStringExtra("total payment"));

        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmPaymentActivity.this, PaymentOnlinePayActivity.class);
                intent.putExtra("amt", tvTotal.getText().toString());
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                startActivity(intent);
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

}
