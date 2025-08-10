package com.shubham.mycompassapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shubham.mycompassapp.domain.model.usecase.SensorAccuracy

@Composable
fun InfoCardsRow(
    bearing: Float,
    accuracy: SensorAccuracy,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    )
    {
        //Bearing card
        InfoCard(
            label = "BEARING",
            value = "${bearing.toInt()}°",
            modifier = Modifier.weight(1f)
        )

        //Accuracy Card
        InfoCard(
            label = "ACCURACY",
            value = accuracy.displayName,
            modifier = Modifier.weight(1f)
        )

    }
}


@Composable
fun InfoCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x261a202c)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Label text
            Text(
                text = label,
                color = Color(0xFFa0aec0), //Light Gray
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            //Value Text
            Text(
                text = value,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}