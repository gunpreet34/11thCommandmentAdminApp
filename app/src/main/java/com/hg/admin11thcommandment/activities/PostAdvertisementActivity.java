package com.hg.admin11thcommandment.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hg.admin11thcommandment.database.DatabaseHandler;
import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.utils.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostAdvertisementActivity extends AppCompatActivity {
    private String id;
    private EditText mTitle,mDescription,mUrl,mAdvertisementUrl,mSource,mCount;
    private CheckBox mIsShown;
    private String type = "";
    private Boolean isShown = true;
    private CollapsingToolbarLayout toolbarLayout;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_advertisement);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        id = getIntent().getStringExtra("id");
        mTitle = findViewById(R.id.et_title);
        mDescription = findViewById(R.id.et_description);
        mAdvertisementUrl = findViewById(R.id.et_advertisement_url);
        mSource = findViewById(R.id.et_source);
        mUrl = findViewById(R.id.et_url);
        mCount = findViewById(R.id.et_count);
        mIsShown = findViewById(R.id.cb_is_shown);
        mIsShown.setChecked(true);

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
        final boolean add = getIntent().getBooleanExtra("add",true);

        FloatingActionButton delete = findViewById(R.id.fab_delete);

        type = getIntent().getStringExtra("type");
        if(add){
            delete.setVisibility(View.INVISIBLE);
            switch (type){
                case "Simple":
                    toolbarLayout.setTitle("Add Simple Advertisement");
                    break;
                case "Image":
                    toolbarLayout.setTitle("Add Image Advertisement");
                    break;
                case "Video":
                    toolbarLayout.setTitle("Add Video Advertisement");
                    break;
                default:
                    Toast.makeText(this, "Error fetching, please restart application", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else{
            switch (type){
                case "simple":
                    toolbarLayout.setTitle("Update Simple Advertisement");
                    break;
                case "image":
                    toolbarLayout.setTitle("Update Image Advertisement");
                    break;
                case "video":
                    toolbarLayout.setTitle("Update Video Advertisement");
                    break;
                default:
                    Toast.makeText(this, "Error fetching, please restart application", Toast.LENGTH_SHORT).show();
                    break;
            }
            final DatabaseHandler databaseHandler = new DatabaseHandler(PostAdvertisementActivity.this);
            databaseHandler.getAdvertisementByTitle(new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject jsonObject = object.getJSONObject("data");
                        type = jsonObject.getString("type");
                        mTitle.setText(jsonObject.getString("title"));
                        mDescription.setText(jsonObject.getString("description"));
                        mCount.setText(jsonObject.getString("advertisementListCount"));
                        mAdvertisementUrl.setText(jsonObject.getString("advertisementUrl"));
                        mUrl.setText(jsonObject.getString("URL"));
                        mSource.setText(jsonObject.getString("source"));

                    } catch (JSONException e) {
                        Toast.makeText(PostAdvertisementActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            },getIntent().getStringExtra("title"));
        }



        FloatingActionButton submit = findViewById(R.id.fab_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler databaseHandler = new DatabaseHandler(PostAdvertisementActivity.this);
                //Putting values to map
                Map<String,String> map = new HashMap<>();

                //Checking All Fields
                if(TextUtils.isEmpty(mTitle.getText()) || TextUtils.isEmpty(mDescription.getText()) || TextUtils.isEmpty(mSource.getText()) || TextUtils.isEmpty(mUrl.getText()) || TextUtils.isEmpty(mAdvertisementUrl.getText()) || TextUtils.isEmpty(mCount.getText())){
                    Toast.makeText(PostAdvertisementActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                    return;
                }
                mIsShown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isShown = isChecked;
                    }
                });
                map.put("advertisementListCount",mCount.getText().toString());
                map.put("title",mTitle.getText().toString());
                map.put("description",mDescription.getText().toString());
                map.put("advertisementUrl",mAdvertisementUrl.getText().toString());
                map.put("URL",mUrl.getText().toString());
                map.put("source",mSource.getText().toString());
                map.put("type",type.toLowerCase());
                map.put("shown",isShown.toString());
                if(add) {
                    databaseHandler.postAdvertisement(map);
                }else {
                    map.put("_id",getIntent().getStringExtra("id"));
                    databaseHandler.updateAdvertisement(map);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(PostAdvertisementActivity.this);
                Map<String,String> map = new HashMap<>();
                map.put("_id",id);
                databaseHandler.deleteAdvertisement(map);
                Intent intent = new Intent(PostAdvertisementActivity.this,ShowAllAdvertisementsActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton preview = findViewById(R.id.fab_preview);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostAdvertisementActivity.this,PreviewActivity.class);
                i.putExtra("title",mTitle.getText().toString());
                i.putExtra("image_url",mUrl.getText().toString());
                i.putExtra("advertisementUrl",mAdvertisementUrl.getText().toString());
                i.putExtra("description",mDescription.getText().toString());
                i.putExtra("source",mSource.getText().toString());
                startActivity(i);
            }
        });





    }
}
