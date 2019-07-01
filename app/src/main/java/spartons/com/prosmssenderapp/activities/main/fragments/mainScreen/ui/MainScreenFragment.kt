package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main_screen.*
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.adapter.MainScreenFragmentPagerAdapter
import spartons.com.prosmssenderapp.fragments.BaseFragment
import spartons.com.prosmssenderapp.util.SendBulkSmsActivityArgs

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

class MainScreenFragment : BaseFragment() {

    private lateinit var mainScreenViewPager: ViewPager
    private lateinit var mainScreenTabLayout: TabLayout

    companion object {
        fun getInstance() = MainScreenFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainScreenSendSmsFloatingActionButton.setOnClickListener {
            SendBulkSmsActivityArgs().launch(requireContext())
        }
    }

    override fun getLayoutResId() = R.layout.fragment_main_screen

    override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {
        initViews(mRootView)
        val adapter = MainScreenFragmentPagerAdapter(requireActivity().supportFragmentManager)
        mainScreenViewPager.adapter = adapter
        mainScreenTabLayout.setupWithViewPager(mainScreenViewPager)
        mainScreenViewPager.currentItem = 1
        mainScreenViewPager.offscreenPageLimit = 2
    }

    private fun initViews(view: View) {
        mainScreenViewPager = view.findViewById(R.id.mainScreenViewPager)
        mainScreenTabLayout = view.findViewById(R.id.mainScreenTabLayout)
    }
}