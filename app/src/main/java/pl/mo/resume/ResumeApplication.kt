package pl.mo.resume

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.mo.resume.utils.TimeConstants.FIRST_HOUR_OF_THE_DAY
import pl.mo.resume.utils.TimeConstants.LAST_HOUR_OF_THE_DAY
import java.util.*

@ExperimentalCoroutinesApi
@HiltAndroidApp
class ResumeApplication : Application() {

    // Day is set to be from 6:00 AM to 6:00 PM
    private val isDay = Calendar.getInstance()
        .get(Calendar.HOUR_OF_DAY) in FIRST_HOUR_OF_THE_DAY .. LAST_HOUR_OF_THE_DAY

    override fun onCreate() {
        super.onCreate()

        // Sets value for night mode.
        // Value depends on hour of the day.
        AppCompatDelegate.setDefaultNightMode(if (isDay) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES)
    }
}