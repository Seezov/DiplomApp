package com.example.workloadtracker.database.dao

import androidx.room.*
import com.example.workloadtracker.enteties.Discipline
import com.example.workloadtracker.enteties.EducationForm

@Dao
interface EducationFormDao {

    @Query("SELECT * FROM EducationForm")
    fun getAll(): List<EducationForm>

    @Query("SELECT * FROM EducationForm " + "WHERE id = :id")
    fun getById(id: Int): EducationForm

    @Query("SELECT * FROM EducationForm " + "WHERE name = :name")
    fun getByName(name: String): EducationForm

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(educationForm: EducationForm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(educationForms: List<EducationForm>)

    @Query("DELETE FROM EducationForm")
    fun deleteAll()
}
