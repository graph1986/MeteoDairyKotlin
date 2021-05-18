package com.example.meteodairykotlin.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.meteodairykotlin.R
import com.example.meteodairykotlin.models.DayMeteo
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter(val days: List<DayMeteo>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.day, parent, false))
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 5) {
            holder.contDay.setBackgroundColor(Color.YELLOW)
        }
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, days.get(position).month - 1)
        val month: String = SimpleDateFormat("MMMM", Locale("ru")).format(calendar.time)
        holder.run {
            txtNumberDay.setText(
                days.get(position).numberDay.toString() + "\n" + month + "\n" + days.get(
                    position
                ).year.toString()
            )
            txtTemperature.setText(days.get(position).temperature)
            Picasso.get().load("https://" + days.get(position).urlCloud).resize(60, 60)
                .centerInside()
                .into(this.imgCloud)
            Picasso.get().load("https://" + days.get(position).urlEffect).resize(60, 60)
                .centerInside()
                .into(this.imgEffect)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtNumberDay: TextView = itemView.findViewById(R.id.txtNumberDay)
        var txtTemperature: TextView = itemView.findViewById(R.id.txtTemperature)
        var imgCloud: ImageView = itemView.findViewById(R.id.imgCloud)
        var imgEffect: ImageView = itemView.findViewById(R.id.imgEffect)
        var contDay: ConstraintLayout = itemView.findViewById(R.id.contDay)
    }
}