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
import com.example.workloadtracker.R
import com.example.workloadtracker.SPCache
import com.example.workloadtracker.adapters.WorkloadAdapter
import com.example.workloadtracker.database.AppDatabase
import com.example.workloadtracker.enteties.Plan
import com.example.workloadtracker.enteties.Workload
import kotlinx.android.synthetic.main.dialog_add_workload.view.*
import kotlinx.android.synthetic.main.fragment_workload.*
import java.text.SimpleDateFormat
import java.util.*


class WorkloadFragment(
    private var db: AppDatabase,
    private var spCache: SPCache
) : Fragment() {

    private lateinit var adapter: WorkloadAdapter

    private var workloads: MutableList<Workload> = emptyList<Workload>().toMutableList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_workload, container, false)

    companion object {
        fun newInstance(db: AppDatabase, spCache: SPCache): WorkloadFragment = WorkloadFragment(db, spCache)
    }

    override fun onResume() {
        super.onResume()
        filterItems()
    }

    private fun filterItems() {
        workloads.clear()
        workloads.addAll(db.workloadDao().getAll().filter {
            db.lecturerDao().getById(it.idLecturer).name == spCache.currentLecturer.name
        })
        adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workloads = db.workloadDao().getAll().toMutableList()
        adapter = WorkloadAdapter(db, workloads)
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
            inflateSpinner(mDialogView.spinnerDisc, db.disciplineDao().getAll().map { it.name }.toMutableList())
            inflateSpinner(mDialogView.spinnerLT, db.lessonTypeDao().getAll().map { it.name }.toMutableList())
            inflateSpinner(mDialogView.spinnerGC, db.groupCodeDao().getAll().map { it.name }.toMutableList())
            inflateSpinner(mDialogView.spinnerEF, db.educationFormDao().getAll().map { it.name }.toMutableList())

            val newCalendar = Calendar.getInstance()
            mDialogView.txtDate.text = SimpleDateFormat("dd/MM/yyyy").format(newCalendar.time)

            val startTime = DatePickerDialog(
                context!!,
                R.style.Base_Theme_AppCompat_Light_Dialog,
                { _, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)
                    mDialogView.txtDate.text = SimpleDateFormat("dd/MM/yyyy").format(newDate.time)
                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
            )

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

                    db.workloadDao().add(
                        Workload(
                            db.workloadDao().getAll().size + 1,
                            db.groupCodeDao().getByName(mDialogView.spinnerGC.selectedItem.toString()).id,
                            db.lecturerDao().getByName(spCache.currentLecturer.name).id,
                            db.lessonTypeDao().getByName(mDialogView.spinnerLT.selectedItem.toString()).id,
                            db.disciplineDao().getByName(mDialogView.spinnerDisc.selectedItem.toString()).id,
                            db.educationFormDao().getByName(mDialogView.spinnerEF.selectedItem.toString()).id,
                            SimpleDateFormat("dd/MM/yyyy").parse(mDialogView.txtDate.text.toString()),
                            mDialogView.fHours.text.toString().toInt(),
                            mDialogView.fWeek.text.toString().toInt(),
                            mDialogView.fIndex.text.toString().toInt(),
                            mDialogView.fHall.text.toString().toInt()
                        )
                    )
                    filterItems()
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