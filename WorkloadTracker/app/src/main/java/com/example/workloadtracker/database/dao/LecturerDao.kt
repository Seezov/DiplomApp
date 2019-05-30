package com.example.workloadtracker.database.dao

import androidx.room.*
import com.example.workloadtracker.enteties.Discipline
import com.example.workloadtracker.enteties.EducationForm
import com.example.workloadtracker.enteties.GroupCode
import com.example.workloadtracker.enteties.Lecturer

@Dao
interface LecturerDao {

    @Query("SELECT * FROM Lecturer")
    fun getAll(): List<Lecturer>

    @Query("SELECT * FROM Lecturer " + "WHERE id = :id")
    fun getById(id: Int): Lecturer

    @Query("SELECT * FROM Lecturer " + "WHERE name = :name")
    fun getByName(name: String): Lecturer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(lecturer: Lecturer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(lecturers: List<Lecturer>)

    @Query("DELETE FROM Lecturer")
    fun deleteAll()
}
