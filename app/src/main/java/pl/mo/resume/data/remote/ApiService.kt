package pl.mo.resume.data.remote

import pl.mo.resume.data.model.ParentInformation
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("raw/46ddb6a735d9f85926aa76a02e5d1f60f3edd1f9/")
    suspend fun getInformations(): Response<List<ParentInformation>>

    companion object {
        const val API_URL = "https://gist.githubusercontent.com/Plantaer/be197e9dfff13259f0eadac3e17634aa/"
    }
}