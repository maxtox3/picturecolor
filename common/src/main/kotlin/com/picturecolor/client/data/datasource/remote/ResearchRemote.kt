package com.picturecolor.client.data.datasource.remote

import com.picturecolor.client.model.*

interface ResearchRemote {
  suspend fun getResearches(token: String): List<Research>
  suspend fun initResearch(token: String, researchId: Int): ResearchInitResponse
  suspend fun getSlice(token: String, request: SliceRequest, researchId: Int): String
  suspend fun createMark(token: String, request: MarkRequest): SelectedArea
  suspend fun createMark(token: String, request: NewMarkRequest): SelectedArea
  suspend fun deleteMark(selectedArea: SelectedArea, token: String)
  suspend fun deleteMark(areaId: Int, token: String)
  suspend fun getMarks(researchId: Int, token: String): List<SelectedArea>
  suspend fun updateMark(selectedArea: SelectedArea, token: String)
  suspend fun getHounsfieldData(
    token: String,
    request: HounsfieldRequest
  ): Double
}