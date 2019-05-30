package com.example.workloadtracker.api

import com.example.workloadtracker.api.responses.DisciplinesResponse
import com.example.workloadtracker.enteties.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiClient {

    @GET("disciplines")
    fun getDisciplines(): Call<List<Discipline>>

    @GET("groupcodes")
    fun getGroupCodes(): Call<List<GroupCode>>

    @GET("educationforms")
    fun getEducationForms(): Call<List<EducationForm>>

    @GET("lessontypes")
    fun getLessonTypes(): Call<List<LessonType>>

    @GET("lecturers")
    fun getLecturers(): Call<List<Lecturer>>

    @GET("rates")
    fun getRates(): Call<List<Rate>>

    @GET("workloads")
    fun getWorkloads(): Call<List<Workload>>

    @GET("plans")
    fun getPlans(): Call<List<Plan>>
}