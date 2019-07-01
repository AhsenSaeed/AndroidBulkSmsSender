package spartons.com.prosmssenderapp.di.components

import dagger.Subcomponent
import spartons.com.prosmssenderapp.activities.main.fragments.SplashScreenFragment
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.ui.HistoryFragment
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.settings.ui.SettingsFragment
import spartons.com.prosmssenderapp.di.scopes.FragmentScope


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@FragmentScope
@Subcomponent
interface FragmentComponent {

    fun inject(splashScreenFragment: SplashScreenFragment)

    fun inject(splashScreenFragment: HistoryFragment)

    fun inject(settingsFragment: SettingsFragment)
}