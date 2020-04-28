package com.alvin.churchfinderapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.login.SignInActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset.*

class ResetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        iv_back.setOnClickListener {
            finish()
        }

        btn_send_reset.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            val email = resetEmail.text.toString()
            if (email.isEmpty()){
                resetEmail.error = "Please fill your email"
                resetEmail.requestFocus()
            }else{
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("ResetPassword", "Email sent.")
                            Toast.makeText(this,"Check your email to reset your password",Toast.LENGTH_LONG).show()
                        }else{
                            val dialog = BottomSheetDialog(this)
                            val view = layoutInflater.inflate(R.layout.no_mail_dialog, null)

                            val close = view.findViewById<Button>(R.id.btn_close)
                            close.setOnClickListener {
                                dialog.cancel()
                            }

                            dialog.setCancelable(false)
                            dialog.setContentView(view)
                            dialog.show()
                        }
                    }
//                finishAffinity()
//                val intent =  Intent(this, SignInActivity::class.java)
//                startActivity(intent)
            }
        }
    }
}
