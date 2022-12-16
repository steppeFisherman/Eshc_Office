package com.example.eshccheck.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eshccheck.data.model.cacheModel.DataCache

@Dao
interface AppRoomDao {

    @Query("SELECT * FROM item_table WHERE locationFlagOnly = :locationFlagOnly AND alarm = :alarm")
    fun fetchAllUsersLocation(locationFlagOnly: Boolean, alarm: Boolean): LiveData<List<DataCache>>

    @Query("SELECT * FROM item_table WHERE alarm = :alarm")
    fun fetchAllUsers(alarm: Boolean): LiveData<List<DataCache>>

    @Query("SELECT * FROM item_table")
    suspend fun fetchAllItemsBySuspend(): List<DataCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(dataCache: DataCache)

    @Query("DELETE FROM item_table")
    suspend fun deleteItem()

}