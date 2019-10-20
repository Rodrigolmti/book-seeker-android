package com.ciandt.book.seeker.util

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment

fun View.getSizeInDp(metric: Int): Int {
    val resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        getResources().getDimension(metric),
        resources.displayMetrics
    ).toInt()
}

fun TextView.safeSetTextAppearance(resId: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        setTextAppearance(context, resId)
    } else {
        setTextAppearance(resId)
    }
}

fun View.hideSoftInput() {
    (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
        windowToken, 0
    )
}

fun Fragment.hideSoftInput() {
    view?.hideSoftInput()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}