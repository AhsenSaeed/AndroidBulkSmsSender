package spartons.com.prosmssenderapp.roomPersistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

@Entity(tableName = "bulk_sms")
class BulkSms(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bulk_sms_id")
    val id: Int = 0,
    @ColumnInfo(name = "sms_contacts")
    val smsContacts: Array<String>,
    @ColumnInfo(name = "sms_content")
    val smsContent: String,
    @ColumnInfo(name = "start_date_time")
    val startDateTime: Date
) {

    @ColumnInfo(name = "end_date_time")
    lateinit var endDateTime: Date

    override fun toString(): String {
        return "BulkSms(id=$id, smsContacts=${Arrays.toString(smsContacts)}, smsContent='$smsContent', startDateTime=$startDateTime, endDateTime=$endDateTime)"
    }
}