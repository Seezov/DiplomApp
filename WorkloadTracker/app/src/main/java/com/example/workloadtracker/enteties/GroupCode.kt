package com.example.workloadtracker.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "GroupCode"
)
data class GroupCode(
    @PrimaryKey val id: Int,
    val name: String
)