package com.example.appwithapi.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.appwithapi.Fragments.HomeFragment
import com.example.appwithapi.Fragments.SettingsFragment
import com.example.appwithapi.Fragments.UsersFragment
import com.example.appwithapi.R
import com.example.appwithapi.api.RetrofitClient
import com.example.appwithapi.models.LoginResponse
import com.example.appwithapi.models.UsersResponse
import com.example.appwithapi.storage.SharedPreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        bottom_nav.setOnNavigationItemSelectedListener(this)

        displayFragments(HomeFragment())

       // var call: retrofit2.Call<LoginResponse> = RetrofitClient().getInstance().getApi().userlogin()
    }

    private fun displayFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragments_container, fragment)
            .commit()

    }

    @Override
    override fun onStart() {
        super.onStart()
        if (!SharedPreferenceManager(this).getInstance(this).isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var fragment: Fragment? = null

        when(item.itemId) {
            R.id.menu_home -> fragment = HomeFragment()

            R.id.menu_users -> fragment = UsersFragment()

            R.id.menu_settings -> fragment = SettingsFragment()

        }
        if(fragment != null) {
            displayFragments(fragment)
        }
        return false
    }
}
