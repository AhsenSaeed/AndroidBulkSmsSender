package spartons.com.prosmssenderapp.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


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

fun Context.launch(
    options: Bundle? = null,
    intent: Intent
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        startActivity(intent, options)
    else
        startActivity(intent)
}

fun Context.getMutedColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Activity.getMutedColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Fragment.getMutedColor(@ColorRes color: Int) = requireActivity().getMutedColor(color)

fun Fragment.getResourceString(@StringRes stringResource: Int): String =
    requireActivity().getResourceString(stringResource)

fun Activity.getResourceString(@StringRes stringResource: Int): String = resources.getString(stringResource)

fun Context.getResourceString(@StringRes stringResource: Int): String = resources.getString(stringResource)

fun Activity.isHasPermission(permission: String): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        applicationContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    else true
}

fun Activity.askPermission(permissions: Array<out String>, @IntRange(from = 0) requestCode: Int) =
    ActivityCompat.requestPermissions(this, permissions, requestCode)