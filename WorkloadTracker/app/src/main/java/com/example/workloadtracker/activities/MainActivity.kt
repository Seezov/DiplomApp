package com.example.workloadtracker.activities

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.workloadtracker.R
import com.example.workloadtracker.api.ApiClient
import com.example.workloadtracker.api.responses.DisciplinesResponse
import com.example.workloadtracker.database.DBUtils
import com.example.workloadtracker.enteties.*
import com.example.workloadtracker.fragments.PlanFragment
import com.example.workloadtracker.fragments.WorkloadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigation)
        val db = DBUtils().initDb(this)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.101:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiClient = retrofit.create(ApiClient::class.java)

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_plan -> {
                    toolbar.title = getString(R.string.plan)
                    val planFragment = PlanFragment.newInstance(db)
                    openFragment(planFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_workload -> {
                    toolbar.title = getString(R.string.workload)
                    val workloadFragment = WorkloadFragment.newInstance(db)
                    openFragment(workloadFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        bottomNavigation.selectedItemId = R.id.navigation_plan

        apiClient.getDisciplines().enqueue(object : Callback<List<Discipline>> {
            override fun onResponse(call: Call<List<Discipline>>, response: Response<List<Discipline>>) {
                if (response.isSuccessful) {
                    db.disciplineDao().addAll(
                        response.body()!!
                    )
                }
            }

            override fun onFailure(call: Call<List<Discipline>>, t: Throwable) {
            }
        })

        apiClient.getGroupCodes().enqueue(object : Callback<List<GroupCode>> {
            override fun onResponse(call: Call<List<GroupCode>>, response: Response<List<GroupCode>>) {
                if (response.isSuccessful) {
                    db.groupCodeDao().addAll(
                        response.body()!!
                    )
                }
            }

            override fun onFailure(call: Call<List<GroupCode>>, t: Throwable) {
            }
        })

        apiClient.getEducationForms().enqueue(object : Callback<List<EducationForm>> {
            override fun onResponse(call: Call<List<EducationForm>>, response: Response<List<EducationForm>>) {
                if (response.isSuccessful) {
                    db.educationFormDao().addAll(
                        response.body()!!
                    )
                }
            }

            override fun onFailure(call: Call<List<EducationForm>>, t: Throwable) {
            }
        })

        apiClient.getLessonTypes().enqueue(object : Callback<List<LessonType>> {
            override fun onResponse(call: Call<List<LessonType>>, response: Response<List<LessonType>>) {
                if (response.isSuccessful) {
                    db.lessonTypeDao().addAll(
                        response.body()!!
                    )
                }
            }

            override fun onFailure(call: Call<List<LessonType>>, t: Throwable) {
            }
        })

        apiClient.getLecturers().enqueue(object : Callback<List<Lecturer>> {
            override fun onResponse(call: Call<List<Lecturer>>, response: Response<List<Lecturer>>) {
                if (response.isSuccessful) {
                    db.lecturerDao().addAll(
                        response.body()!!
                    )
                }
            }

            override fun onFailure(call: Call<List<Lecturer>>, t: Throwable) {
            }
        })

        apiClient.getRates().enqueue(object : Callback<List<Rate>> {
            override fun onResponse(call: Call<List<Rate>>, response: Response<List<Rate>>) {
                if (response.isSuccessful) {
                    db.rateDao().addAll(
                        response.body()!!
                    )
                }
            }

            override fun onFailure(call: Call<List<Rate>>, t: Throwable) {
            }
        })

        apiClient.getWorkloads().enqueue(object : Callback<List<Workload>> {
            override fun onResponse(call: Call<List<Workload>>, response: Response<List<Workload>>) {
                if (response.isSuccessful) {
                    db.workloadDao().addAll(
                        response.body()!!
                    )
                }
            }

            override fun onFailure(call: Call<List<Workload>>, t: Throwable) {
            }
        })

        apiClient.getPlans().enqueue(object : Callback<List<Plan>> {
            override fun onResponse(call: Call<List<Plan>>, response: Response<List<Plan>>) {
                if (response.isSuccessful) {
                    db.planDao().addAll(
                        response.body()!!
                    )
                }
            }

            override fun onFailure(call: Call<List<Plan>>, t: Throwable) {
            }
        })
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
