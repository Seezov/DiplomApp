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
import com.example.workloadtracker.DataProvider
import com.example.workloadtracker.R
import com.example.workloadtracker.adapters.PlanAdapter
import com.example.workloadtracker.enteties.Plan
import kotlinx.android.synthetic.main.fragment_plan.*



class PlanFragment : Fragment() {

    private val dataProvider: DataProvider = DataProvider()

    private val plans = mutableListOf(
        Plan(1, 1, 1, 1, 1, 10),
        Plan(2, 1, 2, 1, 1, 15),
        Plan(3, 1, 3, 1, 1, 5)
    )

    private var newPlans: MutableList<Plan> = emptyList<Plan>().toMutableList()

    private lateinit var adapter: PlanAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_plan, container, false)

    companion object {
        fun newInstance(): PlanFragment = PlanFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newPlans.addAll(plans)
        adapter = PlanAdapter(newPlans)
        rvPlan.adapter = adapter
        rvPlan.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        // Set spinners type
        inflateSpinner(spinnerDisc, dataProvider.getDisciplines().map { it.name }.toMutableList())
        inflateSpinner(spinnerLT, dataProvider.getLessonTypes().map { it.name }.toMutableList())

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
        newPlans.addAll(plans.filter {
            (dataProvider.getDisciplineById(it.idDisc)?.name == spinnerDisc.selectedItem.toString() ||
            spinnerDisc.selectedItem.toString() == "Any") &&
            (dataProvider.getLessonTypeById(it.idLT)?.name == spinnerLT.selectedItem.toString() ||
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