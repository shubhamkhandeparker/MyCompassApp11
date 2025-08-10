package com.shubham.mycompassapp.data.repository.sensor

import com.shubham.mycompassapp.data.repository.sensor.MagneticFieldSensor
import com.shubham.mycompassapp.domain.model.usecase.CompassReading
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompassRepository @Inject constructor(
    private val magneticFieldSensor: MagneticFieldSensor
) {

    fun getCompassReadings(): Flow<CompassReading> {
        return magneticFieldSensor.getCompassReading()
    }

    fun hasSensors():Boolean {
        return magneticFieldSensor.hasSensors()
    }
    fun startListening():Flow<CompassReading>{
        return getCompassReadings()
    }

    //We Can Add More Method later like :
    // fun CalibrateSensor()
    //fun setCompassMode(mode:CompassMode)

}