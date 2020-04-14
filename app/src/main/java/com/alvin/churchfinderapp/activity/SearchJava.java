package com.alvin.churchfinderapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvin.churchfinderapp.R;
import com.alvin.churchfinderapp.adapter.ViewHolder;
import com.alvin.churchfinderapp.model.Church;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;

public class SearchJava extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_java);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Search");
        toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");

        mRecyclerView = findViewById(R.id.rv_list_search);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mfirebaseDatabase.getReference("Church");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Church, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Church, ViewHolder>(
                        Church.class,
                        R.layout.row_item_all,
                        ViewHolder.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Church church, int position) {
                        viewHolder.setDetails(getApplicationContext(),church.getEng_name(),church.getRating(),church.getDisplay());
                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView mNameTv = view.findViewById(R.id.tv_simple_name_all);
                                TextView mRatingTv = view.findViewById(R.id.tv_rate_all);
                                ImageView mImageView = view.findViewById(R.id.iv_poster_image_all);

                                String mName = mNameTv.getText().toString();
                                String mRating = mRatingTv.getText().toString();
                                String mImage = mImageView.toString();
                                //Glide.with(getApplicationContext()).load(mImage).into(mImageView);

                                Drawable mDrawable = mImageView.getDrawable();
                                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                                Intent intent = new Intent(view.getContext(), DetailActivityJava.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                                byte[] bytes = stream.toByteArray();
                                intent.putExtra("display",mImage);
                                intent.putExtra("eng_name",mName);
                                intent.putExtra("rating",mRating);
                                startActivity(intent);

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        return viewHolder;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void firebaseSearch(String searchText){
        Query firebaseSearchQuery = mRef.orderByChild("simple_name").startAt(searchText).endAt(searchText + "\uf88ff");

        FirebaseRecyclerAdapter<Church, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Church, ViewHolder>(
                        Church.class,
                        R.layout.row_item_all,
                        ViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Church church, int i) {
                        viewHolder.setDetails(getApplicationContext(),church.getEng_name(),church.getRating(),church.getDisplay());
                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView mNameTv = view.findViewById(R.id.tv_simple_name_all);
                                TextView mRatingTv = view.findViewById(R.id.tv_rate_all);
                                ImageView mImageView = view.findViewById(R.id.iv_poster_image_all);

                                String mName = mNameTv.getText().toString();
                                String mRating = mRatingTv.getText().toString();
                                Drawable mDrawable = mImageView.getDrawable();
                                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                                byte[] bytes = stream.toByteArray();
                                intent.putExtra("display",bytes);
                                intent.putExtra("eng_name",mName);
                                intent.putExtra("rating",mRating);
                                startActivity(intent);

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        return viewHolder;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search_data);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    //    private void firebaseSearch(String searchText){
//        Query firebaseSearchQuery = mRef.orderByChild("simple_name").startAt(searchText).endAt(searchText + "\uf8ff");
//
//        FirebaseRecyclerAdapter<Church, ViewHolder> firebaseRecyclerAdapter =
//                new FirebaseRecyclerAdapter<Church, ViewHolder>(
//                        Church.class,
//                        R.layout.row_item_all,
//                        ViewHolder.class,
//                        firebaseSearchQuery
//                ) {
//                    @Override
//                    protected void populateViewHolder(ViewHolder viewHolder, Church church, int position) {
//                        viewHolder.setDetails(getApplicationContext(),church.getEng_name(),church.getRating(),church.getDisplay());
//                    }
//                };
//        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseRecyclerAdapter<Church, ViewHolder> firebaseRecyclerAdapter =
//                new FirebaseRecyclerAdapter<Church, ViewHolder>(
//                        Church.class,
//                        R.layout.row_item_all,
//                        ViewHolder.class,
//                        mRef
//                ) {
//                    @Override
//                    protected void populateViewHolder(ViewHolder viewHolder, Church church, int i) {
//                        viewHolder.setDetails(getApplicationContext(),church.getEng_name(),church.getRating(),church.getDisplay());
//                    }
//
//                };
//
//        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search,menu);
//        MenuItem item = menu.findItem(R.id.search_data);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                firebaseSearch(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                firebaseSearch(newText);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//
//        return super.onOptionsItemSelected(item);
//    }
}
