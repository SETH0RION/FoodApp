package com.pe.edu.idat.grupo2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.ActivityInitialBinding

class InitialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInitialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}