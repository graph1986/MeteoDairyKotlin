package com.example.meteodairykotlin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meteodairykotlin.R
import com.example.meteodairykotlin.models.City
import kotlinx.android.synthetic.main.activity_main.view.*

class PickerCityAdapter(val cities:List<City>,val listner:OnClickCityListner) : RecyclerView.Adapter<PickerCityAdapter.ViewHolder>() {
interface OnClickCityListner{
    fun pickCity(city:City)
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.city,parent,false))
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtCity.setText(cities.get(position).name)
        holder.txtCity.setOnClickListener {
            listner.pickCity(cities.get(position))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCity:TextView=itemView.findViewById(R.id.txtDialogCity)
    }
}