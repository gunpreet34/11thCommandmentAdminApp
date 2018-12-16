package com.hg.admin11thcommandment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.fragments.AdvertisementChooseFragment;
import com.hg.admin11thcommandment.fragments.CategoryChooseFragment;
import com.hg.admin11thcommandment.fragments.NewsChooseFragment;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;

public class NavigationActivity extends AppCompatActivity {
    private boolean doubleBackToExitPressedOnce = false;
    private SharedPrefUtil util;
    private String adminType;
    private TextView mTextMessage,mTextMessageTwo;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_news_poll:
                    DialogFragment newsChooseFragment= new NewsChooseFragment();
                    newsChooseFragment.show(fragmentManager,"News Choose Fragment");
                    return true;
                case R.id.navigation_add:
                    DialogFragment advertisementChooseFragment= new AdvertisementChooseFragment();
                    advertisementChooseFragment.show(fragmentManager,"Advertisement Choose Fragment");
                    return true;
                case R.id.navigation_category:
                    //startActivity(new Intent(NavigationActivity.this,AddCategoryActivity.class));
                    DialogFragment categoryChooseFragment= new CategoryChooseFragment();
                    categoryChooseFragment.show(fragmentManager,"Category Choose Fragment");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new SharedPrefUtil(getApplicationContext());
        setContentView(R.layout.activity_navigation);
        getSupportActionBar().setTitle("Admin Panel");
        mTextMessage = findViewById(R.id.message);
        mTextMessageTwo = findViewById(R.id.message_two);
        if(util.getAccess() == 1){
            adminType = "Admin";
        }else {
            adminType = "Editor";
        }
        mTextMessage.setText("Howdy " + adminType + ",");
        mTextMessageTwo.setText(" We got some pending tasks done.\n\n Shall we?\n\n Good Day :)");
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_choose_menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ui:
                util.saveUi(1 - util.getUi());
                startActivity(new Intent(NavigationActivity.this,SplashActivity.class));
                break;
            case R.id.menu_logout:
                util.saveToken("");
                util.saveAccess(0);
                util.logout();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(getWindow().getDecorView().getRootView(),"Press back once again to exit application",Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
