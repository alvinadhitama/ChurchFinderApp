package com.alvin.churchfinderapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvin.churchfinderapp.R
import kotlinx.android.synthetic.main.activity_ob2.*

class OB2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ob2)

        btn_next.setOnClickListener {
            val intent = Intent(this, OB3Activity::class.java)
            startActivity(intent)
        }
    }
}
