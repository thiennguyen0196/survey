package com.thiennguyen.survey.di

import com.thiennguyen.survey.data.di.RepositoryModule
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import com.thiennguyen.survey.domain.repository.SurveyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import org.mockito.Mockito

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class MockRepositoryModule {

    @Provides
    @Singleton
    fun provideMockAuthenticationRepository(): AuthenticationRepository = Mockito.mock(AuthenticationRepository::class.java)

    @Provides
    @Singleton
    fun provideMockSurveyRepository(): SurveyRepository = Mockito.mock(SurveyRepository::class.java)
}
