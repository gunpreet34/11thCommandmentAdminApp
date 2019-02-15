package com.hg.admin11thcommandment.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.activities.PostNewsActivity;
import com.hg.admin11thcommandment.activities.ShowAllNewsActivity;
import com.hg.admin11thcommandment.activities.ShowUnverifiedNewsActivity;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;

import static com.hg.admin11thcommandment.constants.ServerConstants.SERVER_HOST;

public class NewsChooseFragment extends DialogFragment {
    private SharedPrefUtil util;
    private RelativeLayout mPostNews, mPostMultipleNews, mShowAllNews, mVerifyNews;
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

        mPostMultipleNews = v.findViewById(R.id.but_add_multiple);
        mPostMultipleNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = SERVER_HOST + "/uploadMultipleNews";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
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
