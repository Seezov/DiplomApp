package com.example.workloadtracker.database.dao

import androidx.room.*
import com.example.workloadtracker.enteties.Discipline
import com.example.workloadtracker.enteties.EducationForm
import com.example.workloadtracker.enteties.GroupCode

@Dao
interface GroupCodeDao {

    @Query("SELECT * FROM GroupCode")
    fun getAll(): List<GroupCode>

    @Query("SELECT * FROM GroupCode " + "WHERE id = :id")
    fun getById(id: Int): GroupCode

    @Query("SELECT * FROM GroupCode " + "WHERE name = :name")
    fun getByName(name: String): GroupCode

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(groupCode: GroupCode)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(groupCodes: List<GroupCode>)

    @Query("DELETE FROM GroupCode")
    fun deleteAll()
}
