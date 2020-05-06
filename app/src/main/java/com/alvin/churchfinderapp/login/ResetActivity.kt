package com.alvin.churchfinderapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.alvin.churchfinderapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset.*

class ResetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        //button back
        iv_back.setOnClickListener {
            finish()
        }

        //button reset
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
                            //successful
                            Log.d("ResetPassword", "Email sent.")
                            val dialog = BottomSheetDialog(this)
                            val view = layoutInflater.inflate(R.layout.mail_sent_dialog, null)

                            val close = view.findViewById<Button>(R.id.btn_close)
                            close.setOnClickListener {
                                dialog.cancel()
                                finishAffinity()
                                val intent =  Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                            }
                            dialog.setCancelable(false)
                            dialog.setContentView(view)
                            dialog.show()
                        }else{
                            //fail
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
            }
        }
    }
}
