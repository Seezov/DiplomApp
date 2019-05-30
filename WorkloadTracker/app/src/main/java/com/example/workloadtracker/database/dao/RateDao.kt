package com.example.workloadtracker.database.dao

import androidx.room.*
import com.example.workloadtracker.enteties.*

@Dao
interface RateDao {

    @Query("SELECT * FROM Rate")
    fun getAll(): List<Rate>

    @Query("SELECT * FROM Rate " + "WHERE id = :id")
    fun getById(id: Int): Rate

    @Query("SELECT * FROM Rate " + "WHERE value = :value")
    fun getByValue(value: Float): Rate

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(rate: Rate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(rates: List<Rate>)

    @Query("DELETE FROM Rate")
    fun deleteAll()
}
