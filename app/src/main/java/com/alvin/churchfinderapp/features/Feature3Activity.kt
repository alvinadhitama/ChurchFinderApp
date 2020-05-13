package com.alvin.churchfinderapp.features

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.activity.HomeActivity
import kotlinx.android.synthetic.main.activity_feature3.*

class Feature3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature3)

        btn_start.setOnClickListener {
            finishAffinity()
            val intent = Intent(this,
                HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
