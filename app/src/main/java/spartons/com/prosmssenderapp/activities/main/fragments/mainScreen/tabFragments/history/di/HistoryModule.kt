package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.viewModel.HistoryFragmentViewModel
import spartons.com.prosmssenderapp.backend.MyCustomApplication


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 10/27/19}
 */

val historyModule = module {

    viewModel {
        HistoryFragmentViewModel(
            androidApplication() as MyCustomApplication,
            get(),
            get()
        )
    }

}