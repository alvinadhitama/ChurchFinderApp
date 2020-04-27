package com.alvin.churchfinderapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.utils.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_email.*

class EditEmailActivity : AppCompatActivity() {

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_email)

        preferences = Preferences(this)

        btn_save_email.setOnClickListener {
            val email = editEmail.text.toString()
            if (email.isEmpty()){
                editEmail.error = "Please fill your new email"
                editEmail.requestFocus()
            }else{
                val user = FirebaseAuth.getInstance().currentUser
                val uid = FirebaseAuth.getInstance().uid ?:""
                val db = FirebaseFirestore.getInstance()

                user?.updateEmail(email)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Change Email", "User email address updated.")
                        }
                    }

                val saveRef = db.collection("users").document(uid)
                saveRef.update("email",editEmail.text.toString())
                preferences.setValues("email",email)

                finishAffinity()
                val intent =  Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
