package spartons.com.prosmssenderapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

abstract class BaseFragment : Fragment() {

    @LayoutRes
    abstract fun getLayoutResId(): Int

    abstract fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(getLayoutResId(), container, false)
        initViews(view)
        inOnCreateView(view, container, savedInstanceState)
        return view
    }

    protected  open fun initViews(view: View) {}
}