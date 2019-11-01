package spartons.com.prosmssenderapp.util

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 10/31/19}
 */

fun Drawable.applyColor(color: Int) {
    this.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
}