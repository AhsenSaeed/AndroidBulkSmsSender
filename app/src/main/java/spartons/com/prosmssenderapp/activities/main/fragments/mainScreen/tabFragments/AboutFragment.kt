package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.fragments.BaseFragment


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class AboutFragment : BaseFragment(){

    companion object {
        fun getInstance() = AboutFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_about

    override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {
    }
}