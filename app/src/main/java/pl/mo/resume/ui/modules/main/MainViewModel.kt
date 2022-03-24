package pl.mo.resume.ui.modules.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.mo.resume.data.model.ParentInformation
import pl.mo.resume.data.repository.InformationRepository
import pl.mo.resume.data.state.DataState
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: InformationRepository) :
    ViewModel() {

    private val _data: MutableStateFlow<DataState<List<ParentInformation>>> = MutableStateFlow(DataState.loading())

    val data: StateFlow<DataState<List<ParentInformation>>> = _data

    fun getAllInformations() {
        viewModelScope.launch {
            repository.getAllInformations()
                .map { resource -> DataState.fromResourceState(resource) }
                .collect { state -> _data.value = state }
        }
    }
}