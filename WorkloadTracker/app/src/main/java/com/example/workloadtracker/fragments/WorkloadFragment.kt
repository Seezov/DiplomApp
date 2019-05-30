package com.example.workloadtracker.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workloadtracker.DataProvider
import com.example.workloadtracker.R
import com.example.workloadtracker.adapters.PlanAdapter
import com.example.workloadtracker.adapters.WorkloadAdapter
import kotlinx.android.synthetic.main.dialog_add_workload.*
import kotlinx.android.synthetic.main.dialog_add_workload.view.*
import kotlinx.android.synthetic.main.fragment_workload.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*




class WorkloadFragment : Fragment() {

    private val dataProvider: DataProvider = DataProvider()

    private lateinit var adapter: WorkloadAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_workload, container, false)

    companion object {
        fun newInstance(): WorkloadFragment = WorkloadFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val workloads = dataProvider.getWorkloads()
        adapter = WorkloadAdapter(dataProvider, workloads)
        rvWorkload.adapter = adapter
        rvWorkload.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        btnAddWorkload.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_workload, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(context!!)
                .setView(mDialogView)
            //show dialog
            val mAlertDialog = mBuilder.show()
            inflateSpinner(mDialogView.spinnerDisc, dataProvider.getDisciplines().map { it.name }.toMutableList())
            inflateSpinner(mDialogView.spinnerLT, dataProvider.getLessonTypes().map { it.name }.toMutableList())
            inflateSpinner(mDialogView.spinnerGC, dataProvider.getGroupCodes().map { it.name }.toMutableList())
            inflateSpinner(mDialogView.spinnerLecturer, dataProvider.getLecturers().map { it.name }.toMutableList())
            inflateSpinner(mDialogView.spinnerEF, dataProvider.getEducationForms().map { it.name }.toMutableList())

            val newCalendar = Calendar.getInstance()
            mDialogView.txtDate.text = SimpleDateFormat("dd/MM/yyyy").format(newCalendar.time)

            val startTime = DatePickerDialog(context!!, R.style.Base_Theme_AppCompat_Light_Dialog, { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                mDialogView.txtDate.text = SimpleDateFormat("dd/MM/yyyy").format(newDate.time)
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))

            mDialogView.btnDate.setOnClickListener { startTime.show() }

            //login button click of custom layout
            mDialogView.btnSaveWorkload.setOnClickListener {

                //dismiss dialog
                if (
                    mDialogView.fHours.text.toString().isNotEmpty() &&
                    mDialogView.fWeek.text.toString().isNotEmpty() &&
                    mDialogView.fIndex.text.toString().isNotEmpty() &&
                    mDialogView.fHall.text.toString().isNotEmpty()
                ) {
                    mAlertDialog.dismiss()
                    dataProvider.addWorkload(
                        mDialogView.spinnerGC.selectedItem.toString(),
                        mDialogView.spinnerLecturer.selectedItem.toString(),
                        mDialogView.spinnerLT.selectedItem.toString(),
                        mDialogView.spinnerDisc.selectedItem.toString(),
                        mDialogView.spinnerEF.selectedItem.toString(),
                        SimpleDateFormat("dd/MM/yyyy").parse(mDialogView.txtDate.text.toString()),
                        mDialogView.fHours.text.toString().toInt(),
                        mDialogView.fWeek.text.toString().toInt(),
                        mDialogView.fIndex.text.toString().toInt(),
                        mDialogView.fHall.text.toString().toInt()
                    )
                    workloads.clear()
                    workloads.addAll(dataProvider.getWorkloads())
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun inflateSpinner(spinner: Spinner, objects: MutableList<Any>) {
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val disciplineAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, objects)
        // Set layout to use when the list of choices appear
        disciplineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = disciplineAdapter
    }
}