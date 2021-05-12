package com.example.meteodairykotlin.ui

import com.example.meteodairykotlin.database.AppDataBase
import com.example.meteodairykotlin.models.City
import com.example.meteodairykotlin.models.DayMeteo
import com.example.meteodairykotlin.network.NetworkClient
import com.example.meteodairykotlin.tools.DaysParseHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val daysParseHelper: DaysParseHelper,
    private val appDataBase: AppDataBase,
    private val networkClient: NetworkClient
) : MainContract.Presenter {
    private var view: MainContract.View? = null
    private var year: Int = 0
    private var pastYear = 0
    private var month: Int = 0
    private var pastMonth = 0
    private var cities = mutableListOf<City>()
    private lateinit var city: City
    lateinit var compositeDisposable: CompositeDisposable


    override fun onStart(view: MainContract.View) {
        this.view = view
        createCityList()
        compositeDisposable = CompositeDisposable()
        city = cities[0]
        year = Calendar.getInstance().get(Calendar.YEAR)
        month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        pastYear = calendar.get(Calendar.YEAR)
        pastMonth = calendar.get(Calendar.MONTH) + 1
        getDays(year, month)
    }

    override fun onStop() {
        this.view = null
        compositeDisposable.dispose()
    }

    override fun changeCity(city: City) {
        this.city = city
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
        getDays(year, month)
    }

    override fun onClickCity() {
        view?.showPickerCity(cities)
    }

    private fun getDays(year: Int, month: Int) {
        view?.showProgressBar()
        appDataBase.dayMeteoDao().getElevenDays(city.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ dayMeteoList ->
                if (!dayMeteoList.isEmpty()) {
                    view?.showWeather(dayMeteoList, city.name)
                }
                if (dayMeteoList.size > 10) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_MONTH, -5)
                    val lastFiveDay = calendar[Calendar.DAY_OF_MONTH]
                    calendar.add(Calendar.DAY_OF_MONTH, +10)
                    val futureFiveDay = calendar[Calendar.DAY_OF_MONTH]
                    if (dayMeteoList[0].numberDay == lastFiveDay && dayMeteoList[10].numberDay == futureFiveDay
                    ) {
                        view?.showWeather(dayMeteoList, city.name)
                    } else if (dayMeteoList[10].numberDay != futureFiveDay) {
                        loadWeather(year, month)
                    } else if (dayMeteoList[0].numberDay != lastFiveDay) {
                        loadDairy(year, month)
                    }
                } else {
                    if (pastMonth == Calendar.DECEMBER) {
                        loadDairy(pastYear, pastMonth)
                    } else {
                        loadDairy(year, pastMonth)
                    }
                }
            }, { view?.showError() })
            .also { compositeDisposable::add }
    }

    private fun loadDairy(year: Int, month: Int) {
        val dairyDisposable =
            networkClient.apiClient.loadDiary(year, month, city.id)
                .subscribeOn(Schedulers.io())
                .subscribe { s, throwable ->
                    if (throwable != null) {
                        view?.showError()
                    } else {
                        val days =
                            daysParseHelper.parseDiary(s!!, city.id, year, month)
                        if (!days.isEmpty()) {
                            appDataBase.dayMeteoDao().insertAll(days)
                        }
                    }
                }
        compositeDisposable.add(dairyDisposable)
    }

    private fun loadWeather(year: Int, month: Int) {
        val weatherDisposable =
            networkClient.apiClient.loadWeather(city.urlName, city.id)
                .subscribeOn(Schedulers.io())
                .subscribe { s, throwable ->
                    if (throwable != null) {
                        view?.showError()
                    } else {
                        val days =
                            daysParseHelper.parseWeather(s!!, city.id, year, month)
                        if (!days.isEmpty()) {
                            appDataBase.dayMeteoDao().insertAll(days)
                        }
                    }
                }
        compositeDisposable.add(weatherDisposable)
    }

    private fun createCityList() {
        val kazan = City(4364, "-kazan-", "Казань")
        val krasnysteklovar = City(201904, "-krasny-steklovar-", "Красный-Стекловар")
        val kukeyevo = City(205398, "-kukeyevo-", "Кукеево")
        val tatarskiyesaraly = City(205002, "-tatarskiye-saraly-", "Татарские Саралы")
        val ilet = City(201468, "-ilet-", "Илеть")
        val shali = City(205367, "-shali-", "Шали")
        val matyushino = City(204985, "-matyushino-", "Матюшино")
        val dubyazy = City(204605, "-dubyazy-", "Дубъязы")
        val bima = City(204970, "-bima-", "Бима")
        val oktyabrsky = City(204568, "-oktyabrsky-", "Октябрьский")
        cities = arrayListOf()
        cities.add(kazan)
        cities.add(krasnysteklovar)
        cities.add(kukeyevo)
        cities.add(tatarskiyesaraly)
        cities.add(ilet)
        cities.add(shali)
        cities.add(matyushino)
        cities.add(dubyazy)
        cities.add(bima)
        cities.add(oktyabrsky)
    }
}