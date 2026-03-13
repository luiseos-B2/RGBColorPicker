package com.example.rgbcolorpicker.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rgbcolorpicker.presentation.state.ColorPickerUiState
import com.example.rgbcolorpicker.ui.theme.RGBColorPickerTheme

@Composable
fun RGBColorPickerScreen(
    uiState: ColorPickerUiState,
    onRedChange: (Int) -> Unit,
    onGreenChange: (Int) -> Unit,
    onBlueChange: (Int) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val selectedColor = Color(uiState.red, uiState.green, uiState.blue)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Color Picker",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(selectedColor, RoundedCornerShape(12.dp))
        )

        Text(
            text = "HEX: ${uiState.hexCode}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                clipboardManager.setText(AnnotatedString(uiState.hexCode))
                Toast.makeText(context, "Hex code copied!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Copy")
        }

        RGBSlider(
            label = "Red",
            value = uiState.red,
            onChanged = onRedChange
        )

        RGBSlider(
            label = "Green",
            value = uiState.green,
            onChanged = onGreenChange
        )

        RGBSlider(
            label = "Blue",
            value = uiState.blue,
            onChanged = onBlueChange
        )
    }
}

@Composable
private fun RGBSlider(
    label: String,
    value: Int,
    onChanged: (Int) -> Unit
) {
    Column {
        Text(text = "$label: $value")
        Slider(
            value = value.toFloat(),
            onValueChange = { onChanged(it.toInt()) },
            valueRange = 0f..255f
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorPickerScreenPreview() {
    RGBColorPickerTheme {
        RGBColorPickerScreen(
            uiState = ColorPickerUiState(red = 120, green = 80, blue = 210),
            onRedChange = {},
            onGreenChange = {},
            onBlueChange = {}
        )
    }
}
