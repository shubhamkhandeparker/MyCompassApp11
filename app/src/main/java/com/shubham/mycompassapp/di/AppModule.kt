package com.shubham.mycompassapp.di

import android.content.Context
import com.shubham.mycompassapp.data.repository.sensor.CompassRepository
import com.shubham.mycompassapp.data.repository.sensor.MagneticFieldSensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMagneticFieldSensor(@ApplicationContext context: Context): MagneticFieldSensor{
        return MagneticFieldSensor(context)
    }

    @Provides
    @Singleton
    fun provideCompassRepository(magneticFieldSensor: MagneticFieldSensor): CompassRepository{
        return CompassRepository(magneticFieldSensor)
    }

}