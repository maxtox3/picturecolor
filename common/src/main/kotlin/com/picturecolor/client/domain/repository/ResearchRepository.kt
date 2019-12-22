package com.picturecolor.client.domain.repository

import com.picturecolor.client.model.AreaToSave
import com.picturecolor.client.model.Research
import com.picturecolor.client.model.ResearchSlicesSizesData
import com.picturecolor.client.model.SelectedArea

interface ResearchRepository : Repository {

  suspend fun getResearches(): List<Research>
  suspend fun initResearch(researchId: Int): ResearchSlicesSizesData
  suspend fun getSlice(
    researchId: Int,
    black: Double,
    white: Double,
    gamma: Double,
    type: Int,
    mipMethod: Int,
    slyceNumber: Int,
    aproxSize: Int
  ): String

  suspend fun createMark(researchId: Int, selectedArea: SelectedArea): SelectedArea
  suspend fun createMark(researchId: Int, selectedArea: AreaToSave): SelectedArea
  suspend fun deleteMark(selectedArea: SelectedArea)
  suspend fun deleteMark(areaId: Int)
  suspend fun getMarks(researchId: Int): List<SelectedArea>
  suspend fun updateMark(selectedArea: SelectedArea)
  suspend fun getHounsfieldData(axialCoord: Int, frontalCoord: Int, sagittalCoord: Int): Double
}