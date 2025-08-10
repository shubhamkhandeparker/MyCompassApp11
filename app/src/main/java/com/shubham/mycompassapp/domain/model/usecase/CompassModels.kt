package com.shubham.mycompassapp.domain.model.usecase

/**
 * Represent a single Compass reading from device
 */


data class CompassReading(
    val azimuth :Float , //0-360(North=0, East =90 )
    val pitch : Float, //Device tilt forward(-1) / backward(+)
    val roll :Float,  //Device tilt left (-) /right (+)
    val accuracy : SensorAccuracy ,  //How reliable this  reading is
val timestamp :Long    //When this reading was taken
)

/**
 * Sensor accuracy levels from Android SensorManager
 */

enum class SensorAccuracy (val displayName :String, val value : Int){
    UNRELIABLE ("Poor",0),          //Red indicator
    LOW("Low",1),                   //Orange indicator
    MEDIUM("Medium",2),             //Yellow indicator
    HIGH("High",3);                 //Green indicator

    companion object{
        fun fromValue (value:Int): SensorAccuracy{
            return values().find { it.value==value }?:UNRELIABLE
        }
    }
}

/**
 * Compass operating modes
 */

enum class CompassMode(val displayName: String){
    MAGNETIC("Magnetic"),  //Point to Magnetic North
    TRUE_NORTH("True North")  //Point to geographic North
}

/**
 * UI State for the Compass Screen
 */

data class CompassUiState (
    val bearing :Float=0f,
    val accuracy: SensorAccuracy= SensorAccuracy.UNRELIABLE,
    val compassMode : CompassMode = CompassMode.MAGNETIC,
    val isCalibrating :Boolean =false,
    val sensorAvailable : Boolean = false,
    val errorMessage:String?= null
)