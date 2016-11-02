package com.drivojoy.drivohelper.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.drivojoy.drivohelper.R;
import com.drivojoy.drivohelper.common.AppCommonValues;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by sam on 10/29/2016.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navMenu) NavigationView navMenu;
    @BindView(R.id.drivoDrawer) DrawerLayout drivoDrawer;
    ImageView imgLogo;
    TextView txtDrivoName;
    TextView txtContactMail;
    @BindView(R.id.drivoToolbar) Toolbar drivoToolbar;
    @BindString(R.string.app_name) String appName;
    @BindString(R.string.drivocontact) String mailContact;
    @BindDrawable(R.drawable.drivologo) Drawable drivoLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(drivoToolbar);

        initUiValues();
        setUpDrivoDrawer();
        setUpNavigationController();
    }

    /**
     * @task initializes the values of different view elements
     */
    private void initUiValues() {
        /* header views of navigationview needs to be explicitly retrieved */
        View v = navMenu.getHeaderView(0);
        imgLogo = (ImageView) v.findViewById(R.id.imgLogo);
        txtDrivoName = (TextView) v.findViewById(R.id.txtDrivoName);
        txtContactMail = (TextView) v.findViewById(R.id.txtContactMail);
        /* set values to these views */
        txtDrivoName.setText(appName);
        txtContactMail.setText(mailContact);
        imgLogo.setImageDrawable(drivoLogo);
    }

    /**
     * @task sets up the drawerlayout for the activity, crucial to display the hamburger
     */
    private void setUpDrivoDrawer() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drivoDrawer, drivoToolbar, R.string.opendrawer, R.string.closedrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drivoDrawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     * @task handles the clicks on different nav menu items by id
     */
    private void setUpNavigationController() {
        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navBike:
                        drivoDrawer.closeDrawers();
                        giveTimeToCloseDraerAndChangeActivity(AppCommonValues.URLACTIVITY);
                        break;
                    case R.id.navLocation:
                        drivoDrawer.closeDrawers();
                        giveTimeToCloseDraerAndChangeActivity(AppCommonValues.MAPACTIVITY);
                        break;
                    case R.id.navAboutDrivojoy:
                        drivoDrawer.closeDrawers();
                        giveTimeToCloseDraerAndChangeActivity(AppCommonValues.ABOUTACTIVITY);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * @task runs a handler to take some time for closing the drawer and starts the next activity
     * @param whichActivity
     *        specifies which activity to start
     */
    private void giveTimeToCloseDraerAndChangeActivity(final int whichActivity) {
        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.removeCallbacks(this);
                startSelectedActivity(whichActivity);
            }
        }, AppCommonValues.ACTIVITY_CHANGE_DELAY);
    }

    /**
     * @task starts the next activity
     * @param whichActivity
     */
    private void startSelectedActivity(int whichActivity) {
        if(whichActivity == AppCommonValues.URLACTIVITY) {
            startActivity(new Intent(MainActivity.this, UrlActivity.class));
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        } else if(whichActivity == AppCommonValues.MAPACTIVITY){
            checkLocationPermission();
        } else {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
    }

    /**
     * @task starts map containing activity
     */
    private void startMapActivity(){
        startActivity(new Intent(MainActivity.this, MapActivity.class));
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    /**
     * @task check for location runtime permission
     * @return
     */
    public void checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            }
        } else {
            startMapActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 99: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        startMapActivity();
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}