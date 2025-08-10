package com.shubham.mycompassapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.mycompassapp.data.repository.sensor.CompassRepository
import com.shubham.mycompassapp.domain.model.usecase.CompassUiState
import com.shubham.mycompassapp.domain.model.usecase.CompassMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class CompassViewModel @Inject constructor(
    private val compassRepository: CompassRepository
) : ViewModel() {

    //Private Mutable State
    private val _uiState = MutableStateFlow(CompassUiState())

    //public rad only state for UI
    val uiState : StateFlow<CompassUiState> = _uiState.asStateFlow()

    private var lastBearing : Float =0f


    init {
        checkSensorAvailability()
    }

    fun startCompass(){
        viewModelScope.launch {
            compassRepository.startListening()
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Sensor error :${exception.message}"
                    )
                }
                .collect {compassReading ->

                    _uiState.value = _uiState.value.copy(
                        bearing =compassReading.azimuth,
                        accuracy = compassReading.accuracy,
                        sensorAvailable = true,
                        errorMessage = null
                    )
                }

        }
    }




    fun stopCompass(){
        //Flow will automatically stop when viewModelScope is Cancelled
    }
    fun toggleCompassMode(){
        val currentMode = _uiState.value.compassMode

        val newMode = if(currentMode==CompassMode.MAGNETIC){
            CompassMode.TRUE_NORTH
        }else{
            CompassMode.MAGNETIC
        }
        _uiState.value = _uiState.value.copy(compassMode = newMode)
    }

    fun startCalibration (){
        _uiState.value = _uiState.value.copy(isCalibrating = true)

        //Auto Stop calibration after 3 second
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000)
            _uiState.value = _uiState.value.copy(isCalibrating = false)
        }
    }

    private fun checkSensorAvailability(){

        val available = compassRepository.hasSensors()
        _uiState.value = _uiState.value.copy(sensorAvailable = available)

        if(!available){
            _uiState.value=_uiState.value.copy(
                errorMessage = "Compass Sensor not available on this Device "
            )
        }


    }


}