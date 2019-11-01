package spartons.com.prosmssenderapp.activities.sendBulkSms.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import spartons.com.prosmssenderapp.activities.sendBulkSms.viewHolder.PreviewContactViewHolder


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/26/19}
 */

class PreviewContactAdapter :
    ListAdapter<String, PreviewContactViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PreviewContactViewHolder.create(parent)

    override fun onBindViewHolder(holder: PreviewContactViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.setClickListener {
            val tempList = currentList.toMutableList()
            tempList.removeAt(it)
            submitList(tempList)
        }
    }

    private companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        }
    }
}
