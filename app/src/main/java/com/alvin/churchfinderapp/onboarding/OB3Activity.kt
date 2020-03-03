package com.alvin.churchfinderapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.login.SignInActivity
import kotlinx.android.synthetic.main.activity_ob3.*

class OB3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ob3)

        btn_start.setOnClickListener {
            finishAffinity()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}