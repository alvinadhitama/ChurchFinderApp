package com.alvin.churchfinderapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alvin.churchfinderapp.R;
import com.alvin.churchfinderapp.adapter.SearchAdapterJava;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestSearch extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> fullNameList;
    ArrayList<String> userNameList;
    ArrayList<String> profilePicList;
    SearchAdapterJava searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_search);

        search_edit_text = (EditText) findViewById(R.id.search_text_test);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_search);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        /*
         * Create a array list for each node you want to use
         * */
        fullNameList = new ArrayList<>();
        userNameList = new ArrayList<>();
        profilePicList = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    /*
                     * Clear the list when editText is empty
                     * */
                    fullNameList.clear();
                    userNameList.clear();
                    profilePicList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("Church").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                fullNameList.clear();
                userNameList.clear();
                profilePicList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String full_name = snapshot.child("eng_name").getValue(String.class);
                    String user_name = snapshot.child("rating").getValue(String.class);
                    String profile_pic = snapshot.child("display").getValue(String.class);

                    if (full_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        fullNameList.add(full_name);
                        userNameList.add(user_name);
                        profilePicList.add(profile_pic);
                        counter++;
                    } else if (user_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        fullNameList.add(full_name);
                        userNameList.add(user_name);
                        profilePicList.add(profile_pic);
                        counter++;
                    }

                    /*
                     * Get maximum of 15 searched results only
                     * */
                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapterJava(TestSearch.this, fullNameList, userNameList, profilePicList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}