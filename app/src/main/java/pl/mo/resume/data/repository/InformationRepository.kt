package pl.mo.resume.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import pl.mo.resume.data.local.ParentInformationDao
import pl.mo.resume.data.model.ParentInformation
import pl.mo.resume.data.remote.ApiService
import pl.mo.resume.data.state.ResourceState
import retrofit2.Response
import javax.inject.Inject

@ExperimentalCoroutinesApi
class InformationRepository @Inject constructor(
    private val informationsDao: ParentInformationDao,
    private val resumeService: ApiService
) : IInformationRepository {

    /**
     * Fetched the informations from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    override fun getAllInformations(): Flow<ResourceState<List<ParentInformation>>> {
        return object : BoundingRepository<List<ParentInformation>, List<ParentInformation>>() {

            override suspend fun saveRemoteData(response: List<ParentInformation>) = informationsDao.addInformations(response)

            override fun fetchFromLocal(): Flow<List<ParentInformation>> = informationsDao.getAllInformations()

            override suspend fun fetchFromRemote(): Response<List<ParentInformation>> = resumeService.getInformations()
        }.asFlow()
    }

    /**
     * @return [ParentInformation] data fetched from the database.
     */
    @MainThread
    override fun getInformationById(informationId: Int): Flow<ParentInformation> = informationsDao.getInformationById(informationId).distinctUntilChanged()
}
