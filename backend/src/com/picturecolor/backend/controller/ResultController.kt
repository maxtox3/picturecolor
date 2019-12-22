package com.picturecolor.backend.controller

import com.picturecolor.backend.deleteMark
import com.picturecolor.backend.getMarks
import com.picturecolor.backend.saveMark
import com.picturecolor.backend.updateMark
import com.picturecolor.backend.util.ID_FIELD
import com.picturecolor.backend.util.RESEARCH_ID_FIELD
import com.picturecolor.backend.util.debugLog
import com.picturecolor.backend.util.user
import com.picturecolor.client.model.MarksResponse
import com.picturecolor.client.model.NewMarkRequest
import com.picturecolor.client.model.SelectedArea
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond

class ResultController {

  suspend fun create(call: ApplicationCall) {
    val request = call.receiveOrNull<NewMarkRequest>()
    debugLog("trying to save mark $request")
    val researchhId = request?.researchId
    if (researchhId != null) {
      call.respond(saveMark(call.user.id, researchhId, request.mark))
    } else {
      call.respond(HttpStatusCode.BadRequest)
    }
  }

  suspend fun read(call: ApplicationCall) {
    val researchId = call.request.queryParameters[RESEARCH_ID_FIELD]?.toIntOrNull()
    if (researchId != null) {
      call.respond(
        MarksResponse(
          getMarks(
            call.user.id,
            researchId
          )
        )
      )
    } else {
      call.respond(HttpStatusCode.BadRequest)
    }
  }

  suspend fun update(call: ApplicationCall) {
    val selectedArea = call.receiveOrNull<SelectedArea>()
    val id = call.parameters[ID_FIELD]?.toIntOrNull()
    if (selectedArea != null && id != null) {
      call.respond(updateMark(id, selectedArea))
    } else {
      call.respond(HttpStatusCode.BadRequest)
    }
  }

  suspend fun delete(call: ApplicationCall) {
    val id = call.parameters[ID_FIELD]?.toIntOrNull()
    debugLog("id = $id")
    if (id != null) {
      call.respond(deleteMark(id))
    } else {
      call.respond(HttpStatusCode.BadRequest)
    }
  }


}