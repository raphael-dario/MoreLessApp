package com.labstyle.darioandroid.morelessapp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.labstyle.darioandroid.morelessapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val longText = "Insulin is released into the blood by beta cells (β-cells), found in the islets of Langerhans in the pancreas, in response to rising levels of blood glucose, typically after eating. Insulin is used by about two-thirds of the body's cells to absorb glucose from the blood for use as fuel, for conversion to other needed molecules, or for storage. Lower glucose levels result in decreased insulin release from the beta cells and in the breakdown of glycogen to glucose. This process is mainly controlled by the hormone glucagon, which acts in the opposite manner to insulin."
        val shortText = "this is a short text less than 4 lines this is a short text less than 4 lines"

        binding.lifecycleOwner = this
        binding.contentText = longText

        val tvFull = binding.tvFull
        val tvLess = binding.tvLess
        val tvReadMore = binding.tvReadMore
        val tvReadLess = binding.tvReadLess

        binding.collapseHandler = object : ICollapseTextHandler {
            override fun onLessThan() {
                tvFull.visibility = View.VISIBLE
                tvLess.visibility = View.GONE
                tvReadMore.visibility = View.GONE
                tvReadLess.visibility = View.GONE
            }

            override fun onGreaterThan() {
                tvFull.visibility = View.GONE
                tvLess.visibility = View.VISIBLE
                tvReadMore.visibility = View.VISIBLE
                tvReadLess.visibility = View.GONE
            }
        }

        tvReadMore.setOnClickListener {
            tvFull.visibility = View.VISIBLE
            tvLess.visibility = View.GONE
            tvReadMore.visibility = View.GONE
            tvReadLess.visibility = View.VISIBLE
        }
        tvReadLess.setOnClickListener {
            tvFull.visibility = View.GONE
            tvLess.visibility = View.VISIBLE
            tvReadMore.visibility = View.VISIBLE
            tvReadLess.visibility = View.GONE
        }
    }
}

@BindingAdapter("collapseText", "collapseHandler", requireAll = true)
fun TextView.setCollapsable(content: String, collapseHandler: ICollapseTextHandler) {
    Log.d("rafff", "setCollapsable")
    this.text = content

    this.post {
        val count = this.lineCount
        Log.d("rafff", "post lineCount $count")
        if (lineCount >= 5) {
            collapseHandler.onGreaterThan()
        } else {
            collapseHandler.onLessThan()
        }
    }
}