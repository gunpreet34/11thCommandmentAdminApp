package com.hg.admin11thcommandment.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.adapters.AdvertisementAdapter;
import com.hg.admin11thcommandment.adapters.CategoryAdapter;
import com.hg.admin11thcommandment.database.DatabaseHandler;
import com.hg.admin11thcommandment.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowAllCategoriesActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Categories");
        adapter = new CategoryAdapter(this);
        setupRecyclerViewForCategories();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        setupRecyclerViewForCategories();
        super.onPostResume();

    }


    private void setupRecyclerViewForCategories() {
        //Setting up recycler view
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        Animation animation = AnimationUtils.loadAnimation(ShowAllCategoriesActivity.this,R.anim.splash);
        recyclerView.setAnimation(animation);
        //Setting recycler view adapter
        DatabaseHandler db = new DatabaseHandler(this);
        try {
            db.getVerifiedCategories(new VolleyCallback(){
                @Override
                public void onSuccess(String result){
                    try {
                        JSONArray jsonArray  = new JSONObject(result).getJSONArray("data");
                        adapter.setData(jsonArray);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        Log.d("SetupRecyclerView","SetupRecyclerView");
                    }
                }
            });
        } catch (Exception e) {
            Log.d("SHOW ALL CAT ACTIVITY","SetupRecyclerView " + e.toString());
        }
    }

}
