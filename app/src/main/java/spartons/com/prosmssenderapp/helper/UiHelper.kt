package spartons.com.prosmssenderapp.helper

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.files.FileFilter
import com.afollestad.materialdialogs.files.fileChooser
import com.google.android.material.snackbar.Snackbar
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.enums.TypeFaceEnum
import spartons.com.prosmssenderapp.util.getMutedColor
import java.io.File
import javax.inject.Inject

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

class UiHelper @Inject constructor(private val applicationContext: Context) {

    private val assetManager = applicationContext.assets

    /**
     * check which type is requested as an enum parameter and return the actual typeface.
     *
     * @param typeFaceEnum enum type like may be Kingthings_Foundation.ttf
     * @return return a typeface which is requested by as an enum parameter.
     */

    fun getTypeFace(typeFaceEnum: TypeFaceEnum): Typeface {
        return Typeface.createFromAsset(assetManager, typeFaceEnum.value)
    }

    fun addFragment(fragment: Fragment, fragmentManager: FragmentManager, containerId: Int) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(containerId, fragment)
        fragmentTransaction.commit()
    }

    fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager, containerId: Int) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment)
        fragmentTransaction.commit()
    }

    fun showSnackBar(view: View, title: String, duration: Int = Snackbar.LENGTH_LONG) {
        runCatching {
            val snackBar = Snackbar.make(view, title, duration)
            val view1 = snackBar.view
            view1.setBackgroundColor(applicationContext.getMutedColor(R.color.colorPrimaryText))
            val textView = view1.findViewById<TextView>(R.id.snackbar_text)
            textView.setTextColor(applicationContext.getMutedColor(R.color.colorWhite))
            textView.text = title
            snackBar.show()
        }
    }

    fun showSimpleMaterialDialog(
        classingActivity: Activity,
        @StringRes title: Int,
        @StringRes content: Int,
        @DrawableRes icon: Int? = null,
        positiveText: String = "OK",
        positiveButtonClickListener: () -> Unit
    ) {
        MaterialDialog(classingActivity).show {
            noAutoDismiss()
            icon(icon)
            title(title)
            message(content)
            cornerRadius(16f)
            positiveButton(text = positiveText) {
                it.dismiss()
                positiveButtonClickListener.invoke()
            }
        }
    }

    fun showSelectContactFileDialog(activity: Activity, selectedFileClosure: (File) -> Unit) {
        val myFilter: FileFilter = { it.name.endsWith(".txt") || it.isDirectory }
        MaterialDialog(activity).show {
            cornerRadius(16f)
            noAutoDismiss()
            title(text = "Choose Contact file with .txt extension")
            positiveButton(text = "SELECT")
            negativeButton(text = "CANCEL") { materialDialog ->
                materialDialog.dismiss()
            }
            fileChooser(filter = myFilter) { dialog, file ->
                dialog.dismiss()
                selectedFileClosure.invoke(file)
            }
        }
    }

    fun showBottomSheetDialog(
        activity: Activity,
        @StringRes content: Int,
        @StringRes title: Int,
        positiveButtonText: String = "Submit",
        negativeButtonText: String = "Cancel",
        positiveButtonClickListener: () -> Unit
    ) {
        MaterialDialog(activity, BottomSheet(layoutMode = LayoutMode.MATCH_PARENT)).show {
            title(title)
            cornerRadius(16f)
            noAutoDismiss()
            message(content)
            positiveButton(text = positiveButtonText) {
                it.dismiss()
                positiveButtonClickListener.invoke()
            }
            negativeButton(text = negativeButtonText) {
                it.dismiss()
            }
        }
    }
}