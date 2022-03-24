package pl.mo.resume.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import pl.mo.resume.data.state.ResourceState
import pl.mo.resume.utils.StringConstants.NETWORK_ERROR
import retrofit2.Response

@ExperimentalCoroutinesApi
abstract class BoundingRepository<RESULT, REQUEST> {

    /**
     * @return Flow<ResourceState<RESULT>>.
     */
    fun asFlow() = flow<ResourceState<RESULT>> {

        emit(ResourceState.Success(fetchFromLocal().first()))

        // Fetch latest data from remote
        val apiResponse = fetchFromRemote()

        val remoteData = apiResponse.body()

        // Check for response validation
        if (apiResponse.isSuccessful && remoteData != null) {
            // Save data into the persistence storage
            saveRemoteData(remoteData)
        } else {
            // Emit unexpected exception
            emit(ResourceState.Failed(apiResponse.message()))
        }

        // Retrieve data from local storage and emit
        emitAll(
            fetchFromLocal().map {
                ResourceState.Success(it)
            }
        )
    }.catch { e ->
        e.printStackTrace()
        emit(ResourceState.Failed(NETWORK_ERROR))
    }

    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>
}