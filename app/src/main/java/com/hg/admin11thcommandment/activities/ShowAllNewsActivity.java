package com.hg.admin11thcommandment.activities;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;

import com.hg.admin11thcommandment.database.DatabaseHandler;
import com.hg.admin11thcommandment.adapters.NewsAdapter;
import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowAllNewsActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String type = getIntent().getStringExtra("type");
        getSupportActionBar().setTitle("News/Poll List");
        adapter = new NewsAdapter(this);
        setupRecyclerView();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_show_news_menu_bar, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String title) {
                searchView.clearFocus();
                String type = getIntent().getStringExtra("type");
                setupRecyclerView(title);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String title) {
                String type = getIntent().getStringExtra("type");
                setupRecyclerView(title);
                //Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_refresh:
                setupRecyclerView();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        setupRecyclerView();
        super.onPostResume();

    }

    public void setupRecyclerView(String title){
        if(title.equals("")){
            setupRecyclerView();
            return;
        }
        //Setting up recycler view
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        Animation animation = AnimationUtils.loadAnimation(ShowAllNewsActivity.this,R.anim.splash);
        recyclerView.setAnimation(animation);
        //Setting recycler view adapter
        DatabaseHandler db = new DatabaseHandler(this);
        try {
            db.searchNewsByTitle(new VolleyCallback(){
                @Override
                public void onSuccess(String result){
                    try {
                        JSONArray jsonArray  = new JSONObject(result).getJSONArray("data");
                        adapter.setData(jsonArray);
                        //Toast.makeText(MainActivity.this, jsonArray.length(), Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        Log.d("RecyclerView(title)","SetupRecyclerView(title)");
                    }
                }
            },title);
        } catch (Exception e) {
            Log.d("MAIN ACTIVITY","SetupRecyclerView(title) " + e.toString());
        }
    }

    public void setupRecyclerView(){
        //Setting up recycler view
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Animation animation = AnimationUtils.loadAnimation(ShowAllNewsActivity.this,R.anim.splash);
        recyclerView.setAnimation(animation);
        //Setting recycler view adapter
        DatabaseHandler db = new DatabaseHandler(this);
        try {
            db.getVerifiedNews(new VolleyCallback(){
                @Override
                public void onSuccess(String result){
                    try {
                        JSONArray jsonArray  = new JSONObject(result).getJSONArray("data");
                        adapter.setData(jsonArray);
                        //Toast.makeText(MainActivity.this, String.valueOf(jsonArray.length()), Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        Log.d("SetupRecyclerView","SetupRecyclerView");
                    }
                }
            });
        } catch (Exception e) {
            Log.d("MAIN ACTIVITY","SetupRecyclerView " + e.toString());
        }
    }


}
