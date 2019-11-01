package spartons.com.prosmssenderapp.roomPersistence

import androidx.lifecycle.LiveData
import androidx.room.*
import spartons.com.prosmssenderapp.activities.sendBulkSms.data.SmsContact


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

@Dao
interface BulkSmsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(t: BulkSms): Long

    @Query(value = "select * from bulk_sms order by start_date_time desc")
    fun all(): LiveData<List<BulkSms>>

    @Query(value = "update bulk_sms set end_date_time= :endDateTime, status= :status where bulk_sms_id= :rowId")
    suspend fun update(endDateTime: Long, status: BulkSmsStatus, rowId: Long)

    @Query(value = "update bulk_sms set sms_contacts= :smsContacts where bulk_sms_id= :rowId")
    suspend fun update(smsContacts: List<SmsContact>, rowId: Long)

    @Query(value = "select * from bulk_sms where bulk_sms_id= :rowId")
    suspend fun bulkSmsWithRowId(rowId: Long): BulkSms

    @Delete
    suspend fun delete(t: BulkSms): Int

    @Query(value = "DELETE from bulk_sms")
    suspend fun nukeSmsTable()
}