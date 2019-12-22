package com.picturecolor.backend.controller

import com.picturecolor.backend.getResearch
import com.picturecolor.backend.getResearches
import com.picturecolor.backend.lib.PictureColorObject
import com.picturecolor.backend.util.ID_FIELD
import com.picturecolor.backend.util.debugLog
import com.picturecolor.backend.util.parseInt
import com.picturecolor.backend.util.user
import com.picturecolor.client.constants.*
import com.picturecolor.client.model.HounsfieldResponse
import com.picturecolor.client.model.ResearchInitResponse
import com.picturecolor.client.model.ResearchesResponse
import com.picturecolor.client.model.SliceRequest
import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondBytes
import java.util.*

class TestController {

  suspend fun init(call: ApplicationCall) {

    val id = call.parameters[ID_FIELD]
    if (id != null && id.isNotEmpty()) {
      debugLog("id is: $id")
      val research = getResearch(parseInt(id), call.user.id)
      try {
        PictureColorObject.loadNewCtByAccessionNumber(research.name)
        call.respond(initSlicesData())
      } catch (e: Exception) {
        e.printStackTrace()
        respondError(call)
      }
    } else {
      call.respond(HttpStatusCode.NotFound)
    }
  }

  suspend fun get(call: ApplicationCall) {
    val request = call.receive<SliceRequest>()
    val slice = PictureColorObject.getSlice(
      request.black,
      request.white,
      request.gamma,
      request.type,
      request.mipMethod,
      request.sliceNumber,
      request.mipValue
    )
    if (slice != null) {
      val message = Base64.getEncoder().encode(slice)
      call.respond(message)
    } else {
      respondError(call)
    }
  }

  suspend fun list(call: ApplicationCall) {
    call.respond(
      ResearchesResponse(
        getResearches(
          call.user.id
        )
      )
    )
  }

  suspend fun getSlice(call: ApplicationCall) {
    val params = call.request.queryParameters
    debugLog(params.toString())
    val black = params["black"]?.toDouble() ?: .0
    val white = params["white"]?.toDouble() ?: .0
    val gamma = params["gamma"]?.toDouble() ?: .0
    val type = params["type"]?.toInt() ?: SLYCE_TYPE_AXIAL
    val mipMethod = params["mipMethod"]?.toInt() ?: MIP_METHOD_TYPE_NO_MIP
    val sliceNumber = params["sliceNumber"]?.toInt() ?: 1
    val mipValue = params["mipValue"]?.toInt() ?: 0

    val slice = PictureColorObject.getSlice(
      black,
      white,
      gamma,
      type,
      mipMethod,
      sliceNumber,
      mipValue
    )
    if (slice != null) {
      call.respondBytes(
        contentType = ContentType("image", "bmp"),
        bytes = //Base64.getEncoder().encode(
        slice
      )
    } else {
      respondError(call)
    }
  }

  suspend fun hounsfield(call: ApplicationCall) {
    val params = call.request.queryParameters
    val axialCoord = params[TYPE_AXIAL]?.toInt() ?: 0
    val frontalCoord = params[TYPE_FRONTAL]?.toInt() ?: 0
    val sagittalCoord = params[TYPE_SAGITTAL]?.toInt() ?: 0

    debugLog("axial = $axialCoord, frontal = $frontalCoord, sagital = $sagittalCoord")

    val value = PictureColorObject.getPointHU(
      axialCoord,
      frontalCoord,
      sagittalCoord
    )

    call.respond(HounsfieldResponse(value))
  }

  private suspend fun respondError(call: ApplicationCall) {
    call.respond(
      status = HttpStatusCode.InternalServerError,
      message = "Произошла ошибка"
    )
  }

  private fun initSlicesData(): ResearchInitResponse {
    return ResearchInitResponse(
      axialReal = PictureColorObject.getRealValue(SLYCE_TYPE_AXIAL),
      axialInterpolated = PictureColorObject.getInterpolatedValue(SLYCE_TYPE_AXIAL),
      frontalReal = PictureColorObject.getRealValue(SLYCE_TYPE_FRONTAL),
      frontalInterpolated = PictureColorObject.getInterpolatedValue(SLYCE_TYPE_FRONTAL),
      sagittalReal = PictureColorObject.getRealValue(SLYCE_TYPE_SAGITTAL),
      sagittalInterpolated = PictureColorObject.getInterpolatedValue(SLYCE_TYPE_SAGITTAL),
      pixelLength = PictureColorObject.getPixelLengthCoef()
    )
  }

}