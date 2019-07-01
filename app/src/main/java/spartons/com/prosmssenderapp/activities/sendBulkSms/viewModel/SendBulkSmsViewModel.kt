package spartons.com.prosmssenderapp.activities.sendBulkSms.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.*
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.util.Event
import spartons.com.prosmssenderapp.workers.SendBulkSmsWorker
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/26/19}
 */

class SendBulkSmsViewModel : ViewModel() {

    private val job = SupervisorJob()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val coroutineContext = Dispatchers.Default + exceptionHandler + job

    private val _uiState = MutableLiveData<SendBulkSmsUiModel>()

    val uiState: LiveData<SendBulkSmsUiModel> = _uiState

    private suspend fun emitUiState(
        showProgress: Boolean = false,
        contactList: Event<List<String>>? = null,
        showMessage: Event<Int>? = null,
        hideProgressBar: Boolean = false
    ) = withContext(Dispatchers.Main) {
        val uiModel =
            SendBulkSmsUiModel(
                showProgress,
                contactList,
                showMessage,
                hideProgressBar
            )
        _uiState.value = uiModel
    }

    fun handleSelectedFile(selectedFile: File) {
        viewModelScope.launch(coroutineContext) {
            emitUiState(showProgress = true)
            BufferedReader(FileReader(selectedFile)).use {
                val filteredContactList = it.readLines()
                    .filter { contactNumber ->
                        contactNumber.length > 6
                    }
                emitUiState(hideProgressBar = true)
                if (filteredContactList.isNotEmpty())
                    emitUiState(contactList = Event(filteredContactList))
                else
                    emitUiState(showMessage = Event(R.string.the_selected_file_is_empty))
            }
        }
    }

    fun sendBulkSms(contactList: Array<String>, smsContent: String) {
        val bulkSmsWorker = OneTimeWorkRequestBuilder<SendBulkSmsWorker>()
            .setInputData(SendBulkSmsWorker.constructWorkerParams(contactList, smsContent))
            .build()
        WorkManager.getInstance().enqueue(bulkSmsWorker)
    }
}

data class SendBulkSmsUiModel(
    val showProgress: Boolean,
    val contactList: Event<List<String>>?,
    val showMessage: Event<Int>?,
    val hideProgressBar: Boolean
)