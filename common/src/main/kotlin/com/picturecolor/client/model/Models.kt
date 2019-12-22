package com.picturecolor.client.model

import com.picturecolor.client.constants.AreaType
import com.picturecolor.client.constants.CutsGridType
import com.picturecolor.client.constants.Position
import kotlinx.serialization.Serializable

@Serializable
data class ResearchInitResponse(
  val axialReal: Int,
  val axialInterpolated: Int,
  val frontalReal: Int,
  val frontalInterpolated: Int,
  val sagittalReal: Int,
  val sagittalInterpolated: Int,
  val pixelLength: Double
)

@Serializable
data class ResearchSlicesSizesData(
  val axial: SliceSizeData,
  val frontal: SliceSizeData,
  val sagittal: SliceSizeData,
  val pixelLength: Double,
  val researchId: Int = -1
)

fun initialResearchSlicesSizesData(): ResearchSlicesSizesData {
  return ResearchSlicesSizesData(
    axial = initialSlicesSizeData(),
    frontal = initialSlicesSizeData(),
    sagittal = initialSlicesSizeData(),
    pixelLength = .0
  )
}

fun initialSlicesSizeData(): SliceSizeData {
  return SliceSizeData(
    maxFramesSize = 0,
    height = 0,
    pixelLength = 0.0
  )
}

@Serializable
data class SliceSizeData(
  val maxFramesSize: Int,
  val height: Int,
  val pixelLength: Double
)

@Serializable
data class Research(
  val id: Int,
  val name: String,
  val seen: Boolean,
  val done: Boolean
)

@Serializable
data class ResearchesResponse(
  val researches: List<Research>
)

@Serializable
data class BaseResponse(
  val status: String,
  val error: String
)

@Serializable
data class MarksResponse(
  val marks: List<SelectedArea>
)

@Serializable
data class MarkRequest(
  val mark: SelectedArea,
  val researchId: Int
)

@Serializable
data class NewMarkRequest(
  val mark: AreaToSave,
  val researchId: Int
)

@Serializable
data class SelectedArea(
  val x: Double,
  val y: Double,
  val z: Double,
  val type: AreaType,
  val radius: Double,
  val size: Double,
  val id: Int
)

@Serializable
data class AreaToSave(
  val x: Double,
  val y: Double,
  val z: Double,
  val radius: Double,
  val size: Double
)

@Serializable
data class HounsfieldResponse(
  val huValue: Double
)

@Serializable
data class HounsfieldRequest(
  val axialCoord: Int,
  val frontalCoord: Int,
  val sagittalCoord: Int
)