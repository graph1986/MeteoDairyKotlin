package com.example.meteodairykotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meteodairykotlin.R
import com.example.meteodairykotlin.databinding.ActivityMainBinding
import com.example.meteodairykotlin.models.City
import com.example.meteodairykotlin.models.DayMeteo
import com.example.meteodairykotlin.ui.adapters.MainAdapter
import com.example.meteodairykotlin.ui.adapters.PickerCityAdapter
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity  : AppCompatActivity(),
    MainContract.View, PickerCityAdapter.OnClickCityListner {
    @Inject
    lateinit var presenter: MainContract.Presenter
    private lateinit var alertDialog: AlertDialog
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Picasso.get().load("https://st7.gismeteo.ru/static/diary/img/sun.png").resize(40, 40).centerInside()
            .into(imgCloudSun)
        Picasso.get().load("https://st7.gismeteo.ru/static/diary/img/sunc.png").resize(40, 40).centerInside()
            .into(imgCloudSunc)
        Picasso.get().load("https://st7.gismeteo.ru/static/diary/img/suncl.png").resize(40, 40).centerInside()
            .into(imgCloudSuncl)
        Picasso.get().load("https://st7.gismeteo.ru/static/diary/img/dull.png").resize(40, 40).centerInside()
            .into(imgCloudDull)
        Picasso.get().load("https://st4.gismeteo.ru/static/diary/img/storm.png").resize(40, 40).centerInside()
            .into(imgEffectStorm)
        Picasso.get().load("https://st8.gismeteo.ru/static/diary/img/rain.png").resize(40, 40).centerInside()
            .into(imgEffectRain)
        Picasso.get().load("https://st8.gismeteo.ru/static/diary/img/snow.png").resize(40, 40).centerInside()
            .into(imgEffectSnow)
        val btnPickerCity: Button = findViewById(R.id.btnCity)
        btnPickerCity.setOnClickListener(View.OnClickListener {
            presenter.onClickCity()
        })
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun showError() {

    }

    override fun showWeather(days: List<DayMeteo>, city: String) {
        binding?.run {
            txtDataEmpty.visibility = if (days.isEmpty()) View.VISIBLE else View.INVISIBLE
            recyclerView.visibility = if (days.isEmpty()) View.INVISIBLE else View.VISIBLE
            progressBar.visibility = View.GONE
            recyclerView.adapter = MainAdapter(days)
            recyclerView.scrollToPosition(5)
            txtCity.text = city
        }
    }

    override fun showPickerCity(cities: List<City>) {
        val inflanter: LayoutInflater = layoutInflater
        val dailogView: View = inflanter.inflate(R.layout.dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dailogView)
        val recyclerViewCity: RecyclerView = dailogView.findViewById(R.id.recyleViewCity)
        recyclerViewCity.layoutManager = LinearLayoutManager(this)
        recyclerViewCity.adapter = PickerCityAdapter(cities, this)
        alertDialog = builder.create()
        alertDialog.show()
    }

    override fun showProgressBar() {
        binding?.recyclerView?.adapter = null
        binding?.progressBar?.visibility = View.VISIBLE
    }

    override fun pickCity(city: City) {
        presenter.changeCity(city)
        alertDialog.cancel()
    }
}