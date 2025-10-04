package com.example.alris.api

import com.example.alris.models.imageresponse.GenerateChat
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
@POST("models/gemini-2.0-flash:generateContent?key=AIzaSyDAAcVej_wi9zEHcVZUy82PqGcofp6u-Ng")
suspend fun generateChat(
    @Body requestBody: RequestBody
) :GenerateChat

}