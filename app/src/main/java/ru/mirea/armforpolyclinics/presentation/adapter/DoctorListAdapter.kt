package ru.mirea.armforpolyclinics.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.armforpolyclinics.R
import ru.mirea.armforpolyclinics.domain.entity.Doctor
import ru.mirea.armforpolyclinics.domain.entity.Profession

class DoctorListAdapter: ListAdapter<Doctor,DoctorListAdapter.DoctorItemViewHolder>(DoctorCallback()) {


    class DoctorItemViewHolder(view: View):RecyclerView.ViewHolder(view){
        val fullName: TextView = view.findViewById(R.id.full_name_textview)
        val progression: TextView = view.findViewById(R.id.profession_textview)
        val cabinet: TextView = view.findViewById(R.id.cabinet_textview)
    }



    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DoctorItemViewHolder, position: Int) {
        val doctor = getItem(position)
        holder.fullName.text = doctor.name
        holder.progression.text = when(doctor.profession){
            Profession.Dentist -> "Дантист"
            Profession.Surgeon -> "Хирург"
        }
        holder.cabinet.text = doctor.cabinet
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.doctor_item, parent, false)
        return DoctorItemViewHolder(itemView)
    }
}