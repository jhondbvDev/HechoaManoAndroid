package com.example.hechoamano

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import com.example.hechoamano.databinding.ActivityMainBinding
import com.example.hechoamano.ui.base.BaseActivity
import com.example.hechoamano.ui.login.LoginActivity


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val splashTimeOut: Long = 2000 // 3 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, splashTimeOut) // 3 segundos


            binding.artisanLogo.animate()
                .translationY(-1000f)
                .alpha(0.0f)
                .setDuration(splashTimeOut)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        binding.artisanLogo.visibility = View.GONE
                    }
                })

            binding.splashImage.animate()
                .translationY(0f)
                .alpha(0.0f)
                .setDuration(splashTimeOut)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        binding.artisanLogo.visibility = View.GONE
                    }
                })

            binding.yearText.animate()
                .translationY(1000f)
                .alpha(0.0f)
                .setDuration(splashTimeOut)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        binding.artisanLogo.visibility = View.GONE
                    }
                })

            binding.splashText.animate()
                .translationY(1000f)
                .alpha(0.0f)
                .setDuration(splashTimeOut)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        binding.artisanLogo.visibility = View.GONE
                    }
                })
        }, splashTimeOut)
    }
}