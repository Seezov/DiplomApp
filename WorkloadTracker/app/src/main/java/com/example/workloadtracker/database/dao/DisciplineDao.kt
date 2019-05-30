package com.example.workloadtracker.database.dao

import androidx.room.*
import com.example.workloadtracker.enteties.Discipline

@Dao
interface DisciplineDao {

    @Query("SELECT * FROM Discipline")
    fun getAll(): List<Discipline>

    @Query("SELECT * FROM Discipline " + "WHERE id = :id")
    fun getById(id: Int): Discipline

    @Query("SELECT * FROM Discipline " + "WHERE name = :name")
    fun getByName(name: String): Discipline

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(discipline: Discipline)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(disciplines: List<Discipline>)

    @Query("DELETE FROM Discipline")
    fun deleteAll()
}
