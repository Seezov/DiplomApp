package com.example.workloadtracker.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.workloadtracker.R
import com.example.workloadtracker.fragments.PlanFragment
import com.example.workloadtracker.fragments.WorkloadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigation)

        bottomNavigation.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.navigation_plan -> {
                    toolbar.title = getString(R.string.plan)
                    val planFragment = PlanFragment.newInstance()
                    openFragment(planFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_workload -> {
                    toolbar.title = getString(R.string.workload)
                    val workloadFragment = WorkloadFragment.newInstance()
                    openFragment(workloadFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        bottomNavigation.selectedItemId = R.id.navigation_plan
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
