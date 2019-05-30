package com.example.workloadtracker.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Lecturer"
)
data class Lecturer(
    @PrimaryKey val id: Int,
    val name: String,
    val degree: String
)