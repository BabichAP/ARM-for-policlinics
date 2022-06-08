package ru.mirea.armforpolyclinics.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.mirea.armforpolyclinics.domain.entity.Patient

class PatientCallback: DiffUtil.ItemCallback<Patient>() {
    override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem == newItem
    }
}