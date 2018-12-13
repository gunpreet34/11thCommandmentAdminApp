package com.hg.admin11thcommandment.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.admin11thcommandment.activities.PostNewsActivity;
import com.hg.admin11thcommandment.R;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private Context mContext;
    private JSONArray mData;

    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(JSONArray mData){
        this.mData = mData;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row,parent,false);
        return new NewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, final int position) {
        try {
            final JSONObject jsonObject = mData.getJSONObject(position);
            newsHolder.mTitle.setText(jsonObject.getString("title"));
            newsHolder.mDescription.setText(jsonObject.getString("description"));
            if(!jsonObject.getString("imageURL").equals(""))
                Picasso.get().load(jsonObject.getString("imageURL")).placeholder(R.drawable.news_dummy).fit().centerCrop().into(newsHolder.mImage);
            newsHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = position;

                    Intent i = new Intent(mContext,PostNewsActivity.class);
                    i.putExtra("add",false);
                    i.putExtra("position",String.valueOf(pos));
                    try {
                        i.putExtra("id",jsonObject.getString("_id"));
                        i.putExtra("title",jsonObject.getString("title"));
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

    class NewsHolder extends RecyclerView.ViewHolder{
        private  TextView mTitle,mDescription;
        private ImageView mImage;
        private CardView mCardView;
        NewsHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mDescription = itemView.findViewById(R.id.tv_description);
            mImage = itemView.findViewById(R.id.image_view);
            mCardView = itemView.findViewById(R.id.card_view);
        }
    }
}
