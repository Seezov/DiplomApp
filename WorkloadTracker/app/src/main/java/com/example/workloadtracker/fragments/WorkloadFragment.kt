package com.example.workloadtracker.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workloadtracker.R
import com.example.workloadtracker.SPCache
import com.example.workloadtracker.adapters.WorkloadAdapter
import com.example.workloadtracker.api.ApiClient
import com.example.workloadtracker.database.AppDatabase
import com.example.workloadtracker.enteties.Workload
import com.example.workloadtracker.moshi.FallbackOnNullAdapterFactory
import com.example.workloadtracker.moshi.toJson
import com.kidslox.app.moshi.DateJsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.dialog_add_workload.view.*
import kotlinx.android.synthetic.main.fragment_workload.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Environment
import android.widget.Toast
import com.example.workloadtracker.PDFHelper
import java.io.File


class WorkloadFragment(
    private var apiClient: ApiClient,
    private var db: AppDatabase,
    private var spCache: SPCache
) : Fragment(), WorkloadAdapter.OnWorkloadListener {

    private lateinit var adapter: WorkloadAdapter

    private var workloads: MutableList<Workload> = emptyList<Workload>().toMutableList()

    val moshi = Moshi.Builder()
        .add(DateJsonAdapter())
        .add(FallbackOnNullAdapterFactory())
        .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_workload, container, false)

    companion object {
        fun newInstance(apiClient: ApiClient, db: AppDatabase, spCache: SPCache): WorkloadFragment =
            WorkloadFragment(apiClient, db, spCache)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val currentWorkload = workloads[item.groupId]
        when (item.itemId) {
            121 -> {
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

                mDialogView.spinnerDisc.setSelection(currentWorkload.idDisc - 1)
                mDialogView.spinnerLT.setSelection(currentWorkload.idLT - 1)
                mDialogView.spinnerGC.setSelection(currentWorkload.idGC - 1)
                mDialogView.spinnerEF.setSelection(currentWorkload.idEF - 1)

                mDialogView.txtDate.text = SimpleDateFormat("dd/MM/yyyy").format(currentWorkload.date)

                val startTime = DatePickerDialog(
                    context!!,
                    R.style.Base_Theme_AppCompat_Light_Dialog,
                    { _, year, monthOfYear, dayOfMonth ->
                        val newDate = Calendar.getInstance()
                        newDate.set(year, monthOfYear, dayOfMonth)
                        mDialogView.txtDate.text = SimpleDateFormat("dd/MM/yyyy").format(newDate.time)
                    },
                    currentWorkload.date.year,
                    currentWorkload.date.month,
                    currentWorkload.date.day
                )

                mDialogView.fHours.setText(currentWorkload.hours.toString(), TextView.BufferType.EDITABLE)
                mDialogView.fWeek.setText(currentWorkload.week.toString(), TextView.BufferType.EDITABLE)
                mDialogView.fIndex.setText(currentWorkload.pairIndex.toString(), TextView.BufferType.EDITABLE)
                mDialogView.fHall.setText(currentWorkload.hall.toString(), TextView.BufferType.EDITABLE)

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

                        val newWorkload = Workload(
                            currentWorkload.id,
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

                        db.workloadDao().add(newWorkload)


                        Thread(Runnable {
                            try {
                                apiClient.updateWorkload(
                                    newWorkload.id,
                                    jsonStringToRequestBody(
                                        moshi.toJson(
                                            mapOf(
                                                "id" to newWorkload.id,
                                                "idGC" to newWorkload.idGC,
                                                "idLecturer" to newWorkload.idLecturer,
                                                "idLT" to newWorkload.idLT,
                                                "idDisc" to newWorkload.idDisc,
                                                "idEF" to newWorkload.idEF,
                                                "date" to newWorkload.date,
                                                "hours" to newWorkload.hours,
                                                "week" to newWorkload.week,
                                                "pairIndex" to newWorkload.pairIndex,
                                                "hall" to newWorkload.hall
                                            )
                                        )
                                    )
                                ).execute()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }).start()

                        filterItems()
                    }
                }

            }
            122 -> {
                db.workloadDao().deleteById(currentWorkload.id)
                Thread(Runnable {
                    apiClient.deleteWorkload(currentWorkload.id).execute()
                }).start()
                workloads.removeAt(item.groupId)
                adapter.notifyDataSetChanged()
            }
        }

        return super.onContextItemSelected(item)
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

    private fun jsonStringToRequestBody(json: String): RequestBody =
        RequestBody.create(MediaType.parse("application/json"), json.toByteArray())

    override fun onWorkloadLongClicked(position: Int) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workloads = db.workloadDao().getAll().toMutableList()
        adapter = WorkloadAdapter(db, workloads)
        rvWorkload.adapter = adapter
        rvWorkload.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        val file = File(Environment.getExternalStorageDirectory(), "pdf")

        btnPdfReport.setOnClickListener {
            PDFHelper(file, context).saveImageToPDF(rvWorkload, getRecyclerViewScreenshot(rvWorkload), "testpdf")
            Toast.makeText(context, "PDF файл створений в " + file.absolutePath, Toast.LENGTH_SHORT).show()
        }

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

                    val newWorkload = Workload(
                        db.workloadDao().getAll().last().id + 1,
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

                    db.workloadDao().add(newWorkload)

                    Thread(Runnable {
                        try {
                            apiClient.postWorkload(
                                jsonStringToRequestBody(
                                    moshi.toJson(
                                        mapOf(
                                            "id" to newWorkload.id,
                                            "idGC" to newWorkload.idGC,
                                            "idLecturer" to newWorkload.idLecturer,
                                            "idLT" to newWorkload.idLT,
                                            "idDisc" to newWorkload.idDisc,
                                            "idEF" to newWorkload.idEF,
                                            "date" to newWorkload.date,
                                            "hours" to newWorkload.hours,
                                            "week" to newWorkload.week,
                                            "pairIndex" to newWorkload.pairIndex,
                                            "hall" to newWorkload.hall
                                        )
                                    )
                                )
                            ).execute()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }).start()

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

    fun getRecyclerViewScreenshot(view: RecyclerView): Bitmap {
        val size = view.adapter!!.itemCount
        val holder = view.adapter!!.createViewHolder(view, 0)
        view.adapter!!.onBindViewHolder(holder, 0)
        holder.itemView.measure(
            View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        holder.itemView.layout(0, 0, holder.itemView.measuredWidth, holder.itemView.measuredHeight)
        val bigBitmap = Bitmap.createBitmap(
            view.measuredWidth, holder.itemView.measuredHeight * size,
            Bitmap.Config.ARGB_8888
        )
        val bigCanvas = Canvas(bigBitmap)
        bigCanvas.drawColor(Color.WHITE)
        val paint = Paint()
        var iHeight = 0f
        holder.itemView.isDrawingCacheEnabled = true
        holder.itemView.buildDrawingCache()
        bigCanvas.drawBitmap(holder.itemView.drawingCache, 0f, iHeight, paint)
        holder.itemView.isDrawingCacheEnabled = false
        holder.itemView.destroyDrawingCache()
        iHeight += holder.itemView.measuredHeight
        for (i in 1 until size) {
            view.adapter!!.onBindViewHolder(holder, i)
            holder.itemView.isDrawingCacheEnabled = true
            holder.itemView.buildDrawingCache()
            bigCanvas.drawBitmap(holder.itemView.drawingCache, 0f, iHeight, paint)
            iHeight += holder.itemView.measuredHeight
            holder.itemView.isDrawingCacheEnabled = false
            holder.itemView.destroyDrawingCache()
        }
        return bigBitmap
    }
}