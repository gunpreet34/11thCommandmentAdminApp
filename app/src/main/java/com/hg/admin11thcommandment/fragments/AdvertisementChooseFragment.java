package com.hg.admin11thcommandment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.activities.PostAdvertisementActivity;
import com.hg.admin11thcommandment.activities.ShowAllAdvertisementsActivity;
import com.hg.admin11thcommandment.activities.ShowAllNewsActivity;
import com.hg.admin11thcommandment.activities.ShowUnverifiedAdvertisementActivity;
import com.hg.admin11thcommandment.activities.ShowUnverifiedNewsActivity;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;

public class AdvertisementChooseFragment extends DialogFragment {
    private SharedPrefUtil util;
    private ImageView mSimple,mImage,mVideo,mShow,mVerify;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        util = new SharedPrefUtil(getContext());
        View v;
        if(util.getAccess() == 1){
            v = inflater.inflate(R.layout.advertisement_choose_admin_fragment, container, false);
            mShow = v.findViewById(R.id.but_show_add);
            mShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(),ShowAllAdvertisementsActivity.class);
                    startActivity(i);
                }
            });
        }else{
            v = inflater.inflate(R.layout.advertisement_choose_editor_fragment, container, false);
        }

        mSimple = v.findViewById(R.id.but_simple_add);
        mSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),PostAdvertisementActivity.class);
                i.putExtra("type","Simple");
                i.putExtra("add",true);
                startActivity(i);
            }
        });

        mImage = v.findViewById(R.id.but_image_add);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),PostAdvertisementActivity.class);
                i.putExtra("type","Image");
                i.putExtra("add",true);
                startActivity(i);
            }
        });

        mVideo = v.findViewById(R.id.but_video_add);
        mVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),PostAdvertisementActivity.class);
                i.putExtra("type","Video");
                i.putExtra("add",true);
                startActivity(i);
            }
        });

        mVerify = v.findViewById(R.id.but_verify_add);
        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ShowUnverifiedAdvertisementActivity.class);
                i.putExtra("type","Advertisement");
                startActivity(i);
            }
        });
        return v;
    }
}
