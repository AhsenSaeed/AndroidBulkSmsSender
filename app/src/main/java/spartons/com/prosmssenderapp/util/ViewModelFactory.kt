package spartons.com.prosmssenderapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.viewModel.HistoryFragmentViewModel
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.settings.viewModel.SettingsFragmentViewModel
import spartons.com.prosmssenderapp.roomPersistence.BulkSmsDao

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val bulkSmsDao: BulkSmsDao) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HistoryFragmentViewModel::class.java) -> HistoryFragmentViewModel(bulkSmsDao) as T
            modelClass.isAssignableFrom(SettingsFragmentViewModel::class.java) -> SettingsFragmentViewModel(bulkSmsDao) as T
            else -> throw IllegalArgumentException("Unknown view model class")
        }
    }
}