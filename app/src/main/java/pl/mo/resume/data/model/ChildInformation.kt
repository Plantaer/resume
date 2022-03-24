package pl.mo.resume.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

data class ChildInformation(
    @ColumnInfo(name = "date") var date: String? = null,
    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "body") var body: String? = null
)