package pl.mo.resume.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Detects availability or unavailability of internet connection
 */
object NetworkUtils : ConnectivityManager.NetworkCallback() {

    private val networkLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getNetworkLiveData(context: Context): LiveData<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.registerDefaultNetworkCallback(this)

        networkLiveData.postValue(isConnected(connectivityManager))

        return networkLiveData
    }

    override fun onAvailable(network: Network) {
        networkLiveData.postValue(true)
    }

    override fun onLost(network: Network) {
        networkLiveData.postValue(false)
    }

    private fun isConnected(connectivityManager: ConnectivityManager): Boolean {
        val networkCapability =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        networkCapability?.let {
            return it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
        return false
    }
}