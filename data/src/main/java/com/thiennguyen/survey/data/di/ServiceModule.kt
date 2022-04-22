package com.thiennguyen.survey.data.di

import android.content.Context
import com.thiennguyen.survey.data.BuildConfig
import com.thiennguyen.survey.data.R
import com.thiennguyen.survey.data.interceptor.AuthenticationInterceptor
import com.thiennguyen.survey.data.interceptor.LogoutService
import com.thiennguyen.survey.data.interceptor.TokenAuthenticator
import com.thiennguyen.survey.data.local.PreferenceManager
import com.thiennguyen.survey.data.service.SurveyService
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        )
    }

    @Provides
    @Singleton
    fun provideAuthenticationInterceptor(
        preferenceManager: PreferenceManager
    ): AuthenticationInterceptor {
        return AuthenticationInterceptor(preferenceManager)
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        authenticationRepository: Lazy<AuthenticationRepository>,
        preferenceManager: PreferenceManager,
        logoutService: LogoutService
    ): TokenAuthenticator {
        return TokenAuthenticator(
            repository = authenticationRepository,
            preferenceManager = preferenceManager,
            logoutService = logoutService
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authenticationInterceptor: AuthenticationInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(authenticationInterceptor)
            .authenticator(tokenAuthenticator)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        context: Context,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.nimble_endpoint))
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSurveyService(
        retrofit: Retrofit
    ): SurveyService {
        return retrofit.create(SurveyService::class.java)
    }
}