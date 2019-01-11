package com.hg.admin11thcommandment.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.admin11thcommandment.R;
import com.hg.admin11thcommandment.activities.AddCategoryActivity;
import com.hg.admin11thcommandment.activities.PostAdvertisementActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private Context mContext;
    private JSONArray mData;

    public CategoryAdapter(Context context) {
        mContext = context;
    }

    public void setData(JSONArray mData){
        this.mData = mData;
    }


    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_view_row,viewGroup,false);
        return new CategoryAdapter.CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder,final int position) {
        try {
            final JSONObject jsonObject = mData.getJSONObject(position);
            categoryHolder.mTitle.setText(jsonObject.getString("category"));
            try{
                if(!jsonObject.getString("imageURL").equals(""))
                    Picasso.get().load(jsonObject.getString("imageURL")).placeholder(R.drawable.news_dummy).fit().centerCrop().into(categoryHolder.mImage);
            }catch (Exception e){
                Log.d("Category ACTIVITY","No image url found");
            }
            categoryHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = position;

                    Intent i = new Intent(mContext,AddCategoryActivity.class);
                    i.putExtra("add",false);
                    i.putExtra("position",String.valueOf(pos));
                    try {
                        i.putExtra("id",jsonObject.getString("_id"));
                        i.putExtra("category",jsonObject.getString("category"));
                    } catch (JSONException e) {
                        Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    mContext.startActivity(i);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mData.length();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        private TextView mTitle;
        private ImageView mImage;
        private CardView mCardView;
        CategoryHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mImage = itemView.findViewById(R.id.image_view);
            mCardView = itemView.findViewById(R.id.card_view);
        }
    }
}
