package com.picturecolor.client.newmvi.researchmvi.store

import com.picturecolor.client.constants.*
import com.picturecolor.client.debugLog
import com.picturecolor.client.model.CellModel
import com.picturecolor.client.model.GridModel
import com.picturecolor.client.model.ResearchSlicesSizesData
import com.picturecolor.client.model.initialSlicesSizeData

interface GridProcessor {
  fun changeType(
    newType: CutsGridType,
    oldModel: GridModel,
    sliceSizesData: ResearchSlicesSizesData?
  ): GridModel

  fun init(researchData: ResearchSlicesSizesData): GridModel
}

internal class GridProcessorImpl : GridProcessor {

  override fun init(researchData: ResearchSlicesSizesData): GridModel {
    debugLog("researchData in store = $researchData")
    return makeSingleContainer(researchSlicesSizesData = researchData)
  }

  override fun changeType(
    newType: CutsGridType,
    oldModel: GridModel,
    sliceSizesData: ResearchSlicesSizesData?
  ): GridModel {
    return when (newType) {
      CutsGridType.SINGLE -> makeSingleContainer(sliceSizesData)
      CutsGridType.TWO_VERTICAL -> makeTwoVertical(sliceSizesData)
      CutsGridType.TWO_HORIZONTAL -> makeTwoHorizontal(sliceSizesData)
      CutsGridType.THREE -> makeThree(sliceSizesData)
      CutsGridType.FOUR -> TODO()
    }
  }

  private fun makeSingleContainer(researchSlicesSizesData: ResearchSlicesSizesData?): GridModel {
    return GridModel(
      type = CutsGridType.SINGLE,
      cells = listOf(
        CellModel(
          SLYCE_TYPE_FRONTAL,
          Position.LEFT_TOP,
          researchSlicesSizesData?.frontal
            ?: initialSlicesSizeData()
        )
      )
    )
  }

  private fun makeTwoVertical(researchSlicesSizesData: ResearchSlicesSizesData?): GridModel {
    return GridModel(
      type = CutsGridType.TWO_VERTICAL,
      cells = listOf(
        CellModel(
          SLYCE_TYPE_AXIAL,
          Position.LEFT_TOP,
          researchSlicesSizesData?.axial
            ?: initialSlicesSizeData()
        ),
        CellModel(
          SLYCE_TYPE_FRONTAL,
          Position.LEFT_BOTTOM,
          researchSlicesSizesData?.frontal
            ?: initialSlicesSizeData()
        )
      )
    )
  }

  private fun makeTwoHorizontal(researchSlicesSizesData: ResearchSlicesSizesData?): GridModel {
    return GridModel(
      type = CutsGridType.TWO_HORIZONTAL,
      cells = listOf(
        CellModel(
          SLYCE_TYPE_FRONTAL,
          Position.LEFT_TOP,
          researchSlicesSizesData?.frontal
            ?: initialSlicesSizeData()
        ),
        CellModel(
          SLYCE_TYPE_AXIAL,
          Position.RIGHT_TOP,
          researchSlicesSizesData?.axial
            ?: initialSlicesSizeData()
        )
      )
    )
  }

  private fun makeThree(researchSlicesSizesData: ResearchSlicesSizesData?): GridModel {
    return GridModel(
      type = CutsGridType.THREE,
      cells = listOf(
        CellModel(
          SLYCE_TYPE_AXIAL,
          Position.LEFT_TOP,
          researchSlicesSizesData?.axial
            ?: initialSlicesSizeData()
        ),
        CellModel(
          SLYCE_TYPE_FRONTAL,
          Position.LEFT_BOTTOM,
          researchSlicesSizesData?.frontal
            ?: initialSlicesSizeData()
        ),
        CellModel(
          SLYCE_TYPE_SAGITTAL,
          Position.RIGHT_BOTTOM,
          researchSlicesSizesData?.sagittal
            ?: initialSlicesSizeData()
        )
      )
    )
  }


}