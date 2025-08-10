package com.shubham.mycompassapp.presentation.ui.components

import android.service.controls.templates.ControlButton
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shubham.mycompassapp.domain.model.usecase.CompassMode

@Composable
fun ControlButtonsRow(
    compassMode: CompassMode,
    isCalibrating: Boolean,
    onModeToggle: () -> Unit,
    onCalibrate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        //Magnetic /True North Toggle Button
        ControlButton(
            text = compassMode.displayName,
            isActive = true,
            onClick = onModeToggle,
            modifier = Modifier.weight(2f)
        )

        //Calibrate Button
        ControlButton(
            text = if (isCalibrating) "C..." else "Calibrate",
            isActive = isCalibrating,
            onClick = onCalibrate,
            modifier = Modifier.weight(1f)
        )
    }

}

@Composable
fun ControlButton(
    text: String,
    isActive :Boolean,
    onClick : ()-> Unit,
    modifier : Modifier = Modifier
){

    Button(
        onClick=onClick,
        modifier=modifier.height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors= ButtonDefaults.buttonColors(containerColor = if(isActive){
            Color(0x4D667eea) //Active more blue
        }
        else{
            Color(0x261a202c) //Inactive same as Cards

        }
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp)
    ){
        Text(
            text=text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = if(isActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}