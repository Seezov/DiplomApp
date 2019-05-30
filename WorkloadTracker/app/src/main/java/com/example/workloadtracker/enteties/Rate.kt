package com.example.workloadtracker.enteties

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Rate",
    foreignKeys = [ForeignKey(
        entity = Lecturer::class,
        parentColumns = ["id"],
        childColumns = ["idLecturer"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Rate(
    @PrimaryKey val id: Int,
    val idLecturer: Int,
    val value: Float
)