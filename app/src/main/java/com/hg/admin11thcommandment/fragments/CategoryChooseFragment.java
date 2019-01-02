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
import com.hg.admin11thcommandment.activities.AddCategoryActivity;
import com.hg.admin11thcommandment.activities.PostNewsActivity;
import com.hg.admin11thcommandment.activities.ShowAllAdvertisementsActivity;
import com.hg.admin11thcommandment.activities.ShowAllNewsActivity;
import com.hg.admin11thcommandment.activities.ShowUnverifiedNewsActivity;
import com.hg.admin11thcommandment.utils.SharedPrefUtil;

public class CategoryChooseFragment extends DialogFragment {
    private SharedPrefUtil util;
    private ImageView mAddCategory, mShowAllCategories, mVerifyCategory;

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
            v = inflater.inflate(R.layout.category_choose_admin_fragment, container, false);
            mShowAllCategories = v.findViewById(R.id.but_show_cat);
            mShowAllCategories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(),ShowAllAdvertisementsActivity.class);
                    startActivity(i);
                }
            });
        }else{
            v = inflater.inflate(R.layout.category_choose_editor_fragment, container, false);
        }
        mAddCategory = v.findViewById(R.id.but_add_cat);
        mAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),AddCategoryActivity.class);
                i.putExtra("add",true);
                startActivity(i);
            }
        });
        mVerifyCategory = v.findViewById(R.id.but_verify_cat);
        mVerifyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ShowAllAdvertisementsActivity.class);
                //i.putExtra("type","News/Poll");
                startActivity(i);
            }
        });
        return v;
    }
}
