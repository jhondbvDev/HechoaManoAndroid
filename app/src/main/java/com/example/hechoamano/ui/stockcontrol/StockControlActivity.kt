package com.example.hechoamano.ui.stockcontrol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.hechoamano.databinding.ActivityStockControlBinding

class StockControlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockControlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStockControlBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}