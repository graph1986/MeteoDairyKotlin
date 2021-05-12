package com.example.meteodairykotlin.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DayMeteo(
    val numberDay: Int,
    val temperature: String,
    val urlCloud: String,
    val urlEffect: String,
    val month: Int,
    val year: Int,
    val cityId: Int
) {
    @PrimaryKey
    var id:Long=setId()
    fun setId():Long{
      val calendar:Calendar= Calendar.getInstance()
        calendar.set(year,month,numberDay)
        id=calendar.timeInMillis
       return id
    }
}