package com.thiennguyen.survey.data.service

import com.thiennguyen.survey.data.request.LoginRequest
import com.thiennguyen.survey.data.response.LoginResponse
import com.thiennguyen.survey.data.response.ObjectResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface SurveyService {

    @POST("oauth/token")
    fun submitLogin(@Body loginRequest: LoginRequest): Observable<ObjectResponse<LoginResponse?>>
}