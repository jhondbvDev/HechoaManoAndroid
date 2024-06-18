package com.example.hechoamano.ui.customerorders.adapter

import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.databinding.ItemProductListBinding
import com.example.hechoamano.domain.model.Product

class ProductsViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater)  {
    private lateinit var product: Product
    private val binding = ItemProductListBinding.bind(inflater)

    fun render(product: Product, onClickListener: (Product) -> Unit) {
        "${product.family} ${product.subfamily}".also { binding.name.text = it }
        binding.region.text = product.region
        binding.size.text = product.size
        binding.type.text = product.type
        binding.stock.text = product.stock
        binding.disponible.text = "DISPONIBLE"
        itemView.setOnClickListener { onClickViewListener(product) }
        this.product = product

        binding.editStock.setOnEditorActionListener(onEditorActionListener)
    }

    private fun onClickViewListener(product: Product) {
        binding.editStock.visibility = View.VISIBLE
        binding.editStock.requestFocus()
        binding.editStock.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0))
        binding.editStock.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0))
    }

    private val onEditorActionListener = TextView.OnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE
            || actionId == EditorInfo.IME_ACTION_NEXT
            || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
            binding.editStock.visibility = View.GONE
            product.stock = v.text.toString()
            binding.stock.text = product.stock
            return@OnEditorActionListener true
        }
        false
    }

}