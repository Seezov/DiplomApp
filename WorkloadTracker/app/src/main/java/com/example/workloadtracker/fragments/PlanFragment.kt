package com.example.workloadtracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workloadtracker.R
import com.example.workloadtracker.adapters.PlanAdapter
import com.example.workloadtracker.enteties.Plan
import kotlinx.android.synthetic.main.fragment_plan.*

class PlanFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_plan, container, false)

    companion object {
        fun newInstance(): PlanFragment = PlanFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plans = mutableListOf(
            Plan(1, 1, 1, 1, 1, 10),
            Plan(2, 1, 2, 1, 1, 15),
            Plan(3, 1, 3, 1, 1, 5)
        )

        val adapter = PlanAdapter(plans)

        rvPlan.adapter = adapter
        rvPlan.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }
}