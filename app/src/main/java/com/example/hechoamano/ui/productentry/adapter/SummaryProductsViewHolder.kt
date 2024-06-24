package com.example.hechoamano.ui.productentry.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.databinding.ItemSummaryProductsListBinding
import com.example.hechoamano.domain.model.Product
import java.text.NumberFormat
import java.util.Locale

class SummaryProductsViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater) {

    private val binding = ItemSummaryProductsListBinding.bind(inflater)
    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))

    fun render(product: Product) {

        binding.productName.text = product.subfamily?.let { product.family + " " + it } ?: product.family

        val description = StringBuilder(binding.productDescription.text)
        product.type?.let { description.append(it).append(" | ") }
        product.size?.let { description.append(it).append(" | ") }
        product.region?.let { description.append(it) }
        binding.productDescription.text = description.toString()

        format.format(product.buyPrice.toInt()).also { binding.productPrice.text = it }
        val total = product.buyPrice.toInt() * product.stockEdited
        format.format(total).also { binding.productTotal.text = it }
    }
}