package com.example.hechoamano.ui.stockcontrol

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.hechoamano.databinding.ActivityStockControlBinding
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.base.BaseActivity
import com.example.hechoamano.ui.productentry.EmployeesProductEntryActivity

class StockControlActivity : BaseActivity() {

    private lateinit var binding: ActivityStockControlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStockControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOrder.setOnClickListener {
            val intent = Intent(this, EmployeesProductEntryActivity::class.java)
            startActivity(intent)
        }

        binding.buttonBack.setOnClickListener { this.onBackPressed() }
    }
}