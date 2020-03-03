package com.alvin.churchfinderapp.login

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alvin.churchfinderapp.activity.HomeActivity
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.model.User
import com.alvin.churchfinderapp.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUpActivity : AppCompatActivity() {

    var statusAdd:Boolean = false
    lateinit var filePath: Uri

    lateinit var uName:String
    lateinit var uUsername:String
    lateinit var uEmail:String

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        preferences = Preferences(this)

        btn_register.setOnClickListener {
            performRegister()
        }
        //Back Button
        iv_back.setOnClickListener {
            finish()
        }

        btn_add.setOnClickListener {
            Log.d("SignUpActivity","Try to select photo")
            if (statusAdd){
                statusAdd = false
                btn_add.setImageResource(R.drawable.ic_btn_upload)
                iv_profile_dashboard.setImageResource(R.drawable.illustration_photo)
            }else{
                ImagePicker.with(this)
                    .galleryOnly()
                    .start()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null){
            statusAdd = true
            Log.d("SignUpActivity","Photo was selected")

            filePath = data?.data!!

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile_dashboard)

            btn_add.setImageResource(R.drawable.ic_btn_delete)
        }
    }

    private fun performRegister(){
        val name = et_name.text.toString()
        val username = et_username.text.toString()
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        if (name.isEmpty()){
            et_name.error = "Please fill your name"
            et_name.requestFocus()
        }else if (username.isEmpty()) {
            et_username.error = "Please enter your username"
            et_username.requestFocus()
        }else if (email.isEmpty()){
            et_email.error = "Please enter yout email"
            et_email.requestFocus()
        }else if (password.isEmpty()){
            et_password.error = "Please enter your password"
            et_password.requestFocus()
        }else if(statusAdd==false){
            Toast.makeText(this,"Please select your profile photo",Toast.LENGTH_SHORT).show()
        }else{
            var statusUsername = username.indexOf(".")
            if (statusUsername >= 0){
                et_username.error = "Don't use dot(.)"
                et_username.requestFocus()
            }else{
                Log.d("SignUpActivity","Name: "+name)
                Log.d("SignUpActivity","Username: "+username)
                Log.d("SignUpActivity","Email: "+email)
                Log.d("SignUpActivity","Password: "+password)
                //Firebase Authentication
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener {
                        if(!it.isSuccessful) return@addOnCompleteListener
                        Log.d("SignUpActivity", "Successfully created user with uid: ${it.result?.user?.uid}")
                        uploadPhoto()
                        finishAffinity()
                        val intent = Intent(this,
                            HomeActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Log.d("SignUpActivity","Failed to create user: ${it.message}")
                        Toast.makeText(this, "${it.message}",Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun uploadPhoto(){
        if (filePath == null) return

        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(filePath!!)
            .addOnSuccessListener {
                Log.d("SignUpActivity","Successfully uploaded image: ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("SignUpActivity","File Location: $it")
                    uploadUserData(it.toString())
                }
            }
            .addOnProgressListener { taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
            }
            .addOnFailureListener {
                Log.d("SignUpActivity","Failed to create account: ${it.message}")
                Toast.makeText(this,"${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadUserData(profilePhoto: String){
        val uid = FirebaseAuth.getInstance().uid ?:""
        val db = FirebaseFirestore.getInstance()

        uName = et_name.text.toString()
        uUsername = et_username.text.toString()
        uEmail = et_email.text.toString()

        val user = User(
            uid,
            uName,
            uUsername,
            uEmail,
            profilePhoto
        )

        db.collection("users").document(uid).set(user)
            .addOnSuccessListener {
                Log.d("SignUpActivity","Successfully uploaded user data")
            }

        val docRef = db.collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Profile", "DocumentSnapshot data: ${document.data}")
                    preferences.setValues("name",document.getString("name").toString())
                    preferences.setValues("email",document.getString("email").toString())
                    preferences.setValues("photo",document.getString("photo").toString())
                    preferences.setValues("username",document.getString("username").toString())
                    preferences.setValues("uid",document.getString("uid").toString())
                } else {
                    Log.d("Profile", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Profile", "get failed with ", exception)
            }
    }
}