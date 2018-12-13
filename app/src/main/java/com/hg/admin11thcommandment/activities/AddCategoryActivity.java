package com.hg.admin11thcommandment.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hg.admin11thcommandment.utils.DatabaseHandler;
import com.hg.admin11thcommandment.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class AddCategoryActivity extends AppCompatActivity {

    private Button mBtnUpload;
    static final String FTP_HOST= "ymca.dreamhosters.com";
    static final String FTP_USER = "ymcaust";
    static final String FTP_PASS  ="ramukaka";
    private int PICK_IMAGE_REQUEST = 1;
    String image = "";
    private EditText mEtCategory,mImageUrl;
    private ImageView mImageView;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            //Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT).show();
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);
                mImageView.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                image = Base64.encodeToString(b,Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Category");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mImageView = findViewById(R.id.image_view);
        mEtCategory = findViewById(R.id.et_cat_name);
        mImageUrl = findViewById(R.id.et_image_url);
       /* mBtnUpload = findViewById(R.id.btn_upload);
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*//*
                Toast.makeText(AddCategoryActivity.this, "In progress", Toast.LENGTH_SHORT).show();
            }
        });*/

        FloatingActionButton fab = findViewById(R.id.fab_submit_category);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(mEtCategory.getText()) || TextUtils.isEmpty(mImageUrl.getText())){
                    Toast.makeText(AddCategoryActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    HashMap <String,String> postData = new HashMap<>();
                    postData.put("category",mEtCategory.getText().toString());
                    postData.put("image",mImageUrl.getText().toString());
                    DatabaseHandler db = new DatabaseHandler(AddCategoryActivity.this);
                    db.addCategory(postData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}


