package com.example.workloadtracker.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.workloadtracker.R
import com.example.workloadtracker.enteties.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TEST DATA
        val groupCodes = listOf(
            GroupCode(1,"KS-15"),
            GroupCode(2,"KM-15"),
            GroupCode(3,"KI-15")
        )

        val disciplines = listOf(
            Discipline(1, "Computer science"),
            Discipline(2, "Functional analysis"),
            Discipline(3, "MMOR")
        )

        val educationForms = listOf(
            EducationForm(1, "Day"),
            EducationForm(2, "Correspondence")
        )

        val lessonTypes = listOf(
            LessonType(1, "Lecture"),
            LessonType(2, "Practice"),
            LessonType(3, "Laboratory")
        )

        val rates = listOf(
            Rate(1, 0.24f),
            Rate(2, 1f)
        )

        val lecturers = listOf(
            Lecturer(1, "Onishchenko B.O.", "Docent"),
            Lecturer(2, "Yarynich Y.O.", "Docent")
        )

        val workloads = listOf(
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
                203)
        )

        val plans = listOf(
            Plan(1, 1, 1, 1, 1, 10),
            Plan(2, 2, 1, 1, 1, 15),
            Plan(3, 3, 1, 1, 1, 5)
        )
    }
}
