package com.example.workloadtracker

import com.example.workloadtracker.enteties.*
import java.util.*

class DataProvider {

    private val groupCodes = listOf(
        GroupCode(1, "KS-15"),
        GroupCode(2, "KM-15"),
        GroupCode(3, "KI-15")
    )

    private val disciplines = listOf(
        Discipline(1, "Computer science"),
        Discipline(2, "Functional analysis"),
        Discipline(3, "MMOR")
    )

    private val educationForms = listOf(
        EducationForm(1, "Day"),
        EducationForm(2, "Correspondence")
    )

    private val lessonTypes = listOf(
        LessonType(1, "Lecture"),
        LessonType(2, "Practice"),
        LessonType(3, "Laboratory")
    )

    private val rates = listOf(
        Rate(1, 0.24f),
        Rate(2, 1f)
    )

    private val lecturers = listOf(
        Lecturer(1, "Onishchenko B.O.", "Docent"),
        Lecturer(2, "Yarynich Y.O.", "Docent")
    )

    private val workloads = listOf(
        Workload(
            1,
            1,
            1,
            1,
            1,
            1,
            Calendar.getInstance().time,
            2,
            1,
            1,
            203
        ),
        Workload(
            2,
            1,
            1,
            1,
            1,
            1,
            Calendar.getInstance().time,
            2,
            2,
            1,
            203
        )
    )

    fun getGroupCodeById(id: Int): GroupCode? = groupCodes.find { it.id == id }

    fun getLecturerById(id: Int): Lecturer? = lecturers.find { it.id == id }

    fun getDisciplineById(id: Int): Discipline? = disciplines.find { it.id == id }

    fun getLessonTypeById(id: Int): LessonType? = lessonTypes.find { it.id == id }

    fun getEducationFormById(id: Int): EducationForm? = educationForms.find { it.id == id }

    fun getWorkloadById(id: Int): Workload? = workloads.find { it.id == id }

    fun getRateById(id: Int): Rate? = rates.find { it.id == id }

    fun getDeductedHours(
        idDisc: Int,
        idLT: Int,
        idEF: Int
    ): Int = workloads.filter {
        it.idDisc == idDisc &&
                it.idLT == idLT &&
                it.idEF == idEF
    }
        .map { it.hours }
        .sum()

    fun getDisciplines(): MutableList<Discipline> = disciplines.toMutableList()

    fun getLessonTypes(): MutableList<LessonType> = lessonTypes.toMutableList()

    fun getWorkloads(): MutableList<Workload> = workloads.toMutableList()
}