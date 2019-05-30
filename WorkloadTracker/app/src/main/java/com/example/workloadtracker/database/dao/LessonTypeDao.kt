package com.example.workloadtracker.database.dao

import androidx.room.*
import com.example.workloadtracker.enteties.*

@Dao
interface LessonTypeDao {

    @Query("SELECT * FROM LessonType")
    fun getAll(): List<LessonType>

    @Query("SELECT * FROM LessonType " + "WHERE id = :id")
    fun getById(id: Int): LessonType

    @Query("SELECT * FROM LessonType " + "WHERE name = :name")
    fun getByName(name: String): LessonType

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(lessonType: LessonType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(lessonTypes: List<LessonType>)

    @Query("DELETE FROM LessonType")
    fun deleteAll()
}
