package com.example.workloadtracker.enteties

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.workloadtracker.database.converters.DateConverter
import java.util.*

@Entity(
    tableName = "Workload",
    foreignKeys = [ForeignKey(
        entity = GroupCode::class,
        parentColumns = ["id"],
        childColumns = ["idGC"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Lecturer::class,
        parentColumns = ["id"],
        childColumns = ["idLecturer"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Discipline::class,
        parentColumns = ["id"],
        childColumns = ["idDisc"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = LessonType::class,
        parentColumns = ["id"],
        childColumns = ["idLT"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = EducationForm::class,
        parentColumns = ["id"],
        childColumns = ["idEF"],
        onDelete = ForeignKey.CASCADE
    )]
)
@TypeConverters(DateConverter::class)
data class Workload (
    @PrimaryKey val id: Int,
    val idGC: Int,
    val idLecturer: Int,
    val idLT: Int,
    val idDisc: Int,
    val idEF: Int,
    val date: Date,
    val hours: Int,
    val week: Int,
    val index: Int,
    val hall: Int
)