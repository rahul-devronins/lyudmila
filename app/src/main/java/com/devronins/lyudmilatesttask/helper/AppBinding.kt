package com.devronins.lyudmilatesttask.helper

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devronins.lyudmilatesttask.R
import com.devronins.lyudmilatesttask.adapter.AppRecyclerAdapter
import com.devronins.lyudmilatesttask.listener.AdapterListener
import java.util.*


@BindingAdapter("observableList", "layout", "type", "recyclerListener")
fun <T> setRecyclerAdapter(
    view: RecyclerView,
    items: ArrayList<T>?,
    layout: Int,
    type: String,
    recyclerListener: AdapterListener?
) {

    when (type) {
        "linear" -> {
            view.layoutManager = LinearLayoutManager(view.context)
        }
        "linear_horizontal" -> {
            val manager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            view.layoutManager = manager
        }
        "ignore" -> {
            // layout manager applied in layout itself
        }
        else -> {
            view.layoutManager = GridLayoutManager(view.context, 2)
        }
    }

    view.itemAnimator = DefaultItemAnimator()
    view.adapter = AppRecyclerAdapter(layout, items, recyclerListener)

}

@BindingAdapter("priceText")
fun setPriceText(view: AppCompatTextView, price: Int?) {
    price?.run {
        val context = view.context
        val ssb = SpannableStringBuilder("")
        val price = "$$price"

        val blueColor = ContextCompat.getColor(
            context,
            R.color.color3173C2
        )
        val grayColor = ContextCompat.getColor(
            context,
            R.color.color3B3B3B
        )

        setColoredSpannable(ssb, price, blueColor)
        ssb.append("\n")
        setColoredSpannable(ssb, "/${context.getString(R.string.day)}", grayColor)
        view.text = ssb
    } ?: run {
        view.text = "N/A"
    }
}

fun setColoredSpannable(
    ssb: SpannableStringBuilder,
    text: String,
    color: Int
) {
    val foregroundWhiteColorSpan = ForegroundColorSpan(color)
    val spannableString = SpannableString(text)
    spannableString.setSpan(
        foregroundWhiteColorSpan,
        0,
        spannableString.length,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    ssb.append(spannableString)
    ssb.append(" ")
}

@BindingAdapter("bgUsingName")
fun setBgUsingName(view: AppCompatImageView, title: String?) {
    title?.run {
        val name = "@drawable/ic_" + lowercase().trim().replace(" ", "_")
        val imageResource = view.context.resources.getIdentifier(name, null, view.context.packageName)
        if (imageResource != 0) {
            val res: Drawable? = ContextCompat.getDrawable(view.context, imageResource)
            res?.run {
                view.setBackgroundDrawable(res)
            } ?: run {
                view.setBackgroundDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_gym_rebel))
            }
        } else {
            view.setBackgroundDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_gym_rebel))
        }
    }
}

@BindingAdapter("srcUsingName")
fun setSrcUsingName(view: AppCompatImageView, title: String?) {
    title?.run {
        val name = "@drawable/ic_" + lowercase().trim().replace(" ", "_")
        val imageResource = view.context.resources.getIdentifier(name, null, view.context.packageName)
        if (imageResource != 0) {
            val res: Drawable? = ContextCompat.getDrawable(view.context, imageResource)
            res?.run {
                view.setImageDrawable(res)
            } ?: run {
                view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_gym_rebel))
            }
        } else {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_gym_rebel))
        }
    }
}

