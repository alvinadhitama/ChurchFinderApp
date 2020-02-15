package com.alvin.churchfinderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btn_login.setOnClickListener {
            val email = et_email.text.toString()
            val password = et_password.text.toString()

            if (email.isEmpty()){
                et_email.error = "Please enter your email"
                et_email.requestFocus()
            }else if(password.isEmpty()){
                et_password.error = "Please enter your password"
                et_password.requestFocus()
            }else{
                Log.d("Login","Attempt login with email/pw: $email/***")

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {
                        if(!it.isSuccessful) return@addOnCompleteListener

                        Log.d("Login","Attempt login with uid: ")

                        finishAffinity()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this,"Email or Password is wrong",Toast.LENGTH_SHORT).show()
                    }
            }


        }

        btn_register.setOnClickListener {
            val intent =  Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
