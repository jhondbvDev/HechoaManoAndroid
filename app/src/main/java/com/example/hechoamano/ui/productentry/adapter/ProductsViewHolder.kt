package com.example.hechoamano.ui.productentry.adapter

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
    private val blueCardColor = ContextCompat.getColor(this.itemView.context, R.color.blue_cards)
    private val graySelectedCardColor = ContextCompat.getColor(this.itemView.context, R.color.gray_selected_card)


    fun render(product: Product) {
        "${product.family} ${product.subfamily}".also { binding.name.text = it }
        binding.region.text = product.region
        binding.size.text = product.size
        binding.type.text = product.type
        setStyleEdited(product)
        itemView.setOnClickListener {
            onClickViewListener(product)
        }
        this.product = product

        binding.editStock.setOnEditorActionListener(onEditorActionListener)
    }

    private fun setStyleEdited(product: Product) {
        if(product.edited){
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
            binding.stock.text = product.stock
            binding.stock.visibility = View.VISIBLE
            binding.disponible.visibility = View.VISIBLE
            binding.stockAvailable.visibility = View.GONE
            binding.stockEdited.visibility = View.GONE
            binding.disp.visibility = View.GONE
            binding.agr.visibility = View.GONE
            binding.layoutProductList.setBackgroundColor(blueCardColor)
        }
    }

    private fun onClickViewListener(product: Product) {
        binding.editStock.visibility = View.VISIBLE
        binding.editStock.requestFocus()
        binding.editStock.dispatchTouchEvent(downEvent)
        binding.editStock.dispatchTouchEvent(upEvent)
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

            if (product.stock.toInt() >= v.text.toString().toInt()){

                binding.editStock.visibility = View.GONE
                product.edited = v.text.toString().toInt() > 0
                product.stockEdited = v.text.toString().toInt()
                setStyleEdited(product)

                return@OnEditorActionListener true
            }
        }
        false
    }
}