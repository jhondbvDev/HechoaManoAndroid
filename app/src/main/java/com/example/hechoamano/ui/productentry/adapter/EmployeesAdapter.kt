package com.example.hechoamano.ui.productentry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R
import com.example.hechoamano.domain.model.Employee
import com.example.hechoamano.ui.productentry.adapter.EmployeesViewHolder

class EmployeesAdapter (private  var employees: List<Employee>, private val onClickListener: (Employee) -> Unit)
    : RecyclerView.Adapter<EmployeesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EmployeesViewHolder(layoutInflater.inflate(R.layout.item_employee_list, parent, false))
    }

    override fun onBindViewHolder(holder: EmployeesViewHolder, position: Int) {
        val item = employees[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = employees.size

    fun filterList(filterList: List<Employee>) {
        employees = filterList
        notifyDataSetChanged()
    }
}