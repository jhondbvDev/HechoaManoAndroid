package com.example.hechoamano.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hechoamano.R

abstract class BaseActionBarActivity : AppCompatActivity() {

    protected fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.show()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> { super.onOptionsItemSelected(item) }
        }
    }
    fun onUnknownError() {
        Toast.makeText(
            this,
            "Ocurrió un error inesperado. Vuelve a intentarlo.",
            Toast.LENGTH_LONG
        ).show()
    }
}