package com.example.rgbcolorpicker.presentation.state

data class ColorPickerUiState(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0
) {
    val hexCode: String
        get() = String.format("#%02X%02X%02X", red, green, blue)
}
