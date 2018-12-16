package com.hg.admin11thcommandment.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;

public class NewsActivity extends AppCompatActivity {
    private SharedPrefUtil util;
    private ImageView mNew,mShow,mVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new SharedPrefUtil(NewsActivity.this);
        if(util.getAccess() == 1){
            setContentView(R.layout.activity_news_admin);
            mShow = findViewById(R.id.but_show);
            mShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(NewsActivity.this,ShowAllNewsActivity.class);
                    i.putExtra("type","News/Poll");
                    startActivity(i);
                }
            });
        }else{
            setContentView(R.layout.activity_news_editor);
        }


        getSupportActionBar().setTitle("News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mNew = findViewById(R.id.but_add);
        mNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsActivity.this,PostNewsActivity.class);
                i.putExtra("add",true);
                startActivity(i);
            }
        });

        mVerify = findViewById(R.id.but_verify);
        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsActivity.this,ShowUnverifiedNewsActivity.class);
                i.putExtra("type","News/Poll");
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
