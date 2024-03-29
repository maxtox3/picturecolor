package com.picturecolor.backend.lib

import com.sun.jna.Library
import com.sun.jna.ptr.DoubleByReference
import com.sun.jna.ptr.IntByReference
import com.sun.jna.ptr.PointerByReference

interface PictureColorLibrary : Library {

  fun updatePic(fileName: String, angle: Int)

//  fun Initpicturecolor_J(data_store_path: String): Int
//
//  fun GetStudiesIDs_J(ptrRef: PointerByReference, length: IntByReference): Int
//
//  fun GetSlice_J(
//    result: PointerByReference,
//    length: IntByReference,
//    sliceType: Int,
//    rescaledSliceNo: Int,
//    black: Double,
//    white: Double,
//    gamma: Double,
//    aproxSize: Int,
//    mipMethod: Int
//  ): Int
//
//  fun LoadCTbyAccession_J(accessionNumber: String): Int
//
//  fun GetNFrames_real_J(result: IntByReference, sliceType: Int)
//  fun GetNFrames_interpolated_J(result: IntByReference, sliceType: Int)
//
//  fun GetCoordinateNative_J(
//    resultCoord: DoubleByReference,
//    sliceType: Int,
//    nativeSlicePosition: Int
//  )
//
//  fun GetCoordinateInterpolated_J(
//    resultCoord: DoubleByReference,
//    sliceType: Int,
//    rescaledSliceNo: Int
//  )
//
//  fun GetOriginalPixelCoordinate_J(
//    resultPixelCoord: IntByReference,
//    sliceType: Int,
//    rescaledSliceNo: Int,
//    interpolateZ: Boolean
//  )
//
//  fun GetPointHU_J(
//    resultValue: DoubleByReference,
//    axialCoord: Int,
//    frontalCoord: Int,
//    sagittalCoord: Int
//  )
//
//  fun GetInterpolatedPixel_J(
//    resultRescaledPixelCoord: IntByReference,
//    sliceType: Int,
//    originalSLiceNumber: Int
//  )
//
//  fun GetPixelLengthCoefficient_J(resultLengthPixelCoef: DoubleByReference)
}