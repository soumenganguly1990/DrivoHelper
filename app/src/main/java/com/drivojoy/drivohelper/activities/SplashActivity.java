package com.drivojoy.drivohelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.drivojoy.drivohelper.R;
import com.drivojoy.drivohelper.common.AppCommonValues;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sam on 10/29/2016.
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.imgDrivoLogo) ImageView imgDrivoLogo;
    @BindView(R.id.txtBanner) TextView txtBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        startAndPlayAnimation(imgDrivoLogo, R.anim.zoomin);
    }

    /**
     * @task plays selected animation on the selected view component
     * @param view
     *        the view on which the animation is to be played
     * @param animCode
     *        the animation effect that needs to be played
     */
    private void startAndPlayAnimation(final View view, int animCode) {
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, animCode);
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (view == imgDrivoLogo) {
                    startAndPlayAnimation(txtBanner, R.anim.fadein);
                } else if (view == txtBanner) {
                    goToMainActivityAfterALittleWait();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * @task shows activity for some time then changes activity to mainactivity
     */
    private void goToMainActivityAfterALittleWait() {
        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /* remove callback and start next activity */
                mHandler.removeCallbacks(this);
                startMainActivity();
            }
        }, AppCommonValues.SPLASH_DELAY);
    }

    /**
     * @task starts the mainactivity activity
     */
    private void startMainActivity(){
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        SplashActivity.this.finish();
    }
}
