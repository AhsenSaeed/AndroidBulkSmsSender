package spartons.com.prosmssenderapp.roomPersistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class CollectionTypeConverter {

    private val gson = Gson()
    private val type = object : TypeToken<Array<String>>() {}.type

    @TypeConverter
    fun toCollectionString(list: Array<String>): String {
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromStringToCollection(json: String): Array<String> {
        return gson.fromJson(json, type)
    }
}