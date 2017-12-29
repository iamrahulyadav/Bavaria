package com.bavaria.group.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bavaria.group.R;
import com.bavaria.group.Util.BaseAppCompactActivity;
import com.bavaria.group.Util.TouchImageView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import static com.bavaria.group.R.id.ivTouchImage;

/**
 * Created by archirayan1 on 6/2/2016.
 */
public class MultiTouchActivity extends BaseAppCompactActivity {
    private String image;
    private TouchImageView ivTouch;
    private LinearLayout ll;
    private ImageView ivback, ivshare;
    private String str_ImageShow;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();

    public static Bitmap captureView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
//        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_multitouch);
        ivback = (ImageView) findViewById(R.id.ivBack_MultitouchActivity);
        ivshare = (ImageView) findViewById(R.id.ivShare_MultitouchActivity);
        ll = (LinearLayout) findViewById(R.id.linearlyout);
        ivTouch = (TouchImageView) findViewById(ivTouchImage);
        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            image = extra.getString("img");
            Log.e("%%%%%%%%", "" + image);
        }



        Bundle extras = getIntent().getExtras();
        str_ImageShow= (extras.getString("str_ImageShow"));

        if(Picasso.with(MultiTouchActivity.this).load(str_ImageShow)!=null)
        {
            Picasso.with(MultiTouchActivity.this).load(str_ImageShow).into(ivTouch);
        }

        Picasso.with(MultiTouchActivity.this).load(image).placeholder(R.drawable.default_img).into(ivTouch);

     //   Glide.with(MultiTouchActivity.this).load(image).placeholder(R.drawable.default_img).into(ivTouch);
        /*ivTouch.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                RectF rect = ivTouch.getZoomedRect();
                float currentZoom = ivTouch.getCurrentZoom();
                boolean isZoomed = ivTouch.isZoomed();
                //Log.e("sfsdfdsf", ""+currentZoom+","+isZoomed);
                //Do whater ever stuff u want
            }
        });*/


        ivback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MultiTouchActivity.this, "CLICK", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        ivshare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = captureView(ll);
                String pathofBmp = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, null, null);
                Uri bmpUri = Uri.parse(pathofBmp);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/jpeg");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));


            }
        });

//        ivTouch.setMaxZoom(4f);

    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        scaleGestureDetector.onTouchEvent(ev);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            matrix.setScale(scaleFactor, scaleFactor);
            ivTouch.setImageMatrix(matrix);
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

}
