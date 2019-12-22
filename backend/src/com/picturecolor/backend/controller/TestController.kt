package com.picturecolor.backend.controller

import com.picturecolor.backend.lib.PictureColorObject
import com.picturecolor.backend.util.debugLog
import com.picturecolor.client.constants.ANGLE_PARAM
import com.picturecolor.client.constants.NAME_PARAM
import com.picturecolor.client.model.ImageModel
import com.picturecolor.client.model.ImageResponse
import com.picturecolor.client.model.TestModel
import com.picturecolor.client.model.TestResponse
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondFile
import java.io.File

class TestController {

  val availablePics = listOf(
    "Apple",
    "Banana"
  )

  suspend fun init(call: ApplicationCall) {
    call.respond(TestResponse(data = TestModel(availablePics)))
  }

  suspend fun updateImage(call: ApplicationCall) {
    val name = call.parameters[NAME_PARAM]
    val angle = call.parameters[ANGLE_PARAM]?.toInt()

    if (name != null && angle != null) {
      try {
        PictureColorObject.updatePic("${name}.png", angle)
        val newFileName = "${name}_update.png"
        val f = File(newFileName)
        if (f.exists() && !f.isDirectory) {
          debugLog("file exists")

          val data = ImageModel(name = name, angle = angle, src = "")
          debugLog(data.toString())
          call.respond(ImageResponse(data))
        }
      } catch (e: Throwable) {
        call.respond(HttpStatusCode.NotFound)
      }
    }
  }

  suspend fun getImage(call: ApplicationCall) {
    val name = call.parameters[NAME_PARAM]
    val angle = call.parameters[ANGLE_PARAM]?.toInt()

    if (name != null && angle != null) {
      try {
        val newFileName = "${name}_update.png"
        val f = File(newFileName)
        if (f.exists() && !f.isDirectory) {
          debugLog("file exists")
          call.respondFile(f)
        }
      } catch (e: Throwable) {
        call.respond(HttpStatusCode.NotFound)
      }
    }
  }

//  suspend fun init(call: ApplicationCall) {
//
//    val id = call.parameters[ID_FIELD]
//    if (id != null && id.isNotEmpty()) {
//      debugLog("id is: $id")
//      val research = getResearch(parseInt(id), call.user.id)
//      try {
//        PictureColorObject.loadNewCtByAccessionNumber(research.name)
//        call.respond(initSlicesData())
//      } catch (e: Exception) {
//        e.printStackTrace()
//        respondError(call)
//      }
//    } else {
//      call.respond(HttpStatusCode.NotFound)
//    }
//  }

//  suspend fun get(call: ApplicationCall) {
//    val request = call.receive<SliceRequest>()
//    val slice = PictureColorObject.getSlice(
//      request.black,
//      request.white,
//      request.gamma,
//      request.type,
//      request.mipMethod,
//      request.sliceNumber,
//      request.mipValue
//    )
//    if (slice != null) {
//      val message = Base64.getEncoder().encode(slice)
//      call.respond(message)
//    } else {
//      respondError(call)
//    }
//  }
//
//  suspend fun list(call: ApplicationCall) {
//    call.respond(
//      ResearchesResponse(
//        getResearches(
//          call.user.id
//        )
//      )
//    )
//  }
//
//  suspend fun getSlice(call: ApplicationCall) {
//    val params = call.request.queryParameters
//    debugLog(params.toString())
//    val black = params["black"]?.toDouble() ?: .0
//    val white = params["white"]?.toDouble() ?: .0
//    val gamma = params["gamma"]?.toDouble() ?: .0
//    val type = params["type"]?.toInt() ?: SLYCE_TYPE_AXIAL
//    val mipMethod = params["mipMethod"]?.toInt() ?: MIP_METHOD_TYPE_NO_MIP
//    val sliceNumber = params["sliceNumber"]?.toInt() ?: 1
//    val mipValue = params["mipValue"]?.toInt() ?: 0
//
//    val slice = PictureColorObject.getSlice(
//      black,
//      white,
//      gamma,
//      type,
//      mipMethod,
//      sliceNumber,
//      mipValue
//    )
//    if (slice != null) {
//      call.respondBytes(
//        contentType = ContentType("image", "bmp"),
//        bytes = //Base64.getEncoder().encode(
//        slice
//      )
//    } else {
//      respondError(call)
//    }
//  }
//
//  suspend fun hounsfield(call: ApplicationCall) {
//    val params = call.request.queryParameters
//    val axialCoord = params[TYPE_AXIAL]?.toInt() ?: 0
//    val frontalCoord = params[TYPE_FRONTAL]?.toInt() ?: 0
//    val sagittalCoord = params[TYPE_SAGITTAL]?.toInt() ?: 0
//
//    debugLog("axial = $axialCoord, frontal = $frontalCoord, sagital = $sagittalCoord")
//
//    val value = PictureColorObject.getPointHU(
//      axialCoord,
//      frontalCoord,
//      sagittalCoord
//    )
//
//    call.respond(HounsfieldResponse(value))
//  }
//
//  private suspend fun respondError(call: ApplicationCall) {
//    call.respond(
//      status = HttpStatusCode.InternalServerError,
//      message = "Произошла ошибка"
//    )
//  }
//
//  private fun initSlicesData(): ResearchInitResponse {
//    return ResearchInitResponse(
//      axialReal = PictureColorObject.getRealValue(SLYCE_TYPE_AXIAL),
//      axialInterpolated = PictureColorObject.getInterpolatedValue(SLYCE_TYPE_AXIAL),
//      frontalReal = PictureColorObject.getRealValue(SLYCE_TYPE_FRONTAL),
//      frontalInterpolated = PictureColorObject.getInterpolatedValue(SLYCE_TYPE_FRONTAL),
//      sagittalReal = PictureColorObject.getRealValue(SLYCE_TYPE_SAGITTAL),
//      sagittalInterpolated = PictureColorObject.getInterpolatedValue(SLYCE_TYPE_SAGITTAL),
//      pixelLength = PictureColorObject.getPixelLengthCoef()
//    )
//  }

}