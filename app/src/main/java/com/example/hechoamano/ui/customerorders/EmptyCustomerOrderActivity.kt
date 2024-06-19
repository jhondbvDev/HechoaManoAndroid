package com.example.hechoamano.ui.customerorders

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.hechoamano.databinding.ActivityEmptyCustomerOrderBinding
import com.example.hechoamano.ui.base.BaseActivity

class EmptyCustomerOrderActivity : BaseActivity() {

    private lateinit var binding: ActivityEmptyCustomerOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEmptyCustomerOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOrder.setOnClickListener {
            val intent = Intent(this, ClientCustomerOrderActivity::class.java)
            startActivity(intent)
        }

        binding.buttonBack.setOnClickListener { this.onBackPressed() }
    }
}