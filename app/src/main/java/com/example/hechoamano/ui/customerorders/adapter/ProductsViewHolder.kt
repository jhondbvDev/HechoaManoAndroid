package com.example.hechoamano.ui.customerorders.adapter

import android.content.Context
import android.content.Context.*
import android.os.SystemClock
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R
import com.example.hechoamano.databinding.ItemProductListBinding
import com.example.hechoamano.domain.model.Product


class ProductsViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater) {
    private lateinit var product: Product
    private val binding = ItemProductListBinding.bind(inflater)
    private val downEvent = MotionEvent.obtain(
        SystemClock.uptimeMillis(),
        SystemClock.uptimeMillis(),
        MotionEvent.ACTION_DOWN,
        0f,
        0f,
        0
    )
    private val upEvent = MotionEvent.obtain(
        SystemClock.uptimeMillis(),
        SystemClock.uptimeMillis(),
        MotionEvent.ACTION_UP,
        0f,
        0f,
        0
    )
    private val greenCardColor = ContextCompat.getColor(this.itemView.context, R.color.green_cards)
    private val blueCardBackground =
        ContextCompat.getDrawable(this.itemView.context, R.drawable.button_list_blue)
    private val graySelectedCardColor =
        ContextCompat.getColor(this.itemView.context, R.color.gray_selected_card)


    fun render(product: Product) {
        binding.name.text = product.subfamily?.let { product.family + " " + it } ?: product.family
        binding.region.text = product.region

        product.size?.let {
            binding.size.text = it
            binding.size.visibility = View.VISIBLE
        } ?: run {
            binding.size.visibility = View.GONE
        }

        product.type?.let {
            binding.type.text = it
            binding.type.visibility = View.VISIBLE
        } ?: run {
            binding.type.visibility = View.GONE
        }

        if (product.subfamily != null && product.size == null && product.type == null) {
            binding.type.text = product.subfamily
            binding.type.visibility = View.VISIBLE
        }

        setStyleEdited(product)
        itemView.setOnClickListener {
            onClickViewListener(product)
        }
        this.product = product

        binding.editStock.setOnEditorActionListener(onEditorActionListener)
    }

    private fun setStyleEdited(product: Product) {
        if (product.edited) {
            binding.stockEdited.text = product.stockEdited.toString()
            binding.stockEdited.visibility = View.VISIBLE
            "/ ${product.stock}".also { binding.stockAvailable.text = it }
            binding.stockAvailable.visibility = View.VISIBLE
            binding.disp.visibility = View.VISIBLE
            binding.agr.visibility = View.VISIBLE
            binding.stock.visibility = View.GONE
            binding.disponible.visibility = View.GONE
            binding.layoutProductList.setBackgroundColor(greenCardColor)
        } else {
            binding.stock.text = product.stock.toString()
            binding.stock.visibility = View.VISIBLE
            binding.disponible.visibility = View.VISIBLE
            binding.stockAvailable.visibility = View.GONE
            binding.stockEdited.text = ""
            binding.stockEdited.visibility = View.GONE
            binding.disp.visibility = View.GONE
            binding.agr.visibility = View.GONE
            binding.layoutProductList.background = blueCardBackground
        }
    }

    private fun onClickViewListener(product: Product) {
        if (product.stock == 0) {
            Toast.makeText(itemView.context, "No hay stock disponible", Toast.LENGTH_SHORT).show()
            return
        }
        binding.editStock.visibility = View.VISIBLE
        binding.editStock.requestFocus()
        binding.editStock.dispatchTouchEvent(downEvent)
        binding.editStock.dispatchTouchEvent(upEvent)
        binding.editStock.setText(if (product.stockEdited == 0) "" else product.stockEdited.toString())
        binding.layoutProductList.setBackgroundColor(graySelectedCardColor)
        binding.stock.visibility = View.GONE
        binding.disponible.visibility = View.GONE
        binding.stockAvailable.visibility = View.GONE
        binding.stockEdited.visibility = View.GONE
        binding.disp.visibility = View.GONE
        binding.agr.visibility = View.GONE
    }

    private val onEditorActionListener = TextView.OnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE
            || actionId == EditorInfo.IME_ACTION_NEXT
            || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER
        ) {
            this@ProductsViewHolder.closeKeyboard()

            if (!v.text.isNullOrEmpty() && product.stock >= v.text.toString().toInt()) {

                binding.editStock.visibility = View.GONE
                product.edited = v.text.toString().toInt() > 0
                product.stockEdited = v.text.toString().toInt()
                setStyleEdited(product)

                return@OnEditorActionListener true
            } else {
                binding.editStock.visibility = View.GONE
                setStyleEdited(product)
                Toast.makeText(itemView.context, "No se puede ingresar una cantidad mayor a la disponible", Toast.LENGTH_LONG).show()
                return@OnEditorActionListener true
            }
        }
        false
    }

    private fun closeKeyboard() {
        val manager = itemView.context.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager?

        manager?.hideSoftInputFromWindow(
            itemView.windowToken, 0
        )
    }
}