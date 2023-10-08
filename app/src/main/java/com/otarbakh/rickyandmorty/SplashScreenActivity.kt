package com.otarbakh.rickyandmorty

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.otarbakh.rickyandmorty.databinding.SplashscreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: SplashscreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val delayMillis: Long = 1000

        lifecycleScope.launch {
            delay(delayMillis)
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}