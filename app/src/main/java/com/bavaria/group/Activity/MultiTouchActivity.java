package com.bavaria.group.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.squareup.picasso.Picasso;

import static com.bavaria.group.R.id.ivTouchImage;

/**
 * Created by archirayan1 on 6/2/2016.
 */
public class MultiTouchActivity extends BaseAppCompactActivity {
    private TouchImageView ivTouch;
    private LinearLayout ll;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;

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
        ImageView ivback = findViewById(R.id.ivBack_MultitouchActivity);
        ImageView ivshare = findViewById(R.id.ivShare_MultitouchActivity);
        ll = findViewById(R.id.linearlyout);
        ivTouch = findViewById(ivTouchImage);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        String image = getIntent().getStringExtra("image");
        Log.e("%%%%%%%%", "" + image);

//        String str_ImageShow = getIntent().getStringExtra("str_ImageShow");

        //  Picasso.with(MultiTouchActivity.this).load(image).into(ivTouch);

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


        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MultiTouchActivity.this, "CLICK", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        ivshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = checkPermission();
                if (result) {
                    Bitmap bitmap = captureView(ll);
                    String pathofBmp = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, null, null);
                    Uri bmpUri = Uri.parse(pathofBmp);
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("image/jpeg");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                }
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MultiTouchActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MultiTouchActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MultiTouchActivity.this);
//                    alertBuilder.setCancelable(true);
//                    alertBuilder.setTitle("Permission necessary");
//                    alertBuilder.setMessage("Write calendar permission is necessary to write event!!!");
//                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//                        public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MultiTouchActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
//                        }
//                    });
//                    AlertDialog alert = alertBuilder.create();
//                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(MultiTouchActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_CALENDAR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Bitmap bitmap = captureView(ll);
                    String pathofBmp = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, null, null);
                    Uri bmpUri = Uri.parse(pathofBmp);
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("image/jpeg");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                } else {
                    //code for deny
                }
                break;
        }
    }

}
