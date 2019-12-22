package com.picturecolor.client.domain.repository

import com.picturecolor.client.data.datasource.local.LocalDataSource
import com.picturecolor.client.data.datasource.remote.ResearchRemote
import com.picturecolor.client.model.*

class ResearchRepositoryImpl(
  private val local: LocalDataSource,
  private val remote: ResearchRemote
) : ResearchRepository {

  override suspend fun getHounsfieldData(
    axialCoord: Int,
    frontalCoord: Int,
    sagittalCoord: Int
  ): Double {
    return remote.getHounsfieldData(
      local.getToken(),
      HounsfieldRequest(
        axialCoord,
        frontalCoord,
        sagittalCoord
      )
    )
  }

  override suspend fun getResearches(): List<Research> {
    return remote.getResearches(local.getToken())
  }

  override suspend fun initResearch(researchId: Int): ResearchSlicesSizesData {
    return remote.initResearch(
      token = local.getToken(),
      researchId = researchId
    ).let {
      ResearchSlicesSizesData(
        axial = SliceSizeData(
          maxFramesSize = it.axialReal,
          height = it.frontalInterpolated,
          pixelLength = it.pixelLength
        ),
        frontal = SliceSizeData(
          maxFramesSize = it.frontalReal,
          height = it.axialInterpolated,
          pixelLength = it.pixelLength
        ),
        sagittal = SliceSizeData(
          maxFramesSize = it.sagittalReal,
          height = it.axialInterpolated,
          pixelLength = it.pixelLength
        ),
        pixelLength = it.pixelLength
      )
    }
  }

  override suspend fun getSlice(
    researchId: Int,
    black: Double,
    white: Double,
    gamma: Double,
    type: Int,
    mipMethod: Int,
    slyceNumber: Int,
    aproxSize: Int
  ): String {
    return remote.getSlice(
      token = local.getToken(),
      request = SliceRequest(
        black = black,
        white = white,
        gamma = gamma,
        type = type,
        mipMethod = mipMethod,
        sliceNumber = slyceNumber,
        mipValue = aproxSize
      ),
      researchId = researchId
    )
  }
//  ): String {
//    return "$END_POINT/$RESEARCH_ROUTE/${researchId}/img?" +
//      "black=${black}" + "&" +
//      "white=${white}" + "&" +
//      "gamma=${gamma}" + "&" +
//      "sliceNumber=${slyceNumber}" + "&" +
//      "mipMethod=${mipMethod}" + "&" +
//      "mipValue=${aproxSize}" + "&" +
//      "type=${type}"
//  }

  override suspend fun createMark(researchId: Int, selectedArea: SelectedArea): SelectedArea {
    return remote.createMark(
      token = local.getToken(),
      request = MarkRequest(
        selectedArea,
        researchId
      )
    )
  }

  override suspend fun createMark(researchId: Int, selectedArea: AreaToSave): SelectedArea {
    return remote.createMark(
      token = local.getToken(),
      request = NewMarkRequest(
        selectedArea,
        researchId
      )
    )
  }

  override suspend fun deleteMark(selectedArea: SelectedArea) {
    remote.deleteMark(selectedArea, local.getToken())
  }

  override suspend fun deleteMark(areaId: Int) {
    remote.deleteMark(areaId, local.getToken())
  }

  override suspend fun getMarks(researchId: Int): List<SelectedArea> {
    return remote.getMarks(researchId, local.getToken())
  }

  override suspend fun updateMark(selectedArea: SelectedArea) {
    return remote.updateMark(selectedArea, local.getToken())
  }
}