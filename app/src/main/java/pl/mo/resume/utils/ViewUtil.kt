package pl.mo.resume.utils

import android.content.Context
import android.view.View
import pl.mo.resume.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun isNight(context: Context): Boolean {
    return context.resources.getBoolean(R.bool.isNight)
}