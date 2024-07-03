package com.example.hechoamano.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.databinding.ItemCustomListBinding
import com.example.hechoamano.databinding.ItemEmployeeListBinding
import com.example.hechoamano.domain.model.Employee

class CustomViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater) {

    private val binding = ItemCustomListBinding.bind(inflater)
    fun render(value: String, valuesSelected: MutableList<String>, onClickListener: (String, Boolean) -> Unit) {
        binding.radioButton.text = value
        binding.radioButton.isChecked = valuesSelected.contains(value.lowercase())
        binding.radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!buttonView.isPressed)
                return@setOnCheckedChangeListener
            onClickListener(value, isChecked)
        }
    }
}