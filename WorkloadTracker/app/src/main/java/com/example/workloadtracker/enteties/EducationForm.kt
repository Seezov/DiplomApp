package com.example.workloadtracker.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "EducationForm"
)
data class EducationForm (
    @PrimaryKey val id: Int,
    val name: String
)