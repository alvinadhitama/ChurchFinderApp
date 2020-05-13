package com.alvin.churchfinderapp.edit

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.activity.HomeActivity
import com.alvin.churchfinderapp.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.iv_back
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    lateinit var preferences: Preferences
    lateinit var filePath: Uri

    lateinit var fileUrl :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        preferences = Preferences(this)

        Glide.with(this)
            .load(preferences.getValues("photo"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile_edit)


        btn_change.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.select_photo_dialog, null)
            val gallery = view.findViewById<TextView>(R.id.btn_gallery)
            gallery.setOnClickListener {
                ImagePicker.with(this)
                    .galleryOnly()
                    .start()
                dialog.cancel()
            }

            val camera = view.findViewById<TextView>(R.id.btn_camera)
            camera.setOnClickListener {
                ImagePicker.with(this)
                    .cameraOnly()
                    .start()
                dialog.cancel()
            }
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()

        }

        val uid = FirebaseAuth.getInstance().uid ?:""
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener {document ->
                if (document != null){
                    val name =  document.getString("name").toString()
                    val username =  document.getString("username").toString()
                    fileUrl = document.getString("photo").toString()
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
            val name = editName.text.toString()
            val username = editUsername.text.toString()
            if(name.isEmpty()){
                editName.error = "Please fill your name"
                editName.requestFocus()
            }
            else if(username.isEmpty()){
                editUsername.error = "Please fill your email address"
                editUsername.requestFocus()
            }
            else{
                preferences.setValues("name",editName.text.toString())
                preferences.setValues("username",editUsername.text.toString())
                uploadUserData()
                uploadPhoto()
//            val saveRef = db.collection("users").document(uid)
//            saveRef.update("name",editName.text.toString(),"username",editUsername.text.toString(),"photo",fileUrl)
//            preferences.setValues("name",editName.text.toString())
//            preferences.setValues("username",editUsername.text.toString())
//            preferences.setValues("photo",fileUrl)
                //saveRef.update(mapOf("name" to editName.text.toString(), "username" to editUsername.text.toString()))
                finishAffinity()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }

        }
        iv_back.setOnClickListener {
            finish()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null){
            Log.d("SignUpActivity","Photo was selected")

            filePath = data?.data!!

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile_edit)

            btn_change.setImageResource(R.drawable.ic_btn_delete)
        }
    }

    private fun uploadPhoto(){
        //if (filePath == null) return

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
                    //uploadUserData(it.toString())
                    preferences.setValues("name",editName.text.toString())
                    preferences.setValues("username",editUsername.text.toString())
                    uploadUser(it.toString())
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

    private fun uploadUser(profilePhoto: String){
        val uid = FirebaseAuth.getInstance().uid ?:""
        val db = FirebaseFirestore.getInstance()

        val saveRef = db.collection("users").document(uid)
        saveRef.update("name",editName.text.toString(),"username",editUsername.text.toString(),"photo",profilePhoto)
        preferences.setValues("photo",profilePhoto)
    }
    private fun uploadUserData(){
        val uid = FirebaseAuth.getInstance().uid ?:""
        val db = FirebaseFirestore.getInstance()

        val saveRef = db.collection("users").document(uid)
        saveRef.update("name",editName.text.toString(),"username",editUsername.text.toString())
    }
}