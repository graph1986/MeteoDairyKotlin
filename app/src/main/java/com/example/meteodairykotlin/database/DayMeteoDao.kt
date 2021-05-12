package com.example.meteodairykotlin.database

import androidx.annotation.IntegerRes
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.meteodairykotlin.models.DayMeteo
import io.reactivex.Flowable

@Dao
interface DayMeteoDao {

    @Query("SELECT * FROM (SELECT * FROM DayMeteo WHERE cityId=:city  ORDER BY id DESC  LIMIT 11) ORDER BY id")
    fun getElevenDays(city:Int):Flowable<List<DayMeteo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(days:List<DayMeteo>)

}