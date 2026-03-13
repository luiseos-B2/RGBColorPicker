package com.example.rgbcolorpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.example.rgbcolorpicker.presentation.viewmodel.ColorPickerViewModel
import com.example.rgbcolorpicker.ui.screens.RGBColorPickerScreen
import com.example.rgbcolorpicker.ui.theme.RGBColorPickerTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ColorPickerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RGBColorPickerTheme {
                val uiState by viewModel.uiState.collectAsState()
                RGBColorPickerScreen(
                    uiState = uiState,
                    onRedChange = viewModel::updateRed,
                    onGreenChange = viewModel::updateGreen,
                    onBlueChange = viewModel::updateBlue
                )
            }
        }
    }
}