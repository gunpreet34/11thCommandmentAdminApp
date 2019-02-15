package com.hg.admin11thcommandment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.database.DatabaseHandler;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;
import com.hg.admin11thcommandment.utils.VolleyCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mEtEmail;
    private Button mBtSubmit;
    private TextView mTvGoBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();

        mEtEmail = findViewById(R.id.et_email_address);
        mBtSubmit = findViewById(R.id.btn_submit);
        mBtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> credentials = new HashMap<>();
                credentials.put("user_id",mEtEmail.getText().toString());
                DatabaseHandler databaseHandler = new DatabaseHandler(ForgotPasswordActivity.this);
                databaseHandler.resetPassword(credentials);
            }
        });
        mTvGoBack = findViewById(R.id.tv_go_back);
        mTvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
