package br.ufrn.dim0524.hub.utils

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun hideSystemBars(window: Window) {
    val windowInsetsController =
        WindowCompat.getInsetsController(window, window.decorView)

    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
}