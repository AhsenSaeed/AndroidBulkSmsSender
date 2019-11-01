package spartons.com.prosmssenderapp.activities.sendBulkSms.data

import kotlinx.serialization.Serializable


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 10/30/19}
 */

fun String.toSmsContact() =
    SmsContact(this)

@Serializable
data class SmsContact(
    val contactNumber: String,
    var isSent: Boolean = false
)
