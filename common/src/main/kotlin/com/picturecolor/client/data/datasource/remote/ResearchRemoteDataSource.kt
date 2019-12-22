package com.picturecolor.client.data.datasource.remote

import com.picturecolor.client.constants.*
import com.picturecolor.client.model.*
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import io.ktor.http.takeFrom
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

class ResearchRemoteDataSource : ResearchRemote {

  private val client: HttpClient = HttpClient {
    install(JsonFeature) {
      serializer = KotlinxSerializer().apply {
        setMapper(SelectedArea::class, SelectedArea.serializer())
        setMapper(NewMarkRequest::class, NewMarkRequest.serializer())
      }
    }
  }

  override suspend fun getResearches(token: String): List<Research> {
    return client.get<ResearchesResponse> {
      authHeader(token)
      apiUrl("$RESEARCH_ROUTE/$LIST_ROUTE")
    }.researches
  }

  override suspend fun initResearch(token: String, researchId: Int): ResearchInitResponse {
    return client.get {
      authHeader(token)
      apiUrl("$RESEARCH_ROUTE/$INIT_ROUTE/$researchId")
    }
  }

  override suspend fun getSlice(
    token: String,
    request: SliceRequest,
    researchId: Int
  ): String {
    return client.post {
      authHeader(token)
      apiUrl("$RESEARCH_ROUTE/$researchId")
      body = "{ black:${request.black},white:${request.white},gamma:${request.gamma},mipMethod:${request.mipMethod},type:${request.type},sliceNumber:${request.sliceNumber}, mipValue:${request.mipValue} }"
    }
  }

  override suspend fun createMark(token: String, request: MarkRequest): SelectedArea {
    return client.post {
      authHeader(token)
      apiUrl(MARK_ROUTE)
      body = "{researchId:${request.researchId}, mark:${getBody(request.mark)}}"
    }
  }

  @UnstableDefault
  override suspend fun createMark(token: String, request: NewMarkRequest): SelectedArea {
    return client.post {
      authHeader(token)
      apiUrl(MARK_ROUTE)
      body = Json.stringify(NewMarkRequest.serializer(), request)
    }
  }

  override suspend fun deleteMark(selectedArea: SelectedArea, token: String) {
    return client.delete {
      authHeader(token)
      apiUrl("$MARK_ROUTE/${selectedArea.id}")
    }
  }

  override suspend fun deleteMark(areaId: Int, token: String) {
    return client.delete {
      authHeader(token)
      apiUrl("$MARK_ROUTE/${areaId}")
    }
  }

  override suspend fun getMarks(researchId: Int, token: String): List<SelectedArea> {
    return client.get<MarksResponse> {
      authHeader(token)
      apiUrl("$MARK_ROUTE?research_id=$researchId")
    }.marks
  }

  override suspend fun updateMark(selectedArea: SelectedArea, token: String) {
    return client.put {
      authHeader(token)
      apiUrl("$MARK_ROUTE/${selectedArea.id}")
      body = getBody(selectedArea)
    }
  }

  override suspend fun getHounsfieldData(
    token: String,
    request: HounsfieldRequest
  ): Double {
    return client.get<HounsfieldResponse> {
      authHeader(token)
      apiUrl("$RESEARCH_ROUTE/$HOUNSFIELD_ROUTE?$TYPE_AXIAL=${request.axialCoord}&$TYPE_FRONTAL=${request.frontalCoord}&$TYPE_SAGITTAL=${request.sagittalCoord}")
    }.huValue
  }

  private fun getBody(selectedArea: SelectedArea) =
    "{radius:${selectedArea.radius}, x:${selectedArea.x}, y:${selectedArea.y}, z:${selectedArea.z}, type:${selectedArea.type}, size:${selectedArea.size} }"

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