package com.example.hechoamano.ui.customerorders.adapter

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
        (product.family + " " + product.subfamily).also { binding.productName.text = it }
        (product.type + " | " + product.size + " | " + product.region).also { binding.productDescription.text = it }

        format.format(product.buyPrice.toInt()).also { binding.productPrice.text = it }
        val total = product.buyPrice.toInt() * product.stockEdited
        format.format(total).also { binding.productTotal.text = it }
    }
}