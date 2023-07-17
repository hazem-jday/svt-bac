package com.hazemjday.svtbacsciencesexprimentales

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial

class StartupActivity : AppCompatActivity() {
    private var bottomNavigationView: BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        val darkModePrefManager = DarkModePrefManager(this)
        darkModePrefManager.setDarkMode(false)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_startup)


        val dark = findViewById<SwitchMaterial>(R.id.drkswitch)





    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}