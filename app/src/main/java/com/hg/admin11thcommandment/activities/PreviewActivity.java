package com.hg.admin11thcommandment.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.admin11thcommandment.R;
import com.squareup.picasso.Picasso;

public class PreviewActivity extends AppCompatActivity {

    private ImageView mImage;
    private TextView mTitle,mDescription,mSource;
    private boolean toolbarVisibility = false;
    private CardView mCardView;
    private Toolbar toolbar;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        toolbar = findViewById(R.id.toolbar_preview_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("");
        getSupportActionBar().setShowHideAnimationEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mImage = findViewById(R.id.iv_news_image);
        mTitle = findViewById(R.id.news_title);
        mDescription = findViewById(R.id.news_desc);
        mSource = findViewById(R.id.news_source);
        try{
            Picasso.get().load(getIntent().getStringExtra("image_url")).placeholder(R.drawable.news_dummy).into(mImage);
        }catch (Exception e){
            Toast.makeText(this, "No image url found", Toast.LENGTH_SHORT).show();
        }
        if(getIntent().getStringExtra("title").equals("")){
            mTitle.setText("Please add a title");
        }else{
            mTitle.setText(getIntent().getStringExtra("title"));
        }
        if(getIntent().getStringExtra("description").equals("")){
            mDescription.setText("Please add a description");
        }else{
            mDescription.setText(getIntent().getStringExtra("description"));
        }
        if(getIntent().getStringExtra("source").equals("")){
            mSource.setText("Please add a source");
        }else{
            mSource.setText(getIntent().getStringExtra("source"));
        }

        mCardView = findViewById(R.id.card_view_preview);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!toolbarVisibility){
                    toolbarVisibility = true;
                    getSupportActionBar().show();
                }else {
                    toolbarVisibility = false;
                    getSupportActionBar().hide();
                }
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
