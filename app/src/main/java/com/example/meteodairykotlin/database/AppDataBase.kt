package com.example.meteodairykotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.meteodairykotlin.models.DayMeteo
import javax.inject.Inject
import javax.inject.Singleton


@Database(entities = [DayMeteo::class], version = 1)
abstract class AppDataBase  : RoomDatabase() {
    abstract fun dayMeteoDao(): DayMeteoDao
}