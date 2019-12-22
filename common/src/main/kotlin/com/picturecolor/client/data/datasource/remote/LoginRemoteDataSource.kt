package com.picturecolor.client.data.datasource.remote

import com.picturecolor.client.constants.*
import com.picturecolor.client.model.AuthorizationRequest
import com.picturecolor.client.model.AuthorizationResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.http.takeFrom
import kotlinx.serialization.json.Json

class LoginRemoteDataSource : LoginRemote {

  private val client: HttpClient = HttpClient {
    install(JsonFeature) {
      serializer = KotlinxSerializer()
    }
  }

  override suspend fun auth(firstName: String, secondName: String): String {
    return client.post<AuthorizationResponse> {
      apiUrl(LOGIN_ROUTE)
      body = Json.stringify(
        AuthorizationRequest.serializer(),
        AuthorizationRequest(name = firstName, secondName = secondName)
      )
    }.token
  }

  override suspend fun tryToAuth(): String {
    return client.post<AuthorizationResponse> {
      apiUrl(AUTH_CHECK_ROUTE)
    }.token
  }

  private fun HttpRequestBuilder.apiUrl(path: String) {
    url {
      takeFrom(END_POINT)
      encodedPath = path
    }
  }

}