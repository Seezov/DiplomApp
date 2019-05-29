package com.example.workloadtracker.enteties

import java.util.*

data class Workload (
    val id: Int,
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