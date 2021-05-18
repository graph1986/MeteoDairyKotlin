package com.example.meteodairykotlin.tools

import com.example.meteodairykotlin.models.DayMeteo
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaysParseHelper @Inject constructor() {
    fun parseDiary(s: String, city: Int, year: Int, month: Int): List<DayMeteo> {
        val dayMeteoList = arrayListOf<DayMeteo>()
        val document: org.jsoup.nodes.Document = Jsoup.parse(s)
        val table: org.jsoup.nodes.Element? =
            document.getElementById("data_block").getElementsByTag("table").first()
        if (table != null) {
            val days: Elements = table.getElementsByTag("tbody").get(1).getElementsByTag("tr")
            for (i in days) {
                val numberDay: Int = i.getElementsByTag("td").get(0).text().toInt()
                val temperature: String = i.getElementsByTag("td").get(1).text()
                var urlCloud = ""
                var urlEffect = ""
                if (i.getElementsByTag("td").get(3).childrenSize() != 0) {
                    urlCloud =
                        i.getElementsByTag("td").get(3).getElementsByTag("img").get(0).attr("src")
                }
                if (i.getElementsByTag("td").get(4).childrenSize() != 0) {
                    urlEffect =
                        i.getElementsByTag("td").get(4).getElementsByTag("img").get(0).attr("src")
                }
                val dayMeteo =
                    DayMeteo(numberDay, temperature, urlCloud, urlEffect, month, year, city)
                dayMeteoList.add(dayMeteo)
            }
            return dayMeteoList
        } else {
            return dayMeteoList
        }
    }

    fun parseWeather(s: String, city: Int, _year: Int, _month: Int): List<DayMeteo> {
        var year = _year
        var month = _month
        val dayMeteoList = arrayListOf<DayMeteo>()
        val document: org.jsoup.nodes.Document = Jsoup.parse(s)
        val date: org.jsoup.nodes.Element =
            document.getElementsByClass("widget__row widget__row_date").first()
        val days: Elements = date.getElementsByClass("widget__item")
        val effect: org.jsoup.nodes.Element =
            document.getElementsByClass("widget__row widget__row_table widget__row_icon").first()
        val effects: Elements = effect.getElementsByClass("widget__item")
        val templineTemperature: org.jsoup.nodes.Element =
            document.getElementsByClass("templine w_temperature").first()
        val value: org.jsoup.nodes.Element =
            templineTemperature.getElementsByClass("values").first()
        val values: Elements = value.getElementsByClass("value")
        for (i in 0 until 6) {
            val temperature: String =
                values[i].getElementsByClass("unit unit_temperature_c").get(0).text()
            val numberDay: Int =
                days[i].getElementsByTag("span")[0].text().replace("\\D+".toRegex(), "")
                    .toInt()
            if (i > 0) {
                if (numberDay < days[i - 1].getElementsByTag("span")[0].text()
                        .replace("\\D+".toRegex(), "").toInt()
                ) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.MONTH, +1)
                    year = calendar[Calendar.YEAR]
                    month = calendar[Calendar.MONTH] + 1
                }
            }
            var urlEffect: String = effects[i].getElementsByTag("span").get(0).attr("data-text")
            with(urlEffect) {
                urlEffect = when {
                    contains("гроза") -> "//st4.gismeteo.ru/static/diary/img/storm.png"
                    contains("дождь") -> "//st8.gismeteo.ru/static/diary/img/rain.png"
                    contains("снег") -> "//st8.gismeteo.ru/static/diary/img/snow.png"
                    else -> ""
                }
            }

            var urlCloud =
                effects[i].getElementsByTag("span")[0].attr("data-text")
            with(urlCloud) {
                urlCloud = when {
                    contains("Малооблачно") -> "//st4.gismeteo.ru/static/diary/img/sunc.png"
                    contains("Ясно") -> "//st7.gismeteo.ru/static/diary/img/sun.png"
                    contains("Переменная облачность") -> "//st6.gismeteo.ru/static/diary/img/suncl.png"
                    contains("Пасмурно") -> "//st7.gismeteo.ru/static/diary/img/dull.png"
                    else -> ""
                }
            }

            val dayMeteo: DayMeteo =
                DayMeteo(numberDay, temperature, urlCloud, urlEffect, month, year, city)
            dayMeteoList.add(dayMeteo)
        }
        return dayMeteoList
    }
}