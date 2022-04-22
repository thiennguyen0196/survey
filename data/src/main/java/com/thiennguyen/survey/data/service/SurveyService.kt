package com.thiennguyen.survey.data.service

import com.thiennguyen.survey.data.request.ResetPasswordRequest
import com.thiennguyen.survey.data.request.LoginRequest
import com.thiennguyen.survey.data.request.RefreshTokenRequest
import com.thiennguyen.survey.data.response.UserResponse
import com.thiennguyen.survey.data.response.ObjectResponse
import com.thiennguyen.survey.data.response.LoginResponse
import com.thiennguyen.survey.data.response.ObjectList
import com.thiennguyen.survey.data.response.SurveyResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.Query

interface SurveyService {

    @POST("oauth/token")
    fun submitLogin(@Body loginRequest: LoginRequest): Observable<ObjectResponse<LoginResponse>>

    @POST("oauth/token")
    fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Observable<ObjectResponse<LoginResponse>>

    @POST("passwords")
    fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Observable<ObjectResponse<Unit>>

    @GET("surveys")
    fun getSurveyList(
        @Query("page[number]") pageNumber: Int,
        @Query("page[size]") pageSize: Int,
    ): Observable<ObjectList<SurveyResponse>>

    @GET("me")
    fun getUserProfile(): Observable<ObjectResponse<UserResponse>>
}