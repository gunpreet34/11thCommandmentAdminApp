package com.hg.admin11thcommandment.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;

public class SplashActivity extends AppCompatActivity {
    private SharedPrefUtil mSharedPrefUtil;
    private ImageView mImageView;
    private TextView mTextView;
    private Class targetActivity ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        mImageView = findViewById(R.id.iv_logged_in);
        mImageView.setVisibility(View.INVISIBLE);
        mTextView = findViewById(R.id.tv_logged_in);
        mTextView.setVisibility(View.INVISIBLE);

        mSharedPrefUtil=new SharedPrefUtil(SplashActivity.this);
        Handler h=new Handler();
        if (mSharedPrefUtil.getToken()==null) {
            targetActivity = LoginActivity.class;
        }else {
            if(mSharedPrefUtil.getUi() == 0){
                targetActivity = NavigationActivity.class;
            }else {
                targetActivity = ChooseActivity.class;
            }
            mImageView.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
        }


        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, targetActivity);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        },800);

    }
}
