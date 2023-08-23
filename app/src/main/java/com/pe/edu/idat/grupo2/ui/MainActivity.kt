package com.pe.edu.idat.grupo2.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var modeSwitch: SwitchCompat
    private var nightMode:Boolean= false
    private var editor: SharedPreferences.Editor?= null
    private var sharedPreferences: SharedPreferences?= null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modeSwitch = findViewById(R.id.switchMode)
        sharedPreferences = getSharedPreferences("MODE" , MODE_PRIVATE)
        nightMode = sharedPreferences?.getBoolean("nightMode",false)!!

        if (nightMode) {
            modeSwitch.isChecked=true

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        modeSwitch.setOnCheckedChangeListener { compoundButton, state ->

            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("nightMode", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("nightMode", true)
            }
            editor?.apply()
        }

        val fragment = supportFragmentManager.findFragmentById(R.id.fcv_navigation) as NavHostFragment
        val navController = fragment.navController
        binding.bnvinferior.setupWithNavController(navController)

    }

}