package com.alvin.churchfinderapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvin.churchfinderapp.R;

public class DetailActivityJava extends AppCompatActivity {

    TextView mEngNameTv, mRatingTv;
    ImageView mImageIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_java);

        mEngNameTv = findViewById(R.id.eng_name);
        mRatingTv = findViewById(R.id.church_rate);
        mImageIv = findViewById(R.id.iv_poster);

        byte[] bytes = getIntent().getByteArrayExtra("display");
        String eng_name = getIntent().getStringExtra("eng_name");
        String rating = getIntent().getStringExtra("rating");
        assert bytes != null;
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        mEngNameTv.setText(eng_name);
        mRatingTv.setText(rating);
        mImageIv.setImageBitmap(bmp);


    }
}
