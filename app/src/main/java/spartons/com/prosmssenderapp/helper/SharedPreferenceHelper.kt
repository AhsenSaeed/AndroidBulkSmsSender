package spartons.com.prosmssenderapp.helper

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/30/19}
 */

class SharedPreferenceHelper(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val BULK_SMS_MESSAGE_DELAY_SECONDS =
            "spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.message_delay_seconds"
        const val BULKS_SMS_PREVIOUS_WORKER_ID =
            "spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.previous_worker_id"
        const val BULK_SMS_MUTIPLE_NUMBERS_ASK_BEFORE_FLAG =
            "spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.multiple_numbers_ask_before_flag"
        const val BULK_SMS_PREFERRED_CARRIER_NUMBER =
            "spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.preferred_carrier_number"
        const val BULK_SMS_PREFERRED_MULTIPLE_CARRIER_NUMBER_FLAG =
            "spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.preferred_multiple_carrier_number_flag"
    }

    fun put(key: String, value: Boolean) {
        sharedPreferences.edit {
            putBoolean(key, value)
        }
    }

    fun getBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)

    fun put(key: String, value: Int) {
        sharedPreferences.edit {
            putInt(key, value)
        }
    }

    fun put(key: String, value: String?) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun getInt(key: String): Int =
        sharedPreferences.getInt(key, 4)

    fun getString(key: String): String? =
        sharedPreferences.getString(key, null)
}
