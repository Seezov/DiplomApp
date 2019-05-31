package com.example.workloadtracker.adapters

import android.annotation.SuppressLint
import android.view.*
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.workloadtracker.R
import com.example.workloadtracker.database.AppDatabase
import com.example.workloadtracker.enteties.Workload
import java.text.SimpleDateFormat

class WorkloadAdapter(private val db: AppDatabase, private val workloads: MutableList<Workload>) :
    RecyclerView.Adapter<WorkloadAdapter.ViewHolder>() {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtDiscipline.text = db.disciplineDao().getById(workloads[position].idDisc).name
        holder.txtLessonType.text = db.lessonTypeDao().getById(workloads[position].idLT).name

        holder.txtWorkloadInfo.text = "${SimpleDateFormat("dd/MM/yyyy").format(workloads[position].date)}\n" +
                "${workloads[position].week} тиждень/" +
                "${workloads[position].pairIndex} пара/" +
                "${workloads[position].hall} аудиторія/" +
                "${db.educationFormDao().getById(workloads[position].idEF).name} форма навчання"

        holder.txtGroupCode.text = db.groupCodeDao().getById(
            db.workloadDao().getById(workloads[position].id).idGC
        ).name
        holder.txtLecturer.text = db.lecturerDao().getById(
            db.workloadDao().getById(workloads[position].id).idLecturer
        ).name
        holder.txtWorkloadHours.text = "${db.workloadDao().getById(workloads[position].id).hours} г."
    }

    interface OnWorkloadListener {
        fun onWorkloadLongClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_workload, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return workloads.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(adapterPosition, 121, 0, "Редагувати")
            menu?.add(adapterPosition, 122, 1, "Видалити")
        }

        val txtDiscipline: TextView = itemView.findViewById(R.id.txtDiscipline)
        val txtLessonType: TextView = itemView.findViewById(R.id.txtLessonType)
        val txtWorkloadInfo: TextView = itemView.findViewById(R.id.txtWorkloadInfo)
        val txtGroupCode: TextView = itemView.findViewById(R.id.txtGroupCode)
        val txtLecturer: TextView = itemView.findViewById(R.id.txtLecturer)
        val txtWorkloadHours: TextView = itemView.findViewById(R.id.txtWorkloadHours)
        val cardView: CardView = itemView.findViewById(R.id.cardView)

        init {
            cardView.setOnCreateContextMenuListener(this)
        }
    }
}