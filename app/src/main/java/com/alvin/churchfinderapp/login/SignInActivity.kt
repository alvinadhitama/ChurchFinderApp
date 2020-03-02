package com.alvin.churchfinderapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alvin.churchfinderapp.HomeActivity
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.model.User
import com.alvin.churchfinderapp.utils.Preferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    var googleSignInClient: GoogleSignInClient? = null
    val RC_SIGN_IN = 1000

    lateinit var uName:String
    lateinit var uUsername:String
    lateinit var uEmail:String
    lateinit var uPhoto:String

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        var currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null){
            finishAffinity()
            startActivity(Intent(this, HomeActivity::class.java))
        }

        preferences = Preferences(this)
        preferences.setValues("onboarding", "1")

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

                        val uid = FirebaseAuth.getInstance().uid ?:""
                        val db = FirebaseFirestore.getInstance()

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

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        btn_login_google.setOnClickListener {
            var signInIntent = googleSignInClient?.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)
        }
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?){
        if (acct != null) {
            Log.d("SignInActivity","Account: " +acct.id)
            Log.d("SignInActivity","Name: " +acct.displayName)
            Log.d("SignInActivity","Email: " +acct.email)
        }
        var credential =  GoogleAuthProvider.getCredential(acct?.idToken,null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener {
                task ->
                if (task.isSuccessful){
                    Log.d("SignInActivity","Sign In With Google success")

                    val uid = FirebaseAuth.getInstance().uid ?:""
                    val db = FirebaseFirestore.getInstance()
                    //uid = acct?.id.toString()
                    uName = acct?.displayName.toString()
                    uUsername = acct?.email.toString()
                    uEmail = acct?.email.toString()
                    uPhoto = acct?.photoUrl.toString()

                    val user = User(
                        uid,
                        uName,
                        uUsername,
                        uEmail,
                        uPhoto
                    )

                    db.collection("users").document(uid).set(user)
                        .addOnSuccessListener {
                            Log.d("SignInActivity","Successfully uploaded user data")
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

                    finishAffinity()
                    val intent = Intent(this,
                        HomeActivity::class.java)
                    startActivity(intent)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        }
    }
}
