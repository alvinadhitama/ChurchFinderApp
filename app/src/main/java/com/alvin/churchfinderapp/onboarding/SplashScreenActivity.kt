package com.alvin.churchfinderapp.onboarding

import android.app.Service
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.activity.FavoriteActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class SplashScreenActivity : AppCompatActivity() {

    var context = this
    var connectivity : ConnectivityManager? = null
    var info : NetworkInfo?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (isConnected() == true){
            var handler = Handler()
            handler.postDelayed({
                val intent = Intent(this@SplashScreenActivity, OB1Activity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
        else{
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.no_internet_dialog, null)

            val close = view.findViewById<Button>(R.id.btn_close)
            close.setOnClickListener {
                dialog.cancel()
                context.finishAffinity()
            }

            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
        }

//        var handler = Handler()
//        handler.postDelayed({
//            val intent = Intent(this@SplashScreenActivity, OB1Activity::class.java)
//            startActivity(intent)
//            finish()
//        }, 2000)
    }

    fun isConnected():Boolean{
        connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity !=null){
            info = connectivity!!.activeNetworkInfo
            if (info != null){
                if (info!!.state == NetworkInfo.State.CONNECTED){
                    return true
                }
            }
        }
        return false
    }
}