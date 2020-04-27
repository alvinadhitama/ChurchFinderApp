package com.alvin.churchfinderapp.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.activity.*
import com.alvin.churchfinderapp.login.SignInActivity
import com.alvin.churchfinderapp.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.chrisbanes.photoview.PhotoView
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

    lateinit var myDialog: Dialog
    lateinit var btn_no : TextView
    lateinit var btn_yes : TextView

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
            tv_edit_password.visibility = View.INVISIBLE
            tv_edit_email.visibility = View.INVISIBLE
        }

        tv_edit_profile.setOnClickListener {
            startActivity(Intent(activity, EditProfileActivity::class.java))
        }

        tv_edit_password.setOnClickListener {
            startActivity(Intent(activity, EditPasswordActivity::class.java))
        }

        tv_edit_email.setOnClickListener {
            startActivity(Intent(activity, EditEmailActivity::class.java))
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
            myDialog = Dialog(context!!)
            myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            myDialog.setContentView(R.layout.logout_dialog)
            btn_yes = myDialog.findViewById(R.id.btn_yes) as TextView
            btn_yes.isEnabled = true

            btn_no = myDialog.findViewById(R.id.btn_no) as TextView
            btn_no.isEnabled = true

            btn_yes.setOnClickListener {
                logout()
            }

            btn_no.setOnClickListener {
                myDialog.dismiss()
            }

            myDialog.show()
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
        startActivity(Intent(activity,SignInActivity::class.java))
    }
}
