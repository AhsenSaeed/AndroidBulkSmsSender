package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.viewHolder.BulkSmsViewHolder
import spartons.com.prosmssenderapp.roomPersistence.BulkSms


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class BulkSmsAdapter(
    private val bulkSmsDeleteListener: IBulkSmsDeleteListener
) : ListAdapter<BulkSms, BulkSmsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BulkSmsViewHolder.create(parent)

    override fun onBindViewHolder(holder: BulkSmsViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.setDeleteClickClosure {
            val deletedBulkSms = getItem(it)
            bulkSmsDeleteListener.onBulkSmsAction(ClickType.DELETE, deletedBulkSms)
        }
        holder.setCancelClickClosure {
            val cancelledBulkSms = getItem(it)
            bulkSmsDeleteListener.onBulkSmsAction(ClickType.CANCEL, cancelledBulkSms)
        }
    }

    private companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BulkSms>() {
            override fun areItemsTheSame(oldItem: BulkSms, newItem: BulkSms) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BulkSms, newItem: BulkSms) =
                oldItem.startDateTime == newItem.startDateTime && oldItem.endDateTime == newItem.endDateTime && oldItem.smsContent == newItem.smsContent && oldItem.smsContacts == newItem.smsContacts
        }
    }

    enum class ClickType {
        CANCEL,
        DELETE
    }

    @FunctionalInterface
    interface IBulkSmsDeleteListener {
        fun onBulkSmsAction(clickType: ClickType, bulkSms: BulkSms)
    }

}