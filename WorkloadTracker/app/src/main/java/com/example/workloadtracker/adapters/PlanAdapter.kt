package com.example.workloadtracker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workloadtracker.DataProvider
import com.example.workloadtracker.R
import com.example.workloadtracker.enteties.Plan

class PlanAdapter(private val plans: MutableList<Plan>) : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    private val dataProvider: DataProvider = DataProvider()

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtDiscipline.text = dataProvider.getDisciplineById(plans[position].idDisc)?.name
        holder.txtLessonType.text = dataProvider.getLessonTypeById(plans[position].idLT)?.name
        holder.txtEducationFormAndRate.text =
            "${dataProvider.getEducationFormById(plans[position].idEF)?.name}/${dataProvider.getRateById(plans[position].idRate)?.value}"
        holder.txtPlanHours.text = getPlanHoursText(position)
    }

    private fun getPlanHoursText(position: Int): CharSequence? {
        val deductedHours = dataProvider.getDeductedHours(
            plans[position].idDisc,
            plans[position].idLT,
            plans[position].idEF
        )

        val planHours = plans[position].hours

        return "$deductedHours/$planHours"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_plan, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return plans.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtDiscipline: TextView = itemView.findViewById(R.id.txtDiscipline)
        val txtLessonType: TextView = itemView.findViewById(R.id.txtLessonType)
        val txtEducationFormAndRate: TextView = itemView.findViewById(R.id.txtEducationFormAndRate)
        val txtPlanHours: TextView = itemView.findViewById(R.id.txtPlanHours)
    }

}