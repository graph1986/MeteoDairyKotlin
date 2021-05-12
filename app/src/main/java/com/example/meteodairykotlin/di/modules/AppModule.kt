package com.example.meteodairykotlin.di.modules

import android.content.Context
import androidx.room.Room
import com.example.meteodairykotlin.database.AppDataBase
import com.example.meteodairykotlin.network.NetworkClient
import com.example.meteodairykotlin.tools.DaysParseHelper
import com.example.meteodairykotlin.ui.MainActivity
import com.example.meteodairykotlin.ui.MainContract
import com.example.meteodairykotlin.ui.MainPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class AppModule() {

    @Singleton
    @Binds
    abstract fun presenter(presenter: MainPresenter): MainContract.Presenter

  companion  object{
      @JvmStatic
      @Provides
      @Singleton
      fun appDataBase(context: Context): AppDataBase =
          Room.databaseBuilder(context, AppDataBase::class.java, "meteoDB").build()
  }

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}