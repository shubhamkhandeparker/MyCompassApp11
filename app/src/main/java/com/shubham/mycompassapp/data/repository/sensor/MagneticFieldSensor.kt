package com.shubham.mycompassapp.data.repository.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.shubham.mycompassapp.domain.model.usecase.CompassReading
import com.shubham.mycompassapp.domain.model.usecase.SensorAccuracy
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.*


class MagneticFieldSensor(private val context: Context) {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)


    //Arrays to Store Sensor Reading
    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)


    //Tracking if we have valid reading from both the sensor
    private var hasAccelerometerRading = false
    private var hasMagnetormeterReading = false

    fun getCompassReading(): Flow<CompassReading> = callbackFlow {

        val sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                when (event.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> {
                        System.arraycopy(event.values, 0, accelerometerReading, 0, 3)
                        hasAccelerometerRading = true

                        android.util.Log.d("CompassApp","Accelerometer accuracy :${event.accuracy}")
                    }

                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        System.arraycopy(event.values, 0, magnetometerReading, 0, 3)
                        hasMagnetormeterReading = true
                        android.util.Log.d("CompassApp","Magnetometer accuracy :${event.accuracy}")
                    }
                }


                    //Only calculate compass reading when we have both sensor readings
                    if (hasAccelerometerRading && hasMagnetormeterReading) {
                        calculateCompassReading(event.accuracy)?.let { reading ->
                            android.util.Log.d("CompassApp","Final accuracy :${reading.accuracy}")
                            trySend(reading)
                        }
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                //Handel accuracy changes if needed
                }
        }

            //Register Sensors
            accelerometer?.let{
                sensorManager.registerListener(
                    sensorEventListener,
                    it,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
            }

            magnetometer?.let{
                sensorManager.registerListener(
                    sensorEventListener,
                    it,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
            }

            //Cleanup when FLow is canceled

            awaitClose{
                sensorManager.unregisterListener(sensorEventListener)
            }
        }



    private fun calculateCompassReading(accuracy: Int): CompassReading? {
        //Get rotation Matrix from accelerometer & magnetometer
        val success = SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )
        if (!success) return null

        //Get Orientation Angles
        SensorManager.getOrientation(rotationMatrix, orientationAngles)


        //Convert azimuth from radius to degrees and normalize to 0-360
        val azimuthRadius = orientationAngles[0]
        val azimuthDegrees = Math.toDegrees(azimuthRadius.toDouble()).toFloat()
        val normalizedAzimuth = if (azimuthDegrees < 0) {
            azimuthDegrees + 360f
        } else {
            azimuthDegrees
        }
        val pitch = Math.toDegrees(orientationAngles[1].toDouble()).toFloat()
        val roll = Math.toDegrees(orientationAngles[2].toDouble()).toFloat()

        return CompassReading(
            azimuth = normalizedAzimuth,
            pitch = pitch,
            roll = roll,
            accuracy = SensorAccuracy.fromValue(accuracy),
            timestamp = System.currentTimeMillis()
        )

    }


    fun hasSensors(): Boolean {
        return accelerometer != null && magnetometer != null
    }

}