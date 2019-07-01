package spartons.com.prosmssenderapp.roomPersistence

import androidx.lifecycle.LiveData
import androidx.room.*


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

@Dao
interface BulkSmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: BulkSms): Long

    @Query(value = "select * from bulk_sms")
    fun all(): LiveData<List<BulkSms>>

    @Delete
    suspend fun delete(t: BulkSms): Int

    @Query(value = "DELETE from bulk_sms")
    suspend fun nukeSmsTable()
}