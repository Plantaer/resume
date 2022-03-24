package pl.mo.resume.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.mo.resume.data.local.InformationsDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = InformationsDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideInformationsDao(database: InformationsDatabase) = database.getInformationsDao()
}