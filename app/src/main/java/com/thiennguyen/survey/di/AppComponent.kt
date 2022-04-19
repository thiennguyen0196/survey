package com.thiennguyen.survey.di

import com.thiennguyen.survey.data.di.RepositoryModule
import com.thiennguyen.survey.data.di.ServiceModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        AppModule::class,
        RepositoryModule::class,
        ServiceModule::class
    ]
)
interface AppComponent
