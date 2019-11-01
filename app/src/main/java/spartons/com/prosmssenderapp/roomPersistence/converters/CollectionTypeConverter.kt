package spartons.com.prosmssenderapp.roomPersistence.converters

import androidx.room.TypeConverter
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import spartons.com.prosmssenderapp.activities.sendBulkSms.data.SmsContact

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

@UnstableDefault
object CollectionTypeConverter {

    @TypeConverter
    @JvmStatic
    fun toCollectionString(list: List<SmsContact>): String {
        return Json.stringify(SmsContact.serializer().list, list)
    }

    @TypeConverter
    @JvmStatic
    fun fromStringToCollection(json: String): List<SmsContact> {
        return Json.parse(SmsContact.serializer().list, json)
    }
}