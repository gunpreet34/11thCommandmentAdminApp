package com.hg.admin11thcommandment.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.admin11thcommandment.database.DatabaseHandler;
import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.utils.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.hg.admin11thcommandment.constants.ServerConstants.SERVER_HOST;

public class AddCategoryActivity extends AppCompatActivity {

    private Button mBtnUpload;
    static final String FTP_HOST= "ymca.dreamhosters.com";
    static final String FTP_USER = "ymcaust";
    static final String FTP_PASS  ="ramukaka";
    private int PICK_IMAGE_REQUEST = 1;
    String image = "";
    private EditText mEtCategory,mImageUrl;
    private TextView mUploadImage;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FloatingActionButton fabDelete = findViewById(R.id.fab_delete_category);
        fabDelete.setVisibility(View.INVISIBLE);
        mEtCategory = findViewById(R.id.et_cat_name);
        mImageUrl = findViewById(R.id.et_image_url);
        mUploadImage = findViewById(R.id.tv_upload_image);
        mUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = SERVER_HOST + "/uploadPicture";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(AddCategoryActivity.this, Uri.parse(url));
            }
        });


        final boolean add = getIntent().getBooleanExtra("add",true);
        if(add){
            getSupportActionBar().setTitle("Add New Category");
        }else{
            getSupportActionBar().setTitle("Update Category");
            fabDelete.setVisibility(View.VISIBLE);
            final DatabaseHandler databaseHandler = new DatabaseHandler(AddCategoryActivity.this);
            databaseHandler.getCategoryByTitle(new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject jsonObject = object.getJSONArray("data").getJSONObject(0);
                        mEtCategory.setText(jsonObject.getString("category"));
                        mImageUrl.setText(jsonObject.getString("imageURL"));
                    } catch (JSONException e) {
                        Toast.makeText(AddCategoryActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            },getIntent().getStringExtra("category"));
        }


        FloatingActionButton fabSubmit = findViewById(R.id.fab_submit_category);
        fabSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(mEtCategory.getText()) || TextUtils.isEmpty(mImageUrl.getText())){
                    Toast.makeText(AddCategoryActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    HashMap <String,String> postData = new HashMap<>();
                    postData.put("category",mEtCategory.getText().toString());
                    postData.put("imageURL",mImageUrl.getText().toString());
                    DatabaseHandler db = new DatabaseHandler(AddCategoryActivity.this);
                    if(add)
                        db.addCategory(postData);
                    else
                        db.updateCategory(postData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(AddCategoryActivity.this);
                Map<String,String> map = new HashMap<>();
                map.put("category",mEtCategory.getText().toString());
                databaseHandler.deleteCategory(map);
                Intent intent = new Intent(AddCategoryActivity.this,ShowAllCategoriesActivity.class);
                startActivity(intent);
            }
        });

    }
}


