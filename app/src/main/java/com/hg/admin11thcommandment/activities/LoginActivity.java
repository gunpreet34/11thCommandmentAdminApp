package com.hg.admin11thcommandment.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hg.admin11thcommandment.utils.DatabaseHandler;
import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;
import com.hg.admin11thcommandment.utils.VolleyCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmailInp;
    private EditText mPasswordInp;
    private Button mLogInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        mEmailInp=findViewById(R.id.email_input);
        mPasswordInp=findViewById(R.id.password_input);
        mLogInBtn=findViewById(R.id.log_in_btn);
        mLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmailInp.getText().toString();
                String password=mPasswordInp.getText().toString();
                if(isValidEmail(email)){
                    Map<String,String> credentials = new HashMap<>();
                    credentials.put("email",email);
                    credentials.put("password",password);
                    DatabaseHandler databaseHandler = new DatabaseHandler(LoginActivity.this);
                    databaseHandler.adminLogin(credentials,new VolleyCallback(){
                        @Override
                        public void onSuccess(String result){
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if(jsonObject.getString("success").equals("1")){
                                    String token = jsonObject.getString("data");
                                    int access = jsonObject.getInt("access");
                                    SharedPrefUtil util = new SharedPrefUtil(LoginActivity.this);
                                    util.saveToken(token);
                                    util.saveAccess(access);
                                    Intent intent = new Intent(LoginActivity.this,ChooseActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    mEmailInp.setError("Invalid email address");
                }
            }
        });

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
