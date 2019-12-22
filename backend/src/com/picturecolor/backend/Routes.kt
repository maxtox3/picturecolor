package com.picturecolor.backend

import com.picturecolor.backend.controller.AuthController
import com.picturecolor.backend.controller.ResultController
import com.picturecolor.backend.controller.TestController
import com.picturecolor.client.constants.*
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.response.respond
import io.ktor.routing.*

fun Application.main(
  loginController: AuthController,
  researchController: TestController,
  marksController: ResultController
) {

  processRequests(
    loginController,
    researchController,
    marksController
  )
}

fun Application.processRequests(
  loginController: AuthController,
  researchController: TestController,
  marksController: ResultController
) {
  routing {

    route("/") {
      get { call.respond("hello world") }
    }

    post("/login") {
      loginController.login(call)
    }

    get("$RESEARCH_ROUTE/{id}/img/") {
      researchController.getSlice(call)
    }

    authenticate("jwt") {

      route(RESEARCH_ROUTE) {
        get(LIST_ROUTE) {
          researchController.list(call)
        }
        get("$INIT_ROUTE/{id}") {
          researchController.init(call)
        }
        post("{id}") {
          researchController.get(call)
        }
        get(HOUNSFIELD_ROUTE){
          researchController.hounsfield(call)
        }
      }


      route(MARK_ROUTE) {
        post {
          marksController.create(call)
        }
        delete("{id}") {
          marksController.delete(call)
        }
        put("{id}") {
          marksController.update(call)
        }
        get {
          marksController.read(call)
        }
      }
    }
  }
}
