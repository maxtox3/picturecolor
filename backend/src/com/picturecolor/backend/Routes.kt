package com.picturecolor.backend

import com.picturecolor.backend.controller.AuthController
import com.picturecolor.backend.controller.ResultController
import com.picturecolor.backend.controller.TestController
import com.picturecolor.client.constants.IMAGE_ROUTE
import com.picturecolor.client.constants.TEST_ROUTE
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing

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
  testController: TestController,
  resultController: ResultController
) {
  routing {

    route("/") {
      get { call.respond("hello world") }
    }

    post("/login") {
      loginController.login(call)
    }

    get(IMAGE_ROUTE) {
      testController.getImage(call)
    }

    authenticate("jwt") {

      route(TEST_ROUTE) {
        get {
          testController.init(call)
        }
      }

      route(IMAGE_ROUTE) {
        post {
          testController.updateImage(call)
        }
      }
    }
  }
}
