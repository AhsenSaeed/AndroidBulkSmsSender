package spartons.com.prosmssenderapp.activities.sendBulkSms.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import spartons.com.prosmssenderapp.R


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/26/19}
 */

class SendBulkSmsContactAdapter(
    private val contactList: MutableList<String> = mutableListOf(),
    context: Context
) : RecyclerView.Adapter<SendBulkSmsContactAdapter.MyViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    fun feedItems(items: List<String>) {
        this.contactList.addAll(items)
        notifyDataSetChanged()
    }

    fun isContactListEmpty() = contactList.isEmpty()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(layoutInflater.inflate(R.layout.sms_contact_user_single_view, parent, false))

    override fun getItemCount() = contactList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contact = contactList[position]
        holder.smsContactUserSinglePhoneTextView.text = contact
        holder.setClickListener { position1 ->
            contactList.removeAt(position1)
            notifyDataSetChanged()
        }
    }

    fun contactItems(): List<String> = contactList

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var clickClosure: (Int) -> Unit
        val smsContactUserSinglePhoneTextView: TextView = itemView.findViewById(R.id.smsContactUserSinglePhoneTextView)
        private val smsContactUserSingleCancelImageView: ImageView =
            itemView.findViewById(R.id.smsContactUserSingleCancelImageView)

        init {
            smsContactUserSingleCancelImageView.setOnClickListener {
                clickClosure.invoke(adapterPosition)
            }
        }

        fun setClickListener(clickClosure: (Int) -> Unit) {
            this.clickClosure = clickClosure
        }
    }
}