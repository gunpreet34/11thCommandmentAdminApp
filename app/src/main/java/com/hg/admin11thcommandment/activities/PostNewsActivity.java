package com.hg.admin11thcommandment.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hg.admin11thcommandment.database.DatabaseHandler;
import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.utils.VolleyCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostNewsActivity extends AppCompatActivity {
    private String id;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> tempList = new ArrayList<>();
    Map<String,Boolean> mMap = new HashMap<>();
    private GridLayout mGridLayout;
    private CheckBox mPollCheckBox;
    private EditText mTitle,mDescription,mUrl,mSource,mImageUrl,mPollQuestion,mPollOptionOne,mPollOptionTwo;
    private CardView mPollQue,mPollOptOne,mPollOptTwo;
    private String type = "";
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_news);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        id = getIntent().getStringExtra("id");
        mTitle = findViewById(R.id.et_title);
        mDescription = findViewById(R.id.et_description);
        mUrl = findViewById(R.id.et_url);
        mSource = findViewById(R.id.et_source);
        mImageUrl = findViewById(R.id.et_image_url);
        mPollCheckBox = findViewById(R.id.cb_poll);
        mPollQuestion = findViewById(R.id.et_poll_question);
        mPollOptionOne = findViewById(R.id.et_option_one);
        mPollOptionTwo = findViewById(R.id.et_option_two);
        mGridLayout=findViewById(R.id.category_grid_layout);
        mPollQue = findViewById(R.id.card_poll_question);
        mPollQue.setVisibility(View.INVISIBLE);
        mPollOptOne = findViewById(R.id.card_option_one);
        mPollOptOne.setVisibility(View.INVISIBLE);
        mPollOptTwo = findViewById(R.id.card_option_two);
        mPollOptTwo.setVisibility(View.INVISIBLE);

        mPollCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mPollQue.setVisibility(View.VISIBLE);
                    mPollOptOne.setVisibility(View.VISIBLE);
                    mPollOptTwo.setVisibility(View.VISIBLE);
                    type = "Poll";

                }else{
                    mPollQue.setVisibility(View.INVISIBLE);
                    mPollOptOne.setVisibility(View.INVISIBLE);
                    mPollOptTwo.setVisibility(View.INVISIBLE);
                    mPollQuestion.setText("");
                    mPollOptionOne.setText("");
                    mPollOptionTwo.setText("");
                    type = "News";
                }
            }
        });

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
        final boolean add = getIntent().getBooleanExtra("add",true);

        FloatingActionButton delete = findViewById(R.id.fab_delete);

        if(add){
            toolbarLayout.setTitle("Add news");
            delete.setVisibility(View.INVISIBLE);
            try {
                DatabaseHandler db = new DatabaseHandler(PostNewsActivity.this);
                db.getCategories(new VolleyCallback(){
                    @Override
                    public void onSuccess(String result){
                        try {
                            JSONArray jsonArray  = new JSONObject(result).getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                if(!mMap.containsKey(jsonArray.getJSONObject(i).getString("category"))) {
                                    mMap.put(jsonArray.getJSONObject(i).getString("category"),false);
                                }
                            }
                            loadCategories(jsonArray);
                            //Toast.makeText(PostNewsActivity.this, categories.toString(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(PostNewsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                Toast.makeText(PostNewsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }else{
            toolbarLayout.setTitle("Update news");
            getIntent().getStringExtra("position");
            final DatabaseHandler databaseHandler = new DatabaseHandler(PostNewsActivity.this);
            databaseHandler.getNewsByTitle(new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject jsonObject = object.getJSONObject("data");
                        try {
                            if(jsonObject.getString("type").equals("Poll")){
                                mPollCheckBox.setChecked(true);
                                mPollQue.setVisibility(View.VISIBLE);
                                mPollOptOne.setVisibility(View.VISIBLE);
                                mPollOptTwo.setVisibility(View.VISIBLE);
                                mPollQuestion.setText(jsonObject.getString("question"));
                                mPollOptionOne.setText(jsonObject.getString("optionOne"));
                                mPollOptionTwo.setText(jsonObject.getString("optionTwo"));
                                Toast.makeText(PostNewsActivity.this, "News opened is a poll", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(PostNewsActivity.this, "News opened is not a poll", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(PostNewsActivity.this, "News opened is not a poll", Toast.LENGTH_SHORT).show();
                        }
                        mTitle.setText(jsonObject.getString("title"));
                        mDescription.setText(jsonObject.getString("description"));
                        mUrl.setText(jsonObject.getString("url"));
                        mImageUrl.setText(jsonObject.getString("imageURL"));
                        mSource.setText(jsonObject.getString("source"));
                        //For category
                        tempList = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("tags");
                        for(int i=0;i<jsonArray.length();i++){
                            tempList.add(jsonArray.getString(i));
                        }
                        try {
                            DatabaseHandler db = new DatabaseHandler(PostNewsActivity.this);
                            db.getCategories(new VolleyCallback(){
                                @Override
                                public void onSuccess(String result){
                                    try {
                                        JSONArray jsonArray  = new JSONObject(result).getJSONArray("data");
                                        for(int i=0;i<jsonArray.length();i++){
                                            if(!mMap.containsKey(jsonArray.getJSONObject(i).getString("category"))) {
                                                mMap.put(jsonArray.getJSONObject(i).getString("category"),false);
                                            }
                                        }
                                        loadCategories(jsonArray);
                                        //Toast.makeText(PostNewsActivity.this, categories.toString(), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        Toast.makeText(PostNewsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(PostNewsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(PostNewsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            },getIntent().getStringExtra("title"));
        }



        FloatingActionButton submit = findViewById(R.id.fab_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler databaseHandler = new DatabaseHandler(PostNewsActivity.this);
                //Putting values to map
                Map<String,String> map = new HashMap<>();

                //Checking All Fields
                if(TextUtils.isEmpty(mTitle.getText()) || TextUtils.isEmpty(mDescription.getText()) || TextUtils.isEmpty(mSource.getText()) || TextUtils.isEmpty(mUrl.getText()) || TextUtils.isEmpty(mImageUrl.getText())){
                    Toast.makeText(PostNewsActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                    return;
                }else if(type.equals("Poll")){
                    if(TextUtils.isEmpty(mPollQuestion.getText()) || TextUtils.isEmpty(mPollOptionOne.getText()) || TextUtils.isEmpty(mPollOptionTwo.getText())){
                        Toast.makeText(PostNewsActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                map.put("title",mTitle.getText().toString());
                map.put("description",mDescription.getText().toString());
                map.put("url",mUrl.getText().toString());
                map.put("imageURL",mImageUrl.getText().toString());
                map.put("source",mSource.getText().toString());
                map.put("type",type);
                if(type.equals("Poll")){
                    map.put("question",mPollQuestion.getText().toString());
                    map.put("optionOne",mPollOptionOne.getText().toString());
                    map.put("optionTwo",mPollOptionTwo.getText().toString());
                }
                try {
                    String cat = list.get(0);
                    for(int i=1;i<list.size();i++){
                        cat = cat + ", " + list.get(i);
                    }
                    map.put("category",cat);
                }catch (Exception e){
                    Toast.makeText(PostNewsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                if(add) {
                    databaseHandler.postNews(map);
                }else {
                    map.put("_id",getIntent().getStringExtra("id"));
                    databaseHandler.updateNews(map);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(PostNewsActivity.this);
                Map<String,String> map = new HashMap<>();
                map.put("_id",id);
                databaseHandler.deleteNews(map);
                Intent intent = new Intent(PostNewsActivity.this,ShowAllNewsActivity.class);
                intent.putExtra("type","news");
                startActivity(intent);
            }
        });

        FloatingActionButton preview = findViewById(R.id.fab_preview);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostNewsActivity.this,PreviewActivity.class);
                i.putExtra("title",mTitle.getText().toString());
                i.putExtra("image_url",mImageUrl.getText().toString());
                i.putExtra("description",mDescription.getText().toString());
                i.putExtra("source",mSource.getText().toString());
                i.putExtra("type","news");
                startActivity(i);
            }
        });





    }


    private void loadCategories(JSONArray jsonArray) {
        try{
            final boolean add = getIntent().getBooleanExtra("add",true);
            int items=jsonArray.length();
            int columnCount=3;
            int rows;
            if(items%columnCount == 0){
                rows=items/columnCount;
            }else{
                rows=items/columnCount+1;
            }
            mGridLayout.removeAllViews();
            mGridLayout.setColumnCount(1);
            mGridLayout.setRowCount(rows);
            final boolean f=false;
            LayoutInflater inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for(int i=0;i<rows;i++) {
                View view1 = inflater.inflate(R.layout.category_row,null);
                for(int j=0;j<columnCount;j++){
                    if(i*3+j+1>items){
                        View view2=view1.findViewById(R.id.category_card1);;
                        if(j==2){
                            view2=view1.findViewById(R.id.category_card2);
                        }
                        view2.setEnabled(false);
                        view2.setVisibility(View.INVISIBLE);
                    }
                }
                final CardView cat0=view1.findViewById(R.id.category_card0);
                final CardView cat1=view1.findViewById(R.id.category_card1);
                final CardView cat2=view1.findViewById(R.id.category_card2);
                final TextView textView0=cat0.findViewById(R.id.tv_categories0);
                final TextView textView1=cat1.findViewById(R.id.tv_categories1);
                final TextView textView2=cat2.findViewById(R.id.tv_categories2);

                //Set texts

                if (items==0){
                    textView0.setText("No Category");
                }
                if(i*3+1<=items){
                    String category=jsonArray.getJSONObject(i*3).getString("category");
                    textView0.setText(category);
                    if(!add){

                        if (isSelected(category)){
                            mMap.put(category,true);
                            list.add(category);
                            cat0.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            textView0.setTextColor(getResources().getColor(R.color.white));
                        }
                    }else {
                        //Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
                    }
                }
                if(i*3+2<=items){
                    String category=jsonArray.getJSONObject(i*3+1).getString("category");
                    textView1.setText(category);
                    if(!add){
                        if (isSelected(category)){
                            mMap.put(category,true);
                            list.add(category);
                            cat1.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            textView1.setTextColor(getResources().getColor(R.color.white));
                        }
                    }
                }
                if(i*3+3<=items){
                    String category=jsonArray.getJSONObject(i*3+2).getString("category");
                    textView2.setText(category);
                    if(!add){
                        if (isSelected(category)){
                            mMap.put(category,true);
                            list.add(category);
                            cat2.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            textView2.setTextColor(getResources().getColor(R.color.white));
                        }
                    }
                }

                //Listeners on cards
                cat0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(mMap.get(textView0.getText().toString())) {
                            cat0.setCardBackgroundColor(getResources().getColor(R.color.cardColor));
                            textView0.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            mMap.put(textView0.getText().toString(),false);
                            list.remove(textView0.getText().toString());
                        }else{
                            cat0.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            textView0.setTextColor(getResources().getColor(R.color.white));
                            mMap.put(textView0.getText().toString(),true);
                            list.add(textView0.getText().toString());
                        }

                    }
                });
                cat1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mMap.get(textView1.getText().toString())) {
                            cat1.setCardBackgroundColor(getResources().getColor(R.color.cardColor));
                            textView1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            mMap.put(textView1.getText().toString(),false);
                            list.remove(textView1.getText().toString());
                        }else{
                            cat1.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            textView1.setTextColor(getResources().getColor(R.color.white));
                            mMap.put(textView1.getText().toString(),true);
                            list.add(textView1.getText().toString());
                        }

                    }
                });
                cat2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mMap.get(textView2.getText().toString())) {
                            cat2.setCardBackgroundColor(getResources().getColor(R.color.cardColor));
                            textView2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            mMap.put(textView2.getText().toString(),false);
                            list.remove(textView2.getText().toString());
                        }else{
                            cat2.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            textView2.setTextColor(getResources().getColor(R.color.white));
                            mMap.put(textView2.getText().toString(),true);
                            list.add(textView2.getText().toString());
                        }

                    }
                });

                mGridLayout.addView(view1);

            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    boolean isSelected(String category){

        String s=category.toLowerCase();
        for (int i=0;i<tempList.size();i++){
            String t=tempList.get(i).toLowerCase();
            if (t.equals(s)){
                return true;
            }
        }
        return false;
    }
}
