package com.alvin.churchfinderapp.features

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvin.churchfinderapp.R
import kotlinx.android.synthetic.main.activity_feature2.*

class Feature2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature2)

        btn_next.setOnClickListener {
            val intent = Intent(this, Feature3Activity::class.java)
            startActivity(intent)
        }
    }
}
