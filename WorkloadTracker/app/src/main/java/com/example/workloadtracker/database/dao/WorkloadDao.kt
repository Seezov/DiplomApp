package com.example.workloadtracker.database.dao

import androidx.room.*
import com.example.workloadtracker.enteties.*

@Dao
interface WorkloadDao {

    @Query("SELECT * FROM Workload")
    fun getAll(): List<Workload>

    @Query("SELECT * FROM Workload " + "WHERE id = :id")
    fun getById(id: Int): Workload

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(workload: Workload)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(workloads: List<Workload>)

    @Query("DELETE FROM Workload")
    fun deleteAll()
}
