package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.AboutFragment
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.ui.HistoryFragment
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.settings.ui.SettingsFragment


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class MainViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mainScreenFragments = listOf(
        SettingsFragment.getInstance(),
        HistoryFragment.getInstance(),
        AboutFragment.getInstance()
    )

    private val mainScreenFragmentTitle = listOf(
        "Settings",
        "History",
        "About"
    )

    override fun getItem(position: Int) = mainScreenFragments[position]

    override fun getCount() = mainScreenFragments.size

    override fun getPageTitle(position: Int) = mainScreenFragmentTitle[position]
}
