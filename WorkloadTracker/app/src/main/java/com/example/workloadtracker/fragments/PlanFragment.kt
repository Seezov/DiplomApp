package com.example.workloadtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workloadtracker.R
import com.example.workloadtracker.adapters.PlanAdapter
import com.example.workloadtracker.database.AppDatabase
import com.example.workloadtracker.database.DBUtils
import com.example.workloadtracker.enteties.Plan
import kotlinx.android.synthetic.main.fragment_plan.*

class PlanFragment(private var db: AppDatabase) : Fragment() {

    private var newPlans: MutableList<Plan> = emptyList<Plan>().toMutableList()

    private lateinit var adapter: PlanAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_plan, container, false)

    companion object {
        fun newInstance(db: AppDatabase): PlanFragment = PlanFragment(db)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newPlans.addAll(db.planDao().getAll())
        adapter = PlanAdapter(db, newPlans)
        rvPlan.adapter = adapter
        rvPlan.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        // Set spinners type
        inflateSpinner(spinnerDisc, db.disciplineDao().getAll().map { it.name }.toMutableList())
        inflateSpinner(spinnerLT, db.lessonTypeDao().getAll().map { it.name }.toMutableList())

        val callback = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                filterItems()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinnerDisc.onItemSelectedListener = callback
        spinnerLT.onItemSelectedListener = callback
    }

    private fun filterItems() {
        newPlans.clear()
        newPlans.addAll(db.planDao().getAll().filter {
            (db.disciplineDao().getById(it.idDisc).name == spinnerDisc.selectedItem.toString() ||
            spinnerDisc.selectedItem.toString() == "Any") &&
            (db.lessonTypeDao().getById(it.idLT).name == spinnerLT.selectedItem.toString() ||
            spinnerLT.selectedItem.toString() == "Any")
        })
        adapter.notifyDataSetChanged()
    }

    private fun inflateSpinner(spinner: Spinner, objects: MutableList<Any>) {
        objects.add(0,"Any")
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val disciplineAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, objects)
        // Set layout to use when the list of choices appear
        disciplineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = disciplineAdapter
    }
}