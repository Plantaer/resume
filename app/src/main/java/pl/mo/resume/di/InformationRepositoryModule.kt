package pl.mo.resume.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.mo.resume.data.repository.IInformationRepository
import pl.mo.resume.data.repository.InformationRepository

@ExperimentalCoroutinesApi
@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class InformationRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindInformationRepository(repository: InformationRepository): IInformationRepository
}