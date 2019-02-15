package com.hg.admin11thcommandment.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;

public class ChooseActivity extends AppCompatActivity {
    private SharedPrefUtil util;
    private boolean doubleBackToExitPressedOnce = false;
    private ImageView mNews,mCategory,mAdvertisement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new SharedPrefUtil(ChooseActivity.this);
        setContentView(R.layout.activity_choose);
        getSupportActionBar().setTitle("Admin app");

        mNews = findViewById(R.id.but_news);
        mAdvertisement = findViewById(R.id.but_advertisement);
        mCategory = findViewById(R.id.but_category);

        mNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseActivity.this,NewsActivity.class));
            }
        });
        mAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseActivity.this,AdvertisementActivity.class));
            }
        });
        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseActivity.this,CategoryActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_choose_menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        util = new SharedPrefUtil(ChooseActivity.this);
        switch (item.getItemId()) {
            case R.id.menu_logout:
                util.saveToken("");
                util.saveAccess(0);
                util.logout();
                Intent intent = new Intent(ChooseActivity.this,LoginActivity.class);
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
