package fr.haumey.leboncointest.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.haumey.leboncointest.model.Title
import kotlinx.coroutines.flow.Flow

@Dao
interface TitleDao {
    @Query("SELECT * FROM ${Title.TABLE_NAME}")
    fun getTitles(): LiveData<List<Title>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTitles(titles: List<Title>)
}