package com.example.hechoamano.ui.productentry

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.hechoamano.databinding.ActivityProductEntryBinding

class ProductEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}