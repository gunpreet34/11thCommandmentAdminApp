package com.hg.admin11thcommandment.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;

public class AdvertisementActivity extends AppCompatActivity {

    private SharedPrefUtil util;
    private ImageView mSimple,mImage,mVideo,mShow,mVerify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Advertisements");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        util = new SharedPrefUtil(AdvertisementActivity.this);
        if(util.getAccess() == 1){
            setContentView(R.layout.activity_advertisement_admin);
            mVerify = findViewById(R.id.but_verify_add);
            mVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(AdvertisementActivity.this,ShowUnverifiedNewsActivity.class);
                    i.putExtra("type","Advertisement");
                    startActivity(i);
                }
            });
        }else{
            setContentView(R.layout.activity_advertisement_editor);
        }

        mSimple = findViewById(R.id.but_simple_add);
        mImage = findViewById(R.id.but_image_add);
        mVideo = findViewById(R.id.but_video_add);
        mShow = findViewById(R.id.but_show_add);


        mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdvertisementActivity.this,ShowAllNewsActivity.class);
                i.putExtra("type","Advertisement");
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
