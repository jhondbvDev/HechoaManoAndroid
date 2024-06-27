package com.example.hechoamano.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R

class CustomAdapter(private var values: List<String>) : RecyclerView.Adapter<CustomViewHolder>() {

    var valuesSelected: MutableList<String> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(R.layout.item_custom_list, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = values[position]
        holder.render(item, onClickListener = { value, checked ->
            if (checked)
                valuesSelected.add(value.lowercase())
            else
                valuesSelected.remove(value.lowercase())
        })
    }

    override fun getItemCount(): Int = values.size
}