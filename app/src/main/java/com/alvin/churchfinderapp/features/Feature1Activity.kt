package com.alvin.churchfinderapp.features

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvin.churchfinderapp.R
import kotlinx.android.synthetic.main.activity_feature1.*

class Feature1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature1)

        btn_next.setOnClickListener {
            val intent = Intent(this, Feature2Activity::class.java)
            startActivity(intent)
        }
    }
}
