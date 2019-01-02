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

import com.hg.admin11thcommandment.activities.PostAdvertisementActivity;
import com.hg.admin11thcommandment.activities.PostNewsActivity;
import com.hg.admin11thcommandment.R;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.AdvertisementHolder> {

    private Context mContext;
    private JSONArray mData;

    public AdvertisementAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(JSONArray mData){
        this.mData = mData;
    }

    @NonNull
    @Override
    public AdvertisementHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row,parent,false);
        return new AdvertisementHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdvertisementAdapter.AdvertisementHolder advertisementHolder, final int position) {
        try {
            final JSONObject jsonObject = mData.getJSONObject(position);
            advertisementHolder.mTitle.setText(jsonObject.getString("title"));
            advertisementHolder.mDescription.setText(jsonObject.getString("description"));
            if(!jsonObject.getString("URL").equals(""))
                Picasso.get().load(jsonObject.getString("URL")).placeholder(R.drawable.news_dummy).fit().centerCrop().into(advertisementHolder.mImage);
            advertisementHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = position;

                    Intent i = new Intent(mContext,PostAdvertisementActivity.class);
                    i.putExtra("add",false);
                    i.putExtra("position",String.valueOf(pos));
                    try {
                        i.putExtra("type",jsonObject.getString("type"));
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

    class AdvertisementHolder extends RecyclerView.ViewHolder{
        private  TextView mTitle,mDescription;
        private ImageView mImage;
        private CardView mCardView;
        AdvertisementHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mDescription = itemView.findViewById(R.id.tv_description);
            mImage = itemView.findViewById(R.id.image_view);
            mCardView = itemView.findViewById(R.id.card_view);
        }
    }
}
