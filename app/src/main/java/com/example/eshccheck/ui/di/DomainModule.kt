package com.example.eshccheck.ui.di

import com.example.eshccheck.domain.FetchUseCase
import com.example.eshccheck.domain.Repository
import com.example.eshccheck.ui.model.MapDomainToUi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideFetchUseCase(repository: Repository): FetchUseCase =
        FetchUseCase.Base(repository = repository)

    @Provides
    fun provideMapDomainToUi(): MapDomainToUi = MapDomainToUi.Base()
}