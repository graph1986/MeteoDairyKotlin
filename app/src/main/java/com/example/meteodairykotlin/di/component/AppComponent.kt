package com.example.meteodairykotlin.di.component

import android.content.Context
import com.example.meteodairykotlin.core.ApplicationMeteoDairyKotlin
import com.example.meteodairykotlin.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent{
fun inject(app:ApplicationMeteoDairyKotlin)
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun getContext(conext:Context):Builder

        fun build():AppComponent
    }
    }

