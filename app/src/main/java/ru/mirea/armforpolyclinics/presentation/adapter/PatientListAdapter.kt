package ru.mirea.armforpolyclinics.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.armforpolyclinics.R
import ru.mirea.armforpolyclinics.domain.entity.Genders
import ru.mirea.armforpolyclinics.domain.entity.Patient

class PatientListAdapter: ListAdapter<Patient,PatientListAdapter.PatientItemViewHolder>(PatientCallback()) {



    var onPatientClickListener: OnPatientClickListener ? = null
    var onMenuClickListener:OnMenuClickListener ? = null
    class PatientItemViewHolder(view: View):RecyclerView.ViewHolder(view){
        val fullName: TextView = view.findViewById(R.id.full_name_textview)
        val snils: TextView = view.findViewById(R.id.snils_textview)
        val policy: TextView = view.findViewById(R.id.policy_textview)
        val phone: TextView = view.findViewById(R.id.phone_textview)
        val menu: TextView = view.findViewById(R.id.options_menu)
        val space: Space = view.findViewById(R.id.menu_space)
    }



    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PatientItemViewHolder, position: Int) {
        val patient = getItem(position)
        holder.fullName.text = "${patient.name} ${patient.surname} ${patient.patronymic}"
        holder.snils.text = patient.snils
        holder.policy.text = patient.policyNumber.toString()
        holder.phone.text = "+7${patient.phoneNumber}"

        holder.menu.setOnClickListener {
            onMenuClickListener?.onMenuClick(patient,holder.space)
        }

        holder.itemView.setOnClickListener {
            onPatientClickListener?.onPatientClickListener(patient)
        }
    }

    interface OnPatientClickListener{
        fun onPatientClickListener(patient: Patient)
    }

    interface OnMenuClickListener{
        fun onMenuClick(patient: Patient, view:View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientItemViewHolder {
        val itemView =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.patient_item, parent, false)
        return PatientItemViewHolder(itemView)
    }
}