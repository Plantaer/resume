package pl.mo.resume.data.repository

import kotlinx.coroutines.flow.Flow
import pl.mo.resume.data.model.ParentInformation
import pl.mo.resume.data.state.ResourceState

interface IInformationRepository {
    fun getAllInformations(): Flow<ResourceState<List<ParentInformation>>>
    fun getInformationById(informationId: Int): Flow<ParentInformation>
}