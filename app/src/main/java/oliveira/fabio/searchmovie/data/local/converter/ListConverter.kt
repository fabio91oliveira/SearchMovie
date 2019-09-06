package oliveira.fabio.searchmovie.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

class ListConverter {
    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        val objects =
            Gson().fromJson(value, Array<String>::class.java) as Array<String>
        return objects.toList()
    }
}