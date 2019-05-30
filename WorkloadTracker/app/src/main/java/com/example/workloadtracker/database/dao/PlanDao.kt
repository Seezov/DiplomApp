package com.example.workloadtracker.database.dao

import androidx.room.*
import com.example.workloadtracker.enteties.*

@Dao
interface PlanDao {

    @Query("SELECT * FROM 'Plan'")
    fun getAll(): List<Plan>

    @Query("SELECT * FROM Plan " + "WHERE id = :id")
    fun getById(id: Int): Plan

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(plan: Plan)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(plans: List<Plan>)

    @Query("DELETE FROM 'Plan'")
    fun deleteAll()
}
