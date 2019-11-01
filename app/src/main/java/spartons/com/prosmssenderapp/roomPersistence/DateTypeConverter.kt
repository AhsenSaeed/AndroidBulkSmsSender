package spartons.com.prosmssenderapp.roomPersistence

import androidx.room.TypeConverter
import java.util.*


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class DateTypeConverter {

    @TypeConverter
    fun toDate(value: Long): Date? = Date(value)

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time
}