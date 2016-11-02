package com.drivojoy.drivohelper.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.drivojoy.drivohelper.R;

/**
 * Created by sam on 11/2/2016.
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
