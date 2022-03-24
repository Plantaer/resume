package pl.mo.resume.data.state

sealed class DataState<T> {
    class Loading<T> : DataState<T>()

    data class Success<T>(val data: T) : DataState<T>()

    data class Error<T>(val message: String) : DataState<T>()

    fun isLoading(): Boolean = this is Loading

    fun isSuccessful(): Boolean = this is Success

    fun isFailed(): Boolean = this is Error

    companion object {

        /**
         * Returns [DataState.Loading] instance.
         */
        fun <T> loading() = Loading<T>()

        /**
         * Returns [DataState.Success] instance.
         * @param data Data to emit with status.
         */
        fun <T> success(data: T) =
            Success(data)

        /**
         * Returns [DataState.Error] instance.
         * @param message Description of failure.
         */
        fun <T> error(message: String) =
            Error<T>(message)

        /**
         * Returns [DataState] from [ResourceState]
         */
        fun <T> fromResourceState(resource: ResourceState<T>): DataState<T> = when (resource) {
            is ResourceState.Success -> success(resource.data)
            is ResourceState.Failed -> error(resource.message)
        }
    }
}