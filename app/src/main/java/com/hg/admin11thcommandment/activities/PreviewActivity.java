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
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.squareup.picasso.Picasso;

public class PreviewActivity extends AppCompatActivity {

    private TextView mDescription;
    private TextView mSource;
    private boolean toolbarVisibility = false;
    private CardView mCardView;
    private Toolbar toolbar;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView image;
        if(getIntent().getStringExtra("type").trim().equals("image")){
            //Image advertisement
            setContentView(R.layout.activity_preview_image);
            image = findViewById(R.id.image_view_preview);
            try{
                Picasso.get().load(getIntent().getStringExtra("image_url")).placeholder(R.drawable.ad_dummy).into(image);
            }catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }else if(getIntent().getStringExtra("type").equals("video")) {
            //Video Advertisement
            setContentView(R.layout.activity_preview_video);
            YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
            youTubePlayerView.initialize(
                    new YouTubePlayerInitListener() {

                        @Override
                        public void onInitSuccess(
                                final YouTubePlayer initializedYouTubePlayer) {

                            initializedYouTubePlayer.addListener(
                                    new AbstractYouTubePlayerListener() {
                                        @Override
                                        public void onReady() {
                                            initializedYouTubePlayer.loadVideo(getIntent().getStringExtra("image_url"), 0);
                                        }
                                    });
                        }
                    }, true);
        }else {
            //News/Poll/Simple Advertisement
            setContentView(R.layout.activity_preview_simple);
            image = findViewById(R.id.iv_news_image);
            TextView title = findViewById(R.id.news_title);
            mDescription = findViewById(R.id.news_desc);
            mSource = findViewById(R.id.news_source);
            try{
                Picasso.get().load(getIntent().getStringExtra("image_url")).placeholder(R.drawable.news_dummy).into(image);
            }catch (Exception e){
                Toast.makeText(this, "No image url found", Toast.LENGTH_SHORT).show();
            }
            if(getIntent().getStringExtra("title").equals("")){
                title.setText("Please add a title");
            }else{
                title.setText(getIntent().getStringExtra("title"));
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

            toolbar = findViewById(R.id.toolbar_preview_activity);
            setSupportActionBar(toolbar);
            getSupportActionBar().hide();
            getSupportActionBar().setTitle("");
            getSupportActionBar().setShowHideAnimationEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
