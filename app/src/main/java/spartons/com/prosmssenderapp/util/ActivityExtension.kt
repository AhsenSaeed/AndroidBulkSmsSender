package spartons.com.prosmssenderapp.util

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.enums.TypeFaceEnum


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

fun Activity.launch(
    requestCode: Int = -1,
    options: Bundle? = null,
    intent: Intent
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        startActivityForResult(intent, requestCode, options)
    else
        startActivityForResult(intent, requestCode)
}

fun Activity.getMutedColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Activity.getResourceString(@StringRes stringResource: Int): String =
    resources.getString(stringResource)

fun Activity.isHasPermission(permission: String): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        applicationContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    else true
}

fun Activity.askPermission(permissions: Array<out String>, @IntRange(from = 0) requestCode: Int) =
    ActivityCompat.requestPermissions(this, permissions, requestCode)

fun FragmentActivity.addFragment(fragment: Fragment, @IdRes containerId: Int) {
    supportFragmentManager.beginTransaction().apply {
        add(containerId, fragment)
        commit()
    }
}

fun FragmentActivity.replaceFragment(fragment: Fragment, @IdRes containerId: Int) {
    supportFragmentManager.beginTransaction().apply {
        replace(containerId, fragment)
        commit()
    }
}

fun Activity.getTypeface(typeFaceEnum: TypeFaceEnum): Typeface {
    return Typeface.createFromAsset(assets, typeFaceEnum.value)
}

fun Activity.snackbar(view: View, title: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(view, title, duration).apply {
        getView().setBackgroundColor(applicationContext.getMutedColor(R.color.colorPrimaryText))
        val textView = getView().findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(applicationContext.getMutedColor(R.color.colorWhite))
        textView.text = title
        show()
    }
}

fun Activity.fullScreenWindow() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

fun Activity.clearWindowsFlag() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}