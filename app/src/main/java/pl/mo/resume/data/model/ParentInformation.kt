package pl.mo.resume.data.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import pl.mo.resume.data.model.ParentInformation.Companion.TABLE_NAME
import java.io.Serializable
import java.lang.reflect.Type

@Entity(tableName = TABLE_NAME)
data class ParentInformation(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: Int? = 0,

    @ColumnInfo(name = "title") var title: String? = null,

    @ColumnInfo(name = "image_url") var imageUrl: String? = null,

    @ColumnInfo(name = "image_url_dark") var imageUrlDark: String? = null,

    @TypeConverters(ChildInformationConverters::class)
    @SerializedName("children")
    @ColumnInfo(name = "children")
    var children: List<ChildInformation>? = null
) {
    companion object {
        const val TABLE_NAME = "resume_information"
    }
}

class ChildInformationConverters: Serializable {
    @TypeConverter
    fun fromChildren(children: List<ChildInformation>?): String? {
        if (children == null)
            return null

        val gson = Gson()
        val type: Type = object : TypeToken<List<ChildInformation?>?>() {}.type
        return gson.toJson(children, type)
    }

    @TypeConverter
    fun toChildren(children: String?): List<ChildInformation>? {
        if (children == null)
            return null

        val gson = Gson()
        val type: Type = object : TypeToken<List<ChildInformation?>?>() {}.type
        return gson.fromJson(children, type)
    }
}