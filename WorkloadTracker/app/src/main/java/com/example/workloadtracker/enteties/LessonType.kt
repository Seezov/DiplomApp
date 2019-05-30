package com.example.workloadtracker.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "LessonType"
)
data class LessonType (
    @PrimaryKey val id: Int,
    val name: String
)