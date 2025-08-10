package com.shubham.mycompassapp.presentation.ui.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import com.shubham.mycompassapp.domain.model.usecase.SensorAccuracy
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI
import androidx.compose.animation.core.FastOutSlowInEasing


@Composable
fun CompassRose(
    bearing: Float,
    accuracy: SensorAccuracy,
    modifier: Modifier = Modifier
) {
    //smooth animation for needle rotation
    val animatedBearing by animateFloatAsState(
        targetValue = bearing,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = modifier.size(280.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(280.dp)) {
            val center = size.center
            val radius = size.minDimension / 2


            //Draw compass background circle
            drawCompassBackground(center, radius)


            //Draw direction markers (N,E,S,W)
            drawDirectionMarkers(center, radius)


            //Draw degree markers
            drawDegreeMarkers(center, radius)

            //Draw compass needles (rotated based on bearings)
            rotate(degrees = animatedBearing, pivot = center) {
                drawCompassNeedle(center, radius)
            }

            //Draw center dot
            drawCompassCenter(center)

        }
    }
}



//Helper function : Drawing compass background
private fun DrawScope.drawCompassBackground(center: Offset, radius: Float) {

    //Outer circle (dark)
    drawCircle(
        color = Color(0xFF2d3748),
        radius = radius,
        center = center
    )

    //Inner Circle
    drawCircle(
        color = Color(0xFF1a202c),
        radius = radius * 0.9f,
        center = center
    )

}

//Helper function : Draw N,E,S,W Markers

private fun DrawScope.drawDirectionMarkers(center: Offset, radius: Float) {

    val directions = listOf(
        "N" to 0f,      //North = 0 degrees
        "E" to 90f,     //East = 90 degrees
        "S" to 180f,    //South =180 degrees
        "W" to 270f     //West =270 degrees

    )

    directions.forEach { (letter, angle) ->
        val angleInRadians = (angle - 90f) * PI / 180f       //-90 to start from top
        val textRadius = radius * 0.8f

        val x = center.x + cos(angleInRadians).toFloat() * textRadius
        val y = center.y + sin(angleInRadians).toFloat() * textRadius

        //Draw direction letter
        drawContext.canvas.nativeCanvas.drawText(
            letter,
            x,
            y,
            android.graphics.Paint().apply {
                color =
                    if (letter == "N") android.graphics.Color.RED else android.graphics.Color.WHITE
                textSize = 48f
                textAlign = android.graphics.Paint.Align.CENTER
                isFakeBoldText = true
            }
        )

    }
}


//Helper Function : Draw degree Marker(small lines )

private fun DrawScope.drawDegreeMarkers(center: Offset, radius: Float) {
    for (degree in 0 until 360 step 30) {

        val angleInRadians = (degree - 90f) * PI / 180f
        val startRadius = radius * 0.85f
        val endRadius = radius * 0.9f

        val startX = center.x + cos(angleInRadians).toFloat() * startRadius
        val startY = center.y + sin(angleInRadians).toFloat() * startRadius

        val endX = center.x + cos(angleInRadians).toFloat() * endRadius
        val endY = center.y + sin(angleInRadians).toFloat() * endRadius

        drawLine(
            color = Color.White,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = 3f
        )

    }
}

//Helper function : Draw compass needle
private fun DrawScope.drawCompassNeedle(center: Offset, radius: Float) {

    val needleLength = radius * 0.7f

    //North Pointer ( red)
    drawLine(
        color = Color(0xFFe53e3e),
        start = center,
        end = Offset(center.x, center.y - needleLength),
        strokeWidth = 8f
    )

    //South pointer (gray )

    drawLine(
        color = Color(0xFF4a5568),
        start = center,
        end = Offset(center.x, center.y + needleLength * 0.6f),
        strokeWidth = 6f
    )

}


//Helper Function  : Draw center dot
private fun DrawScope.drawCompassCenter(center: Offset){
    drawCircle(
        color = Color.White,
        radius = 12f,
        center = center
    )
}
