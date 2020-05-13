package com.alvin.churchfinderapp.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.activity.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_edit_password.*

class EditPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)

        iv_back.setOnClickListener {
            finish()
        }

        btn_save_password.setOnClickListener {
            val newPassword = editPassword.text.toString()
            val confirm = confirmPassword.text.toString()

            if (newPassword.isEmpty()){
                editPassword.error = "Please fill new password"
                editPassword.requestFocus()
            } else if (confirm.isEmpty()){
                confirmPassword.error = "Please confirm your new password"
                confirmPassword.requestFocus()
            }else if (newPassword != confirm){
                confirmPassword.error = "The Confirm Password does not match"
                confirmPassword.requestFocus()
            }else{
                val user = FirebaseAuth.getInstance().currentUser

                user?.updatePassword(newPassword)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("ChangePassword", "User password updated.")
                        }
                    }

                finishAffinity()
                val intent =  Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
