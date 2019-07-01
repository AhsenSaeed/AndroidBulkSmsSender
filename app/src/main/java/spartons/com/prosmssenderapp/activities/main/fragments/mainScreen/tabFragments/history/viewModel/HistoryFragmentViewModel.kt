package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import spartons.com.prosmssenderapp.roomPersistence.BulkSms
import spartons.com.prosmssenderapp.roomPersistence.BulkSmsDao

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class HistoryFragmentViewModel(private val bulkSmsDao: BulkSmsDao) : ViewModel() {

    private val job = SupervisorJob()

    private val coroutineContext = Dispatchers.Default + job

    val allTasks: LiveData<List<BulkSms>> = bulkSmsDao.all()

    fun deleteBulkSms(bulkSms: BulkSms) {
        viewModelScope.launch(coroutineContext) {
            bulkSmsDao.delete(bulkSms)
        }
    }
}