package com.example.hechoamano.ui.stockcontrol.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.databinding.ItemSummaryProductsListBinding
import com.example.hechoamano.databinding.ItemSummaryStockControlListBinding
import com.example.hechoamano.domain.model.Product
import java.text.NumberFormat
import java.util.Locale

class SummaryProductsViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater) {

    private val binding = ItemSummaryStockControlListBinding.bind(inflater)

    fun render(product: Product) {
        (product.family).also { binding.productName.text = it }

        val description = StringBuilder()
        product.subfamily?.let { description.append(it).append(" ") }
        product.type?.let { description.append(it).append(" ") }
        product.size?.let { description.append(it).append(" ") }
        product.region?.let { description.append(it) }
        description.toString().also { binding.productDescription.text = it }

        product.stock.also { binding.cantidadSistema.text = it.toString() }
        product.stockEdited.also { binding.cantidadContada.text = it.toString() }
    }
}