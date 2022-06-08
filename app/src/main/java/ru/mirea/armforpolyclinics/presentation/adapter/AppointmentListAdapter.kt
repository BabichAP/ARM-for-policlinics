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
import ru.mirea.armforpolyclinics.domain.entity.Appointment
import ru.mirea.armforpolyclinics.domain.entity.Genders
import ru.mirea.armforpolyclinics.domain.entity.Patient
import ru.mirea.armforpolyclinics.domain.entity.Profession

class AppointmentListAdapter: ListAdapter<Appointment,AppointmentListAdapter.AppointmentViewHolder>(AppointmentCallback()) {




    var onAppointmentClickListener: OnAppointmentClickListener ? = null
    var onMenuClickListener:OnMenuClickListener ? = null


    class AppointmentViewHolder(view: View):RecyclerView.ViewHolder(view){
        val date: TextView = view.findViewById(R.id.date_textview)
        val doctorName: TextView = view.findViewById(R.id.doctor_name_textview)
        val profession: TextView = view.findViewById(R.id.profession_textview)
        val cabinet: TextView = view.findViewById(R.id.cabinet_textview)
        val menu: TextView = view.findViewById(R.id.appoint_menu)
        val space: Space = view.findViewById(R.id.menu_space)
    }



    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = getItem(position)
        holder.date.text = appointment.dateOfAppointment
        holder.doctorName.text = appointment.doctorName
        holder.cabinet.text = appointment.cabinet
        holder.profession.text = when(appointment.profession){
            Profession.Dentist -> "Дантист"
            Profession.Surgeon -> "Хирург"
        }

        holder.menu.setOnClickListener {
            onMenuClickListener?.onMenuClick(appointment,holder.space)
        }

        holder.itemView.setOnClickListener {
            onAppointmentClickListener?.onAppointmentClickListener(appointment)
        }
    }

    interface OnAppointmentClickListener{
        fun onAppointmentClickListener(appointment: Appointment)
    }

    interface OnMenuClickListener{
        fun onMenuClick(appointment: Appointment, view:View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.appointment_item, parent, false)
        return AppointmentViewHolder(itemView)
    }
}