package com.hg.admin11thcommandment.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class SharedPrefUtil implements SharedConstUtil{
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPrefUtil(Context context) {
        mSharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
    }

    public void saveToken(String token){
        mEditor.putString(TOKEN,token);
        mEditor.apply();
    }
    public void saveAccess(int access){
        mEditor.putInt(ACCESS,access);
        mEditor.apply();
    }
    public void saveUi(int ui) {
        mEditor.putInt(UI,ui);
        mEditor.apply();
    }

    public String getToken(){
        return mSharedPreferences.getString(TOKEN,null);
    }
    public int getAccess(){
        return mSharedPreferences.getInt(ACCESS,0);
    }
    public int getUi() {
        return mSharedPreferences.getInt(UI,0);
    }

    public void logout(){
        mEditor.clear().apply();
    }

 }
