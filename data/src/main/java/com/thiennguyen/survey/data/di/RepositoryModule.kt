package com.thiennguyen.survey.data.di

import com.thiennguyen.survey.data.local.PreferenceManager
import com.thiennguyen.survey.data.repository.AuthenticationRepositoryImpl
import com.thiennguyen.survey.data.repository.SurveyRepositoryImpl
import com.thiennguyen.survey.data.service.SurveyService
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import com.thiennguyen.survey.domain.repository.SurveyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        surveyService: SurveyService,
        preferenceManager: PreferenceManager
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(surveyService, preferenceManager)
    }

    @Provides
    @Singleton
    fun provideSurveyRepository(
        surveyService: SurveyService,
    ): SurveyRepository {
        return SurveyRepositoryImpl(surveyService)
    }
}