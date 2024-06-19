package com.example.hechoamano.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.hechoamano.databinding.ActivityLoginBinding
import com.example.hechoamano.ui.base.BaseActivity
import com.example.hechoamano.ui.home.LoadInventaryActivity

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoadInventaryActivity::class.java)
            startActivity(intent)
        }
    }
}