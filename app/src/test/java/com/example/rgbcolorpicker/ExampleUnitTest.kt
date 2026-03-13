package com.example.rgbcolorpicker

import androidx.lifecycle.SavedStateHandle
import com.example.rgbcolorpicker.presentation.viewmodel.ColorPickerViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun `update methods should expose expected state`() {
        val viewModel = ColorPickerViewModel(SavedStateHandle())

        assertEquals(0, viewModel.uiState.value.red)
        assertEquals(0, viewModel.uiState.value.green)
        assertEquals(0, viewModel.uiState.value.blue)

        viewModel.updateRed(30)
        viewModel.updateGreen(120)
        viewModel.updateBlue(240)

        assertEquals(30, viewModel.uiState.value.red)
        assertEquals(120, viewModel.uiState.value.green)
        assertEquals(240, viewModel.uiState.value.blue)
        assertEquals("#1E78F0", viewModel.uiState.value.hexCode)
    }

    @Test
    fun `values from saved state should initialize ui state`() {
        val savedStateHandle = SavedStateHandle(
            mapOf(
                "red" to 10,
                "green" to 20,
                "blue" to 30
            )
        )
        val viewModel = ColorPickerViewModel(savedStateHandle)

        val uiState = viewModel.uiState.value
        assertEquals(10, uiState.red)
        assertEquals(20, uiState.green)
        assertEquals(30, uiState.blue)
        assertEquals("#0A141E", uiState.hexCode)
    }

    @Test
    fun `input values should be clamped to rgb range`() {
        val viewModel = ColorPickerViewModel(SavedStateHandle())
        viewModel.updateRed(-1)
        viewModel.updateGreen(500)
        viewModel.updateBlue(100)

        val uiState = viewModel.uiState.value
        assertEquals(0, uiState.red)
        assertEquals(255, uiState.green)
        assertEquals(100, uiState.blue)
    }
}