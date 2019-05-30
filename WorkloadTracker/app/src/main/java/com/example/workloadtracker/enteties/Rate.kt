package com.example.workloadtracker.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Rate"
)
data class Rate(
    @PrimaryKey val id: Int,
    val value: Float
)