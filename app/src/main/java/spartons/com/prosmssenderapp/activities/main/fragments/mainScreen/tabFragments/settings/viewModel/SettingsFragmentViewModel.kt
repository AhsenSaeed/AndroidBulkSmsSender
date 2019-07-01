package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.settings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import spartons.com.prosmssenderapp.roomPersistence.BulkSmsDao


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class SettingsFragmentViewModel(private val bulkSmsDao: BulkSmsDao) : ViewModel() {

    private val job = SupervisorJob()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val coroutineContext = Dispatchers.Default + job + exceptionHandler

    fun deleteAllBulkSms() {
        viewModelScope.launch(coroutineContext) {
            bulkSmsDao.nukeSmsTable()
        }
    }
}