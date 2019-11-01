package spartons.com.prosmssenderapp.roomPersistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import spartons.com.prosmssenderapp.activities.sendBulkSms.data.SmsContact


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

@Entity(tableName = "bulk_sms")
class BulkSms(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bulk_sms_id")
    val id: Long = 0,
    @ColumnInfo(name = "sms_contacts")
    val smsContacts: List<SmsContact>,
    @ColumnInfo(name = "sms_content")
    val smsContent: String,
    @ColumnInfo(name = "start_date_time")
    val startDateTime: Long,
    @ColumnInfo(name = "end_date_time")
    val endDateTime: Long? = null,
    @ColumnInfo(name = "carrier_name")
    val carrierName: String,
    @ColumnInfo(name = "status")
    val bulkSmsStatus: BulkSmsStatus = BulkSmsStatus.IN_PROGRESS
) {
    override fun toString(): String {
        return "BulkSms(id=$id, smsContacts=$smsContacts, smsContent='$smsContent', startDateTime=$startDateTime, endDateTime=$endDateTime, carrierName='$carrierName', bulkSmsStatus=$bulkSmsStatus)"
    }
}