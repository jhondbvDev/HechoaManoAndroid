package com.example.hechoamano.ui.productentry

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.hechoamano.databinding.ActivityEmptyProductEntryBinding
import com.example.hechoamano.ui.base.BaseActivity

class EmptyProductEntryActivity : BaseActivity() {

    private lateinit var binding: ActivityEmptyProductEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEmptyProductEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOrder.setOnClickListener {
            val intent = Intent(this, EmployeesProductEntryActivity::class.java)
            startActivity(intent)
        }

        binding.buttonBack.setOnClickListener { this.onBackPressed() }
    }
}