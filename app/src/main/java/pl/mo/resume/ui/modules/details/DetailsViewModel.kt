package pl.mo.resume.ui.modules.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.mo.resume.data.repository.IInformationRepository

@ExperimentalCoroutinesApi
class DetailsViewModel  @AssistedInject constructor(
    informationRepository: IInformationRepository,
    @Assisted parentId: Int
) : ViewModel() {

    val parentInformation = informationRepository.getInformationById(parentId).asLiveData()

    @AssistedFactory
    interface DetailsViewModelFactory {
        fun create(parentId: Int): DetailsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: DetailsViewModelFactory,
            parentId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(parentId) as T
            }
        }
    }
}