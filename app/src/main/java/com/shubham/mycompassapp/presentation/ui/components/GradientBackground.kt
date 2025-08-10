package com.shubham.mycompassapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.shubham.mycompassapp.ui.theme.PurpleEnd
import com.shubham.mycompassapp.ui.theme.PurpleStart

@Composable
fun GradientBackground(
    modifier : Modifier = Modifier.Companion
){
    Box(
        modifier = modifier.fillMaxSize().background(
            Brush.Companion.linearGradient(
                colors = listOf(PurpleStart, PurpleEnd),
                start = Offset(0f, 0f),
                end = Offset(1000f, 1000f)
            )
        )
    )
}