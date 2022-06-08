package ru.mirea.armforpolyclinics.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.mirea.armforpolyclinics.domain.entity.Doctor

class DoctorCallback: DiffUtil.ItemCallback<Doctor>() {
    override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
        return oldItem == newItem
    }
}