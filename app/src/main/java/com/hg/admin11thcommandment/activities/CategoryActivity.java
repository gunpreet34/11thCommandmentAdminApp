package com.hg.admin11thcommandment.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;

public class CategoryActivity extends AppCompatActivity {
    private SharedPrefUtil util;
    private ImageView mNew,mShow,mVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_admin);
        util = new SharedPrefUtil(CategoryActivity.this);
        if(util.getAccess() == 1){
            setContentView(R.layout.activity_category_admin);
            mShow = findViewById(R.id.but_show_cat);
            mShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(CategoryActivity.this,ShowAllCategoriesActivity.class);
                    startActivity(i);
                }
            });
        }else{
            setContentView(R.layout.activity_category_editor);
        }


        getSupportActionBar().setTitle("News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mNew = findViewById(R.id.but_add_cat);
        mNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoryActivity.this,AddCategoryActivity.class);
                i.putExtra("add",true);
                startActivity(i);
            }
        });

        mVerify = findViewById(R.id.but_verify_cat);
        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoryActivity.this,ShowUnverifiedCategoriesActivity.class);
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
