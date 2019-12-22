package com.picturecolor.client.model

data class Cut(
  val src: String,
  val type: Int,
  val sliceNumber: Int,
  val slicesSizesData: SliceSizeData,
  val horizontalLine: Double,
  val verticalLine: Double,
  var x: String = "",
  var y: String = "",
  var z: String = ""
) {

  val selectedAreasViewModels: MutableList<SelectedAreaViewModel> = mutableListOf()

  fun clearCursorData() {
    x = ""
    y = ""
    z = ""
  }

  fun hasCursorData(): Boolean {
    return x.isNotEmpty()
  }
}

data class SelectedAreaViewModel(
  val x: Double,
  val y: Double,
  val radius: Double,
  val id: Int? = null,
  var highlight: Boolean = false,
  var hasContext: Boolean = false,
  val movableRect: MovableRect? = null
)

data class MovableRect(
  val id: Int?,
  val left: Double,
  val top: Double,
  val sideLength: Double,
  val leftSideDragRect: DragRect,
  val topSideDragRect: DragRect,
  val rightSideDragRect: DragRect,
  val bottomSideDragRect: DragRect
){
  fun getSideRects(): List<DragRect> = listOf(leftSideDragRect, topSideDragRect, rightSideDragRect, bottomSideDragRect)
}

data class DragRect(
  val left: Double,
  val top: Double,
  val sideLength: Double
)