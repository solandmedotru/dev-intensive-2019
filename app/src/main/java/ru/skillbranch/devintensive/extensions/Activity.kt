package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.graphics.Rect

fun Activity.hideKeyboard() {
    var view = this.currentFocus
    if (view != null) {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen() : Boolean {
    val rect = Rect()
    this.window.decorView.getWindowVisibleDisplayFrame(rect)
    val screenHeight = this.window.decorView.height
    val heightDifference = screenHeight - (rect.bottom - rect.top)
    return heightDifference > screenHeight / 3
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()