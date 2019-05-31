package com.example.workloadtracker.api

import com.example.workloadtracker.enteties.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    @POST("workloads")
    fun postWorkload(@Body body: RequestBody): Call<Void>

    @POST("workloads/{id}")
    fun updateWorkload(@Path("id") id: Int, @Body body: RequestBody): Call<Void>

    @DELETE("workloads/{id}")
    fun deleteWorkload(
        @Path("id") id: Int
    ): Call<Void>

    @GET("plans")
    fun getPlans(): Call<List<Plan>>
}