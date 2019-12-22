package com.picturecolor.backend.controller


class ResultController {

//  suspend fun create(call: ApplicationCall) {
//    val request = call.receiveOrNull<NewMarkRequest>()
//    debugLog("trying to save mark $request")
//    val researchhId = request?.researchId
//    if (researchhId != null) {
//      call.respond(saveMark(call.user.id, researchhId, request.mark))
//    } else {
//      call.respond(HttpStatusCode.BadRequest)
//    }
//  }
//
//  suspend fun read(call: ApplicationCall) {
//    val researchId = call.request.queryParameters[RESEARCH_ID_FIELD]?.toIntOrNull()
//    if (researchId != null) {
//      call.respond(
//        MarksResponse(
//          getMarks(
//            call.user.id,
//            researchId
//          )
//        )
//      )
//    } else {
//      call.respond(HttpStatusCode.BadRequest)
//    }
//  }
//
//  suspend fun update(call: ApplicationCall) {
//    val selectedArea = call.receiveOrNull<SelectedArea>()
//    val id = call.parameters[ID_FIELD]?.toIntOrNull()
//    if (selectedArea != null && id != null) {
//      call.respond(updateMark(id, selectedArea))
//    } else {
//      call.respond(HttpStatusCode.BadRequest)
//    }
//  }
//
//  suspend fun delete(call: ApplicationCall) {
//    val id = call.parameters[ID_FIELD]?.toIntOrNull()
//    debugLog("id = $id")
//    if (id != null) {
//      call.respond(deleteMark(id))
//    } else {
//      call.respond(HttpStatusCode.BadRequest)
//    }
//  }


}