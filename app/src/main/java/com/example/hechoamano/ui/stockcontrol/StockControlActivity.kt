package com.example.hechoamano.ui.stockcontrol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.hechoamano.databinding.ActivityStockControlBinding
import com.example.hechoamano.ui.base.BaseActivity

class StockControlActivity : BaseActivity() {

    private lateinit var binding: ActivityStockControlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStockControlBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}