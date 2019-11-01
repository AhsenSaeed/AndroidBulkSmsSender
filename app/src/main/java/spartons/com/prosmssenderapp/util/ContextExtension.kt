package spartons.com.prosmssenderapp.util

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.*


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 10/27/19}
 */

fun Context.launch(
    options: Bundle? = null, intent: Intent
) {
    startActivity(intent, options)
}

fun Context.toast(content: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, content, length).show()

fun Context.getMutedColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.getResourceString(@StringRes stringResource: Int): String =
    resources.getString(stringResource)

inline fun Context.notificationBuilder(
    channelId: String,
    func: NotificationCompat.Builder.() -> Unit
): NotificationCompat.Builder {
    val builder = NotificationCompat.Builder(this, channelId)
    builder.func()
    return builder
}

inline fun <reified W : ListenableWorker> Context.enqueueWorker(func: OneTimeWorkRequest.Builder.() -> Unit): OneTimeWorkRequest {
    val builder = OneTimeWorkRequestBuilder<W>()
    builder.func()
    return builder.build().also {
        WorkManager.getInstance(this.applicationContext).enqueue(it)
    }
}

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.subscriptionManager: SubscriptionManager
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    get() = getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

val Context.telephoneManager: TelephonyManager
    get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

fun Context.cancelWorker(id: String) {
    WorkManager.getInstance(this.applicationContext).cancelWorkById(UUID.fromString(id))
}

fun Context.getMutedDrawable(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)
