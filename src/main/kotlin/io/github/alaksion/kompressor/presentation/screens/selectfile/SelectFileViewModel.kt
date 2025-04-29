package io.github.alaksion.kompressor.presentation.screens.selectfile

import androidx.lifecycle.ViewModel
import io.github.alaksion.kompressor.presentation.screens.selectfile.components.FilePickerBoxState
import kotlinx.coroutines.flow.MutableStateFlow

internal class SelectFileViewModel : ViewModel() {
    val pickerState = MutableStateFlow<FilePickerBoxState>(
        value = FilePickerBoxState.Unselected
    )

    fun updatePickerState(newState: FilePickerBoxState) {
        pickerState.value = newState
    }
}