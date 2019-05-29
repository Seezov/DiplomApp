package com.example.workloadtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workloadtracker.DataProvider
import com.example.workloadtracker.R
import com.example.workloadtracker.adapters.WorkloadAdapter
import kotlinx.android.synthetic.main.fragment_workload.*

class WorkloadFragment : Fragment() {

    private val dataProvider: DataProvider = DataProvider()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_workload, container, false)

    companion object {
        fun newInstance(): WorkloadFragment = WorkloadFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WorkloadAdapter(dataProvider.getWorkloads())
        rvWorkload.adapter = adapter
        rvWorkload.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }
}