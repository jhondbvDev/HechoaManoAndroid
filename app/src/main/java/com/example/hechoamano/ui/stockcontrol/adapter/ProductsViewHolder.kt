package com.example.hechoamano.ui.stockcontrol.adapter

import android.os.SystemClock
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R
import com.example.hechoamano.databinding.ItemProductListBinding
import com.example.hechoamano.domain.model.Product

class ProductsViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater)  {
    private lateinit var product: Product
    private val binding = ItemProductListBinding.bind(inflater)
    private val downEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0)
    private val upEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0)
    private val greenCardColor = ContextCompat.getColor(this.itemView.context, R.color.green_cards)
    private val blueCardBackground = ContextCompat.getDrawable(this.itemView.context, R.drawable.button_list_blue)
    private val graySelectedCardColor = ContextCompat.getColor(this.itemView.context, R.color.gray_selected_card)


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

        if(product.subfamily != null && product.size == null && product.type == null){
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

    private fun setVisibility(vararg views: View, visibility: Int) {
        views.forEach { it.visibility = visibility }
    }

    private fun setStyleEdited(product: Product) {
        setVisibility(binding.stock, binding.stockEdited, binding.stockAvailable, binding.disp, binding.agr, binding.disponible, visibility = View.GONE)

        if(product.edited){
            binding.stock.text = product.stockEdited.toString()
            binding.stock.visibility = View.VISIBLE
            binding.disponible.visibility = View.VISIBLE
            binding.layoutProductList.setBackgroundColor(greenCardColor)
        } else {
            binding.stock.text = ""
            binding.layoutProductList.background = blueCardBackground
        }
    }

    private fun onClickViewListener(product: Product) {
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
            || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {

            binding.editStock.visibility = View.GONE
            product.edited = if (v.text.isNotEmpty()) v.text.toString().toInt() > 0 else false
            product.stockEdited = if (v.text.isNotEmpty()) v.text.toString().toInt() else 0
            setStyleEdited(product)

            return@OnEditorActionListener true
        }
        false
    }
}