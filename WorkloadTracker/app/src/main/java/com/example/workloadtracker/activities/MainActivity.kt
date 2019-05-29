package com.example.workloadtracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workloadtracker.R
import com.example.workloadtracker.adapters.PlanAdapter
import com.example.workloadtracker.enteties.Plan
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val plans = mutableListOf(
            Plan(1, 1, 1, 1, 1, 10),
            Plan(2, 1, 2, 1, 1, 15),
            Plan(3, 1, 3, 1, 1, 5)
        )

        var adapter = PlanAdapter(plans)

        rvPlan.adapter = adapter
        rvPlan.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}
