package com.picturecolor.backend.controller

import com.picturecolor.backend.JwtConfig
import com.picturecolor.backend.createUser
import com.picturecolor.client.model.AuthorizationRequest
import com.picturecolor.client.model.AuthorizationResponse
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond

class AuthController {

  suspend fun login(call: ApplicationCall) {
    val credentials = call.receive<AuthorizationRequest>()
    try {
      val user = createUser(firstName = credentials.name, secondName = credentials.secondName)
      val token = JwtConfig.makeToken(user = user)
      call.respond(AuthorizationResponse(token = token))
    } catch (e: Throwable) {
      println(e.toString())
      call.respond(HttpStatusCode.Forbidden)
    }
  }
}