package com.example.workloadtracker.enteties

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "Plan",
    foreignKeys = [ForeignKey(
        entity = Discipline::class,
        parentColumns = ["id"],
        childColumns = ["idDisc"],
        onDelete = CASCADE
    ),ForeignKey(
        entity = LessonType::class,
        parentColumns = ["id"],
        childColumns = ["idLT"],
        onDelete = CASCADE
    ),ForeignKey(
        entity = EducationForm::class,
        parentColumns = ["id"],
        childColumns = ["idEF"],
        onDelete = CASCADE
    ),ForeignKey(
        entity = Rate::class,
        parentColumns = ["id"],
        childColumns = ["idRate"],
        onDelete = CASCADE
    )]
)
data class Plan (
    @PrimaryKey val id: Int,
    val idDisc: Int,
    val idLT: Int,
    val idEF: Int,
    val idRate: Int,
    val hours: Int
)