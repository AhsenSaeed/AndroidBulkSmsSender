package spartons.com.prosmssenderapp.util

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 10/27/19}
 */

fun Fragment.getMutedColor(@ColorRes color: Int) = requireActivity().getMutedColor(color)

fun Fragment.getResourceString(@StringRes stringResource: Int): String =
    requireActivity().getResourceString(stringResource)
