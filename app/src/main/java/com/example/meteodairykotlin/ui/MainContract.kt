package com.example.meteodairykotlin.ui

import com.example.meteodairykotlin.models.City
import com.example.meteodairykotlin.models.DayMeteo

interface MainContract {
    interface View{
        fun  showError()

        fun showWeather(days:List<DayMeteo>, city:String)

        fun showPickerCity(cities:List<City>)

        fun showProgressBar()
    }

    interface Presenter{
        fun onStart(view:View)

        fun onStop()

        fun changeCity(city:City)

        fun onClickCity()
    }
}