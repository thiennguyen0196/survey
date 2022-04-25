package com.thiennguyen.survey.di

import android.content.Context
import com.thiennguyen.survey.data.interceptor.LogoutService
import com.thiennguyen.survey.service.LogoutServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideLogoutService(context: Context): LogoutService {
        return LogoutServiceImpl(context)
    }
}
