package com.alvin.churchfinderapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvin.churchfinderapp.activity.EditProfileActivity
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.activity.Feature1Activity
import com.alvin.churchfinderapp.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    lateinit var preferences: Preferences
    var googleSignInClient : GoogleSignInClient?= null

    lateinit var mDatabase2: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        val uid = FirebaseAuth.getInstance().uid ?:""
//        val db = FirebaseFirestore.getInstance()
//
//        val docRef = db.collection("users").document(uid)
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d("Profile", "DocumentSnapshot data: ${document.data}")
//
//                    tv_name.text = document.getString("name")
//                    tv_username.text = document.getString("username")
//                    Glide.with(this)
//                        .load(document.getString("photo"))
//                        .apply(RequestOptions.circleCropTransform())
//                        .into(iv_profile)
//
//                } else {
//                    Log.d("Profile", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("Profile", "get failed with ", exception)
//            }
        //////////////
        preferences = Preferences(context!!.applicationContext)

        tv_name.text = preferences.getValues("name")
        tv_username.text = preferences.getValues("username")
        Glide.with(this)
            .load(preferences.getValues("photo"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        val akun = preferences.getValues("account")

        if (akun == "google"){
            tv_edit_profile.visibility = View.INVISIBLE
        }

        tv_edit_profile.setOnClickListener {
            startActivity(Intent(activity, EditProfileActivity::class.java))
        }

        feature.setOnClickListener {
            startActivity(Intent(activity, Feature1Activity::class.java))
        }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this!!.activity!!,gso)

        btn_logout.setOnClickListener {
            logout()
        }

    }

    fun logout(){
        preferences.setValues("name","")
        preferences.setValues("email","")
        preferences.setValues("photo","")
        preferences.setValues("username","")
        preferences.setValues("uid","")
        FirebaseAuth.getInstance().signOut()
        googleSignInClient?.signOut()
        activity?.finishAffinity()
    }
}
