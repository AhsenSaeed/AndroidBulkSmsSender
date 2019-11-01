package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.viewHolder

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.color
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sms_history_single_view.*
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.roomPersistence.BulkSms
import spartons.com.prosmssenderapp.roomPersistence.BulkSmsStatus
import spartons.com.prosmssenderapp.util.getMutedColor
import spartons.com.prosmssenderapp.util.getResourceString
import spartons.com.prosmssenderapp.util.toDate
import java.text.SimpleDateFormat


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 10/27/19}
 */

class BulkSmsViewHolder constructor(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    private lateinit var deleteClickClosure: (Int) -> Unit
    private lateinit var cancelClickClosure: (Int) -> Unit

    init {
        smsHistorySingleViewDeleteImageView.setOnClickListener {
            deleteClickClosure.invoke(adapterPosition)
        }
        smsHistorySingleViewCancelImageView.setOnClickListener {
            cancelClickClosure.invoke(adapterPosition)
        }
    }

    fun setDeleteClickClosure(clickClosure: (Int) -> Unit) {
        this.deleteClickClosure = clickClosure
    }

    fun setCancelClickClosure(clickClosure: (Int) -> Unit) {
        this.cancelClickClosure = clickClosure
    }

    fun bind(bulkSms: BulkSms) {
        val context = containerView.context
        val contactsSms = bulkSms.smsContacts
        val textContent = context.getResourceString(R.string.no_of_sms_sent)
            .plus(" ${contactsSms.filter { it.isSent }.count()}/${contactsSms.count()}")
        smsHistorySingleViewSmsCountTextView.text = textContent
        if (bulkSms.bulkSmsStatus == BulkSmsStatus.COMPLETE || bulkSms.bulkSmsStatus == BulkSmsStatus.CANCELLED) {
            smsHistorySingleViewDeleteImageView.visibility = VISIBLE
            smsHistorySingleViewCancelImageView.visibility = GONE
        } else if (bulkSms.bulkSmsStatus == BulkSmsStatus.IN_PROGRESS) {
            smsHistorySingleViewDeleteImageView.visibility = GONE
            smsHistorySingleViewCancelImageView.visibility = VISIBLE
        }
        val contentTextViewSpans = SpannableStringBuilder()
            .bold {
                color(context.getMutedColor(R.color.colorSecondaryText)) {
                    append(context.getResourceString(R.string.sms_content_colon))
                }
            }.color(context.getMutedColor(R.color.colorPrimaryText)) {
                append(" ".plus(bulkSms.smsContent))
            }
        if (bulkSms.bulkSmsStatus == BulkSmsStatus.CANCELLED)
            smsHistorySingleViewProcessCancelledTextView.visibility = VISIBLE
        smsHistorySingleViewSmsContentTextView.setText(
            contentTextViewSpans,
            TextView.BufferType.SPANNABLE
        )
        val startTimeDateFormat = dateFormatter.format(bulkSms.startDateTime.toDate())
        smsHistorySingleViewSmsStartTextView.text = startTimeDateFormat
        val endDatetime = bulkSms.endDateTime
        if (endDatetime != null) {
            if (bulkSms.bulkSmsStatus == BulkSmsStatus.CANCELLED)
                smsHistorySingleViewEndTitleTextView.setText(R.string.cancelled_time)
            val endDateTimeFormat = dateFormatter.format(endDatetime.toDate())
            smsHistorySingleViewSmsEndTextView.text = endDateTimeFormat
        }

        val carrierNameTextViewSpans = SpannableStringBuilder()
            .color(context.getMutedColor(R.color.colorPrimaryText)) {
                append(context.getResourceString(R.string.carrier_number_used_to_send_this_bulk_sms))
            }.bold {
                color(context.getMutedColor(R.color.colorSecondaryText)) {
                    append(":${bulkSms.carrierName}")
                }
            }
        smsHistorySingleViewCarrierNameTextView.setText(
            carrierNameTextViewSpans,
            TextView.BufferType.SPANNABLE
        )

    }

    companion object {

        @SuppressLint("SimpleDateFormat")
        private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        fun create(parent: ViewGroup): BulkSmsViewHolder {
            return BulkSmsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.sms_history_single_view, parent, false)
            )
        }
    }
}