package com.example.workloadtracker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workloadtracker.DataProvider
import com.example.workloadtracker.R
import com.example.workloadtracker.enteties.Workload
import java.text.SimpleDateFormat

class WorkloadAdapter(private val plans: MutableList<Workload>) : RecyclerView.Adapter<WorkloadAdapter.ViewHolder>() {

    private val dataProvider: DataProvider = DataProvider()

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtDiscipline.text = dataProvider.getDisciplineById(plans[position].idDisc)?.name
        holder.txtLessonType.text = dataProvider.getLessonTypeById(plans[position].idLT)?.name

        holder.txtWorkloadInfo.text = "${SimpleDateFormat("dd-MM-yyyy").format(plans[position].date)}\n" +
            "${plans[position].week} week/" +
                "${plans[position].index} pair/" +
                "${plans[position].hall} hall/" +
                "${dataProvider.getEducationFormById(plans[position].idEF)?.name} form"

        holder.txtGroupCode.text = dataProvider.getGroupCodeById(
            dataProvider.getWorkloadById(plans[position].id)?.idGC!!
        )?.name
        holder.txtLecturer.text = dataProvider.getLecturerById(
            dataProvider.getWorkloadById(plans[position].id)?.idLecturer!!
        )?.name
        holder.txtWorkloadHours.text = "${dataProvider.getWorkloadById(plans[position].id)?.hours.toString()} hours"
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_workload, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return plans.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtDiscipline: TextView = itemView.findViewById(R.id.txtDiscipline)
        val txtLessonType: TextView = itemView.findViewById(R.id.txtLessonType)
        val txtWorkloadInfo: TextView = itemView.findViewById(R.id.txtWorkloadInfo)
        val txtGroupCode: TextView = itemView.findViewById(R.id.txtGroupCode)
        val txtLecturer: TextView = itemView.findViewById(R.id.txtLecturer)
        val txtWorkloadHours: TextView = itemView.findViewById(R.id.txtWorkloadHours)
    }

}