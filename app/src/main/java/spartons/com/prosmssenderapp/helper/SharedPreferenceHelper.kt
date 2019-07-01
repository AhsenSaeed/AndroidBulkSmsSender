package spartons.com.prosmssenderapp.helper

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/30/19}
 */

class SharedPreferenceHelper @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val BULK_SMS_STORE_MESSAGE_HISTORY_FLAG =
            "spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.store_message_history_flag"
        const val BULK_SMS_MESSAGE_DELAY_SECONDS =
            "spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.message_delay_seconds"
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit {
            putBoolean(key, value)
        }
    }

    fun getBoolean(key: String) = sharedPreferences.getBoolean(key, true)

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit {
            putInt(key, value)
        }
    }

    fun getInt(key: String) =
        sharedPreferences.getInt(key, 2)
}