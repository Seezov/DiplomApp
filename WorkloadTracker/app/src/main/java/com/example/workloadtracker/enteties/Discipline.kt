package com.example.workloadtracker.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Discipline"
)
data class Discipline (
    @PrimaryKey val id: Int,
    val name: String
)