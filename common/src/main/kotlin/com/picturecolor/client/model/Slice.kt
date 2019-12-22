package com.picturecolor.client.model

import com.picturecolor.client.constants.*
import kotlinx.serialization.Serializable

@Serializable
data class SliceRequest(
  val black: Double,
  val white: Double,
  val gamma: Double,
  val type: Int,
  val mipMethod: Int,
  val sliceNumber: Int,
  val mipValue: Int
)

data class Circle(
  val centerX: Double,
  val centerY: Double,
  val radius: Double
)

data class CircleShape(
  val x: Double,
  val y: Double,
  val radius: Double,
  val areaId: Int,
  val highlight: Boolean = false
)

data class Lines(
  val horizontal: Line,
  val vertical: Line,
  val cubeColor: String
)

data class Line(
  val color: String,
  val value: Int
)

data class MoveRect(
  val areaId: Int,
  val left: Double,
  val top: Double,
  val sideLength: Double,
  val color: String,
  val type: MoveRectType
)

data class ContainerSizeModel(
  val width: Int,
  val height: Int
)

data class PositionModel(
  val width: Double,
  val height: Double,
  val translateX: Double,
  val translateY: Double
)

fun initialLines(cutType: Int): Lines = Lines(
  horizontal = initialLine(
    cutType,
    LineType.HORIZONTAL
  ),
  vertical = initialLine(
    cutType,
    LineType.VERTICAL
  ),
  cubeColor = getColorByCutType(cutType)
)

fun getColorByCutType(cutType: Int): String {
  return when (cutType) {
    SLYCE_TYPE_AXIAL -> yellow
    SLYCE_TYPE_FRONTAL -> pink
    SLYCE_TYPE_SAGITTAL -> blue
    else -> throw NotImplementedError("getColor не реализовал")
  }
}

fun initialLine(cutType: Int, lineType: LineType): Line = Line(
  color = getLineColor(cutType, lineType),
  value = 1
)

fun getLineColor(cutType: Int, lineType: LineType): String = when (cutType) {
  SLYCE_TYPE_AXIAL -> {
    when (lineType) {
      LineType.HORIZONTAL -> pink
      LineType.VERTICAL -> blue
    }
  }
  SLYCE_TYPE_FRONTAL -> {
    when (lineType) {
      LineType.HORIZONTAL -> yellow
      LineType.VERTICAL -> blue
    }
  }
  SLYCE_TYPE_SAGITTAL -> {
    when (lineType) {
      LineType.HORIZONTAL -> yellow
      LineType.VERTICAL -> pink
    }
  }
  else -> throw NotImplementedError("getColor не реализовал")
}

data class PositionData(
  val x: Int,
  val y: Int,
  val z: Int
)

data class MouseData(
  val x: Double,
  val y: Double
)

data class MouseMoveData(
  val deltaX: Double,
  val deltaY: Double
)

data class MouseClickData(
  val x: Double,
  val y: Double,
  val altKey: Boolean
)

data class SliceData(
  val black: Double,
  val white: Double,
  val gamma: Double,
  val mipMethod: Int,
  val mipValue: Int,
  val sliceNumber: Int,
  val cutType: Int,
  val researchId: Int,
  val sliceSizeData: SliceSizeData
)

fun initialSliceData(cutType: Int): SliceData =
  SliceData(
    black = INITIAL_BLACK,
    white = INITIAL_WHITE,
    gamma = INITIAL_GAMMA,
    mipMethod = MIP_METHOD_TYPE_NO_MIP,
    mipValue = INITIAL_MIP_VALUE,
    sliceNumber = 1,
    cutType = cutType,
    researchId = 0,
    sliceSizeData = initialSlicesSizeData()
  )

data class GridModel(
  val type: CutsGridType,
  val cells: List<CellModel>
)

data class CellModel(
  val cutType: Int,
  val position: Position,
  val sliceSizeData: SliceSizeData
)

fun initialGridModel(): GridModel {
  return GridModel(
    type = CutsGridType.SINGLE,
    cells = getInitialCells()
  )
}

fun getInitialCells(): List<CellModel> {
  return listOf(
    CellModel(
      SLYCE_TYPE_FRONTAL,
      Position.LEFT_TOP,
      initialSlicesSizeData()
    )
  )
}
