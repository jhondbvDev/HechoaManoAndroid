package com.example.hechoamano.ui.stockcontrol.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.databinding.ItemEmployeeListBinding
import com.example.hechoamano.domain.model.Employee

class EmployeesViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater) {

    private val binding = ItemEmployeeListBinding.bind(inflater)
    fun render(employee: Employee, onClickListener: (Employee) -> Unit) {
        binding.name.text = employee.name
        itemView.setOnClickListener { onClickListener(employee) }
    }
}