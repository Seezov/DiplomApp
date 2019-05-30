package com.example.workloadtracker.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.workloadtracker.R
import com.example.workloadtracker.database.DBUtils
import com.example.workloadtracker.enteties.*
import com.example.workloadtracker.fragments.PlanFragment
import com.example.workloadtracker.fragments.WorkloadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigation)
        val db = DBUtils().initDb(this)

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

        if (!DBUtils().isDatabaseExists(this)) {
            db.disciplineDao().addAll(
                listOf(
                    Discipline(1, "Computer science"),
                    Discipline(2, "Functional analysis"),
                    Discipline(3, "MMOR")
                )
            )

            db.groupCodeDao().addAll(
                listOf(
                    GroupCode(1, "KS-15"),
                    GroupCode(2, "KM-15"),
                    GroupCode(3, "KI-15")
                )
            )

            db.educationFormDao().addAll(
                listOf(
                    EducationForm(1, "Day"),
                    EducationForm(2, "Correspondence")
                )
            )

            db.lessonTypeDao().addAll(
                listOf(
                    LessonType(1, "Lecture"),
                    LessonType(2, "Practice"),
                    LessonType(3, "Laboratory")
                )
            )

            db.rateDao().addAll(
                listOf(
                    Rate(1, 0.24f),
                    Rate(2, 1f)
                )
            )

            db.lecturerDao().addAll(
                listOf(
                    Lecturer(1, "Onishchenko B.O.", "Docent"),
                    Lecturer(2, "Yarynich Y.O.", "Docent")
                )
            )

            db.workloadDao().addAll(
                listOf(
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
            )
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
