package com.example.meteodairykotlin.core

import android.app.Application
import com.example.meteodairykotlin.di.component.AppComponent
import com.example.meteodairykotlin.di.component.DaggerAppComponent
import com.example.meteodairykotlin.di.modules.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ApplicationMeteoDairyKotlin : Application(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .getContext(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}