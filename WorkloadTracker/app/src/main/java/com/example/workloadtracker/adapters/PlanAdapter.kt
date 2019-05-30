package com.example.workloadtracker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workloadtracker.R
import com.example.workloadtracker.database.AppDatabase
import com.example.workloadtracker.enteties.Plan

class PlanAdapter(private val db: AppDatabase, private val plans: MutableList<Plan>) : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtDiscipline.text = db.disciplineDao().getById(plans[position].idDisc).name
        holder.txtLessonType.text = db.lessonTypeDao().getById(plans[position].idLT).name
        holder.txtEducationFormAndRate.text =
            "${db.educationFormDao().getById(plans[position].idEF).name} form/${db.rateDao().getById(plans[position].idRate).value} rate"
        holder.txtPlanHours.text = getPlanHoursText(position)
    }

    private fun getPlanHoursText(position: Int): CharSequence? {
        val deductedHours = getDeductedHours(
            plans[position].idDisc,
            plans[position].idLT,
            plans[position].idEF
        )

        val planHours = plans[position].hours

        return "$deductedHours/$planHours h"
    }

    private fun getDeductedHours(
        idDisc: Int,
        idLT: Int,
        idEF: Int
    ): Int = db.workloadDao().getAll().filter {
        it.idDisc == idDisc &&
                it.idLT == idLT &&
                it.idEF == idEF
    }
        .map { it.hours }
        .sum()

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