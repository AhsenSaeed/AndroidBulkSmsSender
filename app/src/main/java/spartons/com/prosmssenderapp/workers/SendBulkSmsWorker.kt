package spartons.com.prosmssenderapp.workers

import android.app.Activity.RESULT_OK
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.telephony.SmsManager
import android.telephony.SmsManager.RESULT_ERROR_GENERIC_FAILURE
import android.telephony.SmsManager.RESULT_ERROR_NULL_PDU
import androidx.work.Data
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import spartons.com.prosmssenderapp.helper.NotificationHelper
import spartons.com.prosmssenderapp.helper.NotificationIdHelper
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.Companion.BULK_SMS_MESSAGE_DELAY_SECONDS
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.Companion.BULK_SMS_STORE_MESSAGE_HISTORY_FLAG
import spartons.com.prosmssenderapp.roomPersistence.BulkSms
import spartons.com.prosmssenderapp.roomPersistence.BulkSmsDao
import timber.log.Timber
import java.util.*
import javax.inject.Inject


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/27/19}
 */

@Suppress("DEPRECATION")
class SendBulkSmsWorker constructor(context: Context, workerParameters: WorkerParameters) :
    BaseWorker(context, workerParameters) {

    @Volatile
    private var totalSmsContacts: Int = 0

    @Inject
    lateinit var notificationHelper: NotificationHelper
    @Inject
    lateinit var bulkSmsDao: BulkSmsDao
    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    private lateinit var notificationBuilder: Notification.Builder
    private val notificationId = NotificationIdHelper.getId()

    private var smsCounter = 0

    private val pendingIntent =
        PendingIntent.getBroadcast(context, 0, Intent(SENT_SMS_ACTION), 0)

    private val smsManager = SmsManager.getDefault()

    init {
        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (resultCode) {
                    RESULT_OK -> {
                        ++smsCounter
                        if (::notificationBuilder.isInitialized) {
                            notificationBuilder.setProgress(totalSmsContacts, smsCounter, false)
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                                notificationBuilder.setSound(null)
                            notificationHelper.notify(notificationBuilder, notificationId)
                            if (smsCounter == totalSmsContacts) {
                                notificationBuilder.setProgress(0, 0, false)
                                    .setAutoCancel(true)
                                    .setContentTitle("All Sms have been sent.")
                                notificationHelper.notify(notificationBuilder, notificationId)
                            }
                        }
                    }
                    RESULT_ERROR_GENERIC_FAILURE -> {
                        Timber.e("Generic failure cause called")
                    }
                    RESULT_ERROR_NULL_PDU -> {
                        Timber.e("Result error null pdu")
                    }
                }
            }
        }, IntentFilter(SENT_SMS_ACTION))
    }

    companion object {

        private const val SENT_SMS_ACTION = "sent_sms_action"

        private const val BULK_SMS_CONTACT_LIST =
            "spartons.com.prosmssenderapp.workers.SendBulkSmsWorker.BULK_SMS_CONTACT_LIST"
        private const val BULK_SMS_MESSAGE_CONTENT =
            "spartons.com.prosmssenderapp.workers.SendBulkSmsWorker.BULK_SMS_MESSAGE_CONTENT"

        fun constructWorkerParams(contactList: Array<String>, messageContent: String): Data = Data.Builder()
            .putStringArray(BULK_SMS_CONTACT_LIST, contactList)
            .putString(BULK_SMS_MESSAGE_CONTENT, messageContent)
            .build()
    }

    override suspend fun doWork(): Result {
        getWorkerComponent().inject(this)
        val contactList = inputData.getStringArray(BULK_SMS_CONTACT_LIST) ?: emptyArray()
        val messageContent = inputData.getString(BULK_SMS_MESSAGE_CONTENT) ?: ""
        totalSmsContacts = contactList.size
        notificationBuilder = notificationHelper.getSmsSendingNotificationBuilder()
        notificationBuilder.setProgress(totalSmsContacts, 0, false)
        notificationHelper.notify(notificationBuilder, notificationId)
        val bulkSms = BulkSms(
            smsContacts = contactList,
            smsContent = messageContent,
            startDateTime = Date(System.currentTimeMillis())
        )
        val smsDelayValue = (sharedPreferenceHelper.getInt(BULK_SMS_MESSAGE_DELAY_SECONDS).toLong()) * 1000
        for (singleContact in contactList) {
            smsManager.sendTextMessage(singleContact, null, messageContent, pendingIntent, null)
            delay(smsDelayValue)
        }
        if (sharedPreferenceHelper.getBoolean(BULK_SMS_STORE_MESSAGE_HISTORY_FLAG)) {
            bulkSms.endDateTime = Date(System.currentTimeMillis())
            bulkSmsDao.insert(bulkSms)
        }
        return Result.success()
    }
}