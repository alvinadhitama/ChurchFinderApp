package com.alvin.churchfinderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.alvin.churchfinderapp.fragment.DashboardFragment
import com.alvin.churchfinderapp.fragment.FavoriteFragment
import com.alvin.churchfinderapp.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentHome = DashboardFragment()
        val fragmentFavorite = FavoriteFragment()
        val fragmentProfile = ProfileFragment()
        setFragment(fragmentHome)

        iv_home.setOnClickListener {
            setFragment(fragmentHome)
            changeIcon(iv_home, R.drawable.ic_home_gold_active)
            changeIcon(iv_favorite,R.drawable.ic_fav)
            changeIcon(iv_profile_dashboard,R.drawable.ic_profile)
        }

        iv_favorite.setOnClickListener {
            setFragment(fragmentFavorite)
            changeIcon(iv_home, R.drawable.ic_home)
            changeIcon(iv_favorite,R.drawable.ic_fav_gold_active)
            changeIcon(iv_profile_dashboard,R.drawable.ic_profile)
        }

        iv_profile_dashboard.setOnClickListener {
            setFragment(fragmentProfile)
            changeIcon(iv_home, R.drawable.ic_home)
            changeIcon(iv_favorite,R.drawable.ic_fav)
            changeIcon(iv_profile_dashboard,R.drawable.ic_profile_gold_active)
        }
    }

    protected fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame,fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }
}