package com.example.rgbcolorpicker.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.rgbcolorpicker.presentation.state.ColorPickerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ColorPickerViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ColorPickerUiState(
            red = savedStateHandle[RED_KEY] ?: 0,
            green = savedStateHandle[GREEN_KEY] ?: 0,
            blue = savedStateHandle[BLUE_KEY] ?: 0
        )
    )
    val uiState: StateFlow<ColorPickerUiState> = _uiState.asStateFlow()

    fun updateRed(value: Int) {
        val sanitized = value.coerceIn(0, 255)
        _uiState.update { state -> state.copy(red = sanitized) }
        savedStateHandle[RED_KEY] = sanitized
    }

    fun updateGreen(value: Int) {
        val sanitized = value.coerceIn(0, 255)
        _uiState.update { state -> state.copy(green = sanitized) }
        savedStateHandle[GREEN_KEY] = sanitized
    }

    fun updateBlue(value: Int) {
        val sanitized = value.coerceIn(0, 255)
        _uiState.update { state -> state.copy(blue = sanitized) }
        savedStateHandle[BLUE_KEY] = sanitized
    }

    private companion object {
        const val RED_KEY = "red"
        const val GREEN_KEY = "green"
        const val BLUE_KEY = "blue"
    }
}
