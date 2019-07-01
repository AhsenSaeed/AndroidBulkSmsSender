package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history

import androidx.recyclerview.widget.DiffUtil
import spartons.com.prosmssenderapp.roomPersistence.BulkSms


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class BulkSmsDiffCallback(private val oldBulkSmsList: List<BulkSms>, private val newBulkSmsList: List<BulkSms>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldBulkSmsItem = oldBulkSmsList[oldItemPosition]
        val newBulkSmsItem = newBulkSmsList[newItemPosition]
        return oldBulkSmsItem.id == newBulkSmsItem.id
    }

    override fun getOldListSize() = oldBulkSmsList.size

    override fun getNewListSize() = newBulkSmsList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldBulkSmsItem = oldBulkSmsList[oldItemPosition]
        val newBulkSmsItem = newBulkSmsList[newItemPosition]
        return oldBulkSmsItem.startDateTime == newBulkSmsItem.startDateTime && oldBulkSmsItem.endDateTime == newBulkSmsItem.endDateTime && oldBulkSmsItem.smsContent == newBulkSmsItem.smsContent
    }
}