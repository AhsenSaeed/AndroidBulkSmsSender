package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.color
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.BulkSmsDiffCallback
import spartons.com.prosmssenderapp.roomPersistence.BulkSms
import spartons.com.prosmssenderapp.util.getMutedColor
import spartons.com.prosmssenderapp.util.getResourceString
import java.text.SimpleDateFormat


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class AllBulkSmsRecyclerViewAdapter(
    private val bulkSmsList: ArrayList<BulkSms>,
    private val context: Context,
    private val bulkSmsDeleteListener: IBulkSmsDeleteListener
) :
    RecyclerView.Adapter<AllBulkSmsRecyclerViewAdapter.MyViewHolder>() {

    @SuppressLint("SimpleDateFormat")
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(layoutInflater.inflate(R.layout.sms_history_single_view, parent, false))

    override fun getItemCount() = bulkSmsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bulkSms = bulkSmsList[position]
        holder.smsHistorySingleViewSmsCountTextView.text = bulkSms.smsContacts.size.toString()
        val spans = SpannableStringBuilder()
            .bold {
                color(context.getMutedColor(R.color.colorSecondaryText)) {
                    append(context.getResourceString(R.string.sms_content_colon))
                }
            }
            .color(context.getMutedColor(R.color.colorPrimaryText)) {
                append(" ".plus(bulkSms.smsContent))
            }
        holder.smsHistorySingleViewSmsContentTextView.setText(spans, TextView.BufferType.SPANNABLE)
        val startTimeDateFormat = dateFormatter.format(bulkSms.startDateTime)
        holder.smsHistorySingleViewSmsStartTextView.text = startTimeDateFormat
        val endDateTimeFormat = dateFormatter.format(bulkSms.endDateTime)
        holder.smsHistorySingleViewSmsEndTextView.text = endDateTimeFormat
        holder.setClickClosure { position1 ->
            val deletedBulkSms = bulkSmsList[position1]
            bulkSmsDeleteListener.onBulkSmsDelete(deletedBulkSms)
            bulkSmsList.remove(deletedBulkSms)
            notifyDataSetChanged()
        }
    }

    fun updateBulkSmsItems(items: List<BulkSms>) {
        val diffResult = DiffUtil.calculateDiff(
            BulkSmsDiffCallback(this.bulkSmsList, items)
        )
        this.bulkSmsList.clear()
        this.bulkSmsList.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var clickClosure: (Int) -> Unit

        val smsHistorySingleViewSmsCountTextView: TextView =
            itemView.findViewById(R.id.smsHistorySingleViewSmsCountTextView)
        val smsHistorySingleViewSmsContentTextView: TextView =
            itemView.findViewById(R.id.smsHistorySingleViewSmsContentTextView)
        val smsHistorySingleViewSmsStartTextView: TextView =
            itemView.findViewById(R.id.smsHistorySingleViewSmsStartTextView)
        val smsHistorySingleViewSmsEndTextView: TextView =
            itemView.findViewById(R.id.smsHistorySingleViewSmsEndTextView)
        private val smsHistorySingleViewDeleteImageView: ImageView =
            itemView.findViewById(R.id.smsHistorySingleViewDeleteImageView)

        init {
            smsHistorySingleViewDeleteImageView.setOnClickListener {
                clickClosure.invoke(adapterPosition)
            }
        }

        fun setClickClosure(clickClosure: (Int) -> Unit) {
            this.clickClosure = clickClosure
        }
    }

    @FunctionalInterface
    interface IBulkSmsDeleteListener {
        fun onBulkSmsDelete(bulkSms: BulkSms)
    }
}