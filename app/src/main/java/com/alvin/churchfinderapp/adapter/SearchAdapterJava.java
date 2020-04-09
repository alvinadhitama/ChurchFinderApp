package com.alvin.churchfinderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alvin.churchfinderapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapterJava extends RecyclerView.Adapter<SearchAdapterJava.SearchViewHolder> {
    Context context;
    ArrayList<String> fullNameList;
    ArrayList<String> userNameList;
    ArrayList<String> profilePicList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView full_name, user_name;

        public SearchViewHolder(View itemView) {
            super(itemView);
            profileImage = (ImageView) itemView.findViewById(R.id.iv_poster_image_all);
            full_name = (TextView) itemView.findViewById(R.id.tv_simple_name_all);
            user_name = (TextView) itemView.findViewById(R.id.tv_rate_all);
        }
    }

    public SearchAdapterJava(Context context, ArrayList<String> fullNameList, ArrayList<String> userNameList, ArrayList<String> profilePicList) {
        this.context = context;
        this.fullNameList = fullNameList;
        this.userNameList = userNameList;
        this.profilePicList = profilePicList;
    }

    @Override
    public SearchAdapterJava.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_all, parent, false);
        return new SearchAdapterJava.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.full_name.setText(fullNameList.get(position));
        holder.user_name.setText(userNameList.get(position));

        Glide.with(context)
                .load(profilePicList.get(position))
                .into(holder.profileImage);

        holder.full_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Full Name Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fullNameList.size();
    }
}
