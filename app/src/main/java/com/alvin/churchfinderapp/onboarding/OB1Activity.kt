package com.alvin.churchfinderapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvin.churchfinderapp.utils.Preferences
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.login.SignInActivity
import kotlinx.android.synthetic.main.activity_ob1.*

class OB1Activity : AppCompatActivity() {

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ob1)

        preferences = Preferences(this)

        if (preferences.getValues("onboarding").equals("1")){
            finishAffinity()

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        btn_next.setOnClickListener {
            val intent = Intent(this, OB2Activity::class.java)
            startActivity(intent)
        }

        btn_skip.setOnClickListener {
            finishAffinity()

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}