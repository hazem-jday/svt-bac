package com.hazemjday.svtbacsciencesexprimentales

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.navigation)

        if (DarkModePrefManager(this).isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        bottomNavigationView?.setOnItemSelectedListener {
            val f = when (it.itemId) {
                R.id.navigationHome -> {
                    HomeFragment()
                }
                R.id.navigationAbout -> {
                    AboutFragment()
                }
                R.id.navigationSettings -> {
                    SettingsFragment()
                }
                else -> {null}
            }
            if(f != null){
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,f).commit()
                return@setOnItemSelectedListener true
            }
            return@setOnItemSelectedListener false
        }



        val b = intent.extras
        var value = false

        if (b != null) value = b.getBoolean("dark")
        if (value) {
            bottomNavigationView?.selectedItemId = R.id.navigationSettings
        }
        else{
            bottomNavigationView?.selectedItemId = R.id.navigationHome
        }


        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onBackPressed() {
        if(bottomNavigationView?.selectedItemId==R.id.navigationHome){
            finishAffinity()
        }
        else{
            //supportFragmentManager.beginTransaction().replace(R.id.fragment_container,HomeFragment()).commit()
            bottomNavigationView?.selectedItemId=R.id.navigationHome
        }
    }

}