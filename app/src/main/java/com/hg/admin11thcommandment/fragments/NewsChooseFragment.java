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
import com.hg.admin11thcommandment.activities.PostNewsActivity;
import com.hg.admin11thcommandment.activities.ShowAllNewsActivity;
import com.hg.admin11thcommandment.activities.ShowUnverifiedNewsActivity;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;

public class NewsChooseFragment extends DialogFragment {
    private SharedPrefUtil util;
    private ImageView mPostNews, mShowAllNews, mVerifyNews;
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
            v = inflater.inflate(R.layout.news_choose_admin_fragment, container, false);
            mShowAllNews = v.findViewById(R.id.but_show);
            mShowAllNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(),ShowAllNewsActivity.class);
                    i.putExtra("type","News/Poll");
                    startActivity(i);
                }
            });
        }else{
            v = inflater.inflate(R.layout.news_choose_editor_fragment, container, false);
        }
        mPostNews = v.findViewById(R.id.but_add);
        mPostNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),PostNewsActivity.class);
                i.putExtra("add",true);
                startActivity(i);
            }
        });
        mVerifyNews = v.findViewById(R.id.but_verify);
        mVerifyNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ShowUnverifiedNewsActivity.class);
                i.putExtra("type","News/Poll");
                startActivity(i);
            }
        });
        return v;
    }
}
