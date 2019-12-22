package com.picturecolor.client.data.datasource.remote

import com.picturecolor.client.constants.END_POINT
import com.picturecolor.client.constants.IMAGE_ROUTE
import com.picturecolor.client.constants.TEST_ROUTE
import com.picturecolor.client.model.ImageModel
import com.picturecolor.client.model.ImageResponse
import com.picturecolor.client.model.TestModel
import com.picturecolor.client.model.TestResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.takeFrom

class ResearchRemoteDataSource : TestRemote {

  private val client: HttpClient = HttpClient {
    install(JsonFeature) {
      serializer = KotlinxSerializer().apply {
      }
    }
  }

  override suspend fun getTest(token: String): TestModel {
    return client.get<TestResponse> {
      authHeader(token)
      apiUrl(TEST_ROUTE)
    }.data
  }

  override suspend fun getTestImage(token: String, imageName: String, angle: Int): ImageModel {
    return client.post<ImageResponse> {
      authHeader(token)
      apiUrl("$IMAGE_ROUTE?name=$imageName&angle=$angle")
    }.data
  }

  private fun HttpRequestBuilder.authHeader(token: String) {
    header("Authorization", "Bearer $token")
  }

  private fun HttpRequestBuilder.apiUrl(path: String) {
    url {
      takeFrom(END_POINT)
      encodedPath = path
    }
  }


}