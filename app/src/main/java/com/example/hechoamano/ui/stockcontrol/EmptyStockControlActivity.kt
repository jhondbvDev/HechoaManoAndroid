package com.example.hechoamano.ui.stockcontrol

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hechoamano.databinding.ActivityEmptyStockControlBinding

class EmptyStockControlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmptyStockControlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmptyStockControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOrder.setOnClickListener {
            val intent = Intent(this, EmployeesStockControlActivity::class.java)
            startActivity(intent)
        }

        binding.buttonBack.setOnClickListener { this.onBackPressed() }
    }
}