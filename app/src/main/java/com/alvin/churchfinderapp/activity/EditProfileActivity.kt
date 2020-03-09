package com.alvin.churchfinderapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alvin.churchfinderapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val uid = FirebaseAuth.getInstance().uid ?:""
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener {document ->
                if (document != null){
                    val name =  document.getString("name").toString()
                    val username =  document.getString("username").toString()
                    editName.setText(name)
                    editUsername.setText(username)
                }else{
                    Log.d("Edit Profile", "No such document")
                }
            }
            .addOnFailureListener {exception->
                Log.d("Edit Profile", "get failed with ", exception)
            }

        btn_save_edit.setOnClickListener {
            val saveRef = db.collection("users").document(uid)
            saveRef.update("name",editName.text.toString(),"username",editUsername.text.toString())
            //saveRef.update(mapOf("name" to editName.text.toString(), "username" to editUsername.text.toString()))

            finishAffinity()
        }
        iv_back.setOnClickListener {
            finish()
        }


    }
}