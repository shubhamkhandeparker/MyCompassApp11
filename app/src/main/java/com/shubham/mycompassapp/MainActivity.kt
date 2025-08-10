package com.shubham.mycompassapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.shubham.mycompassapp.presentation.ui.components.GradientBackground
import com.shubham.mycompassapp.ui.theme.MyCompassAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.LineHeightStyle
import com.shubham.mycompassapp.presentation.viewmodel.CompassViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.shubham.mycompassapp.presentation.ui.components.CompassRose
import com.shubham.mycompassapp.presentation.ui.components.InfoCardsRow
import com.shubham.mycompassapp.presentation.ui.components.ControlButtonsRow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCompassAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    TestGradient()
                }
            }
        }
    }
}


@Composable
fun TestGradient(viewModel: CompassViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    //Starting Compass when Composable Loads

    LaunchedEffect(Unit) {
        viewModel.startCompass()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GradientBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                //App title
                Text(
                    text = "COMPASS",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 4.sp,
                    modifier = Modifier.padding(top = 60.dp, bottom = 40.dp)
                )

                // Visual Compass Rose
                CompassRose(
                    bearing = uiState.bearing,
                    accuracy = uiState.accuracy
                )
            }
            Spacer(modifier=Modifier.height(40.dp))

            Column {
                //Info Cards
                InfoCardsRow(
                    bearing = uiState.bearing,
                    accuracy = uiState.accuracy,
                    modifier = Modifier.padding(horizontal = 50.dp)
                )

                Spacer(modifier=Modifier.height(40.dp))
                ControlButtonsRow(
                    compassMode = uiState.compassMode,
                    isCalibrating = uiState.isCalibrating,
                    onModeToggle = viewModel::toggleCompassMode,
                    onCalibrate = viewModel::startCalibration,
                    modifier = Modifier.padding(bottom = 40.dp)
                )
            }
        }
    }
}