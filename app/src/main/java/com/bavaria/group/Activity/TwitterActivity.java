package com.bavaria.group.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.bavaria.group.R;

/**
 * Created by archirayan1 on 3/4/2016.
 */
public class TwitterActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_twitter_blog);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
    }

}
