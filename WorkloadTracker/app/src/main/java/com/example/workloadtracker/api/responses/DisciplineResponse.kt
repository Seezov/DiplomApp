package com.example.workloadtracker.api.responses

import com.example.workloadtracker.enteties.Discipline

data class DisciplinesResponse(
    val disciplines: List<Discipline>
)