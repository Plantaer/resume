package pl.mo.resume.data.state

sealed class ResourceState<T> {
    class Success<T>(val data: T) : ResourceState<T>()
    class Failed<T>(val message: String) : ResourceState<T>()
}