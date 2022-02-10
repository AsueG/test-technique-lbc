package fr.haumey.leboncointest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.haumey.leboncointest.model.Title.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Title (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "albumId")
    val albumId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "thumbnailUrl")
    val thumbnailUrl: String
) {
    companion object {
        const val TABLE_NAME = "title_table"
    }
}