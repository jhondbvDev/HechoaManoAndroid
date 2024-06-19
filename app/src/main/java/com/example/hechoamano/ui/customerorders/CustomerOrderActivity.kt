package com.example.hechoamano.ui.customerorders

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.hechoamano.databinding.ActivityCustomerOrderBinding
import com.example.hechoamano.ui.base.BaseActivity

class CustomerOrderActivity : BaseActivity() {

    private lateinit var binding: ActivityCustomerOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCustomerOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}