package com.picturecolor.client.newmvi.login.store

import com.badoo.reaktive.annotations.EventsOnAnyScheduler
import com.badoo.reaktive.single.*
import com.picturecolor.client.domain.repository.LoginRepository
import com.picturecolor.client.coroutines.singleFromCoroutine

interface LoginProcessor {

  @EventsOnAnyScheduler
  fun auth(firstName: String, secondName: String): Single<Result>

  @EventsOnAnyScheduler
  fun tryToAuth(): Single<Result>

  sealed class Result {
    object Success : Result()
    data class Error(val message: String) : Result()
  }
}

internal class LoginProcessorImpl(
  private val repository: LoginRepository
) : LoginProcessor {

  override fun auth(firstName: String, secondName: String): Single<LoginProcessor.Result> =
    singleFromCoroutine {
      repository.auth(firstName, secondName)
    }
      .map { LoginProcessor.Result.Success }
      .onErrorReturnValue(LoginProcessor.Result.Error("Произошла ошибка при авторизации"))

  override fun tryToAuth(): Single<LoginProcessor.Result> =
    singleFromCoroutine {
      repository.tryToAuth()
    }
      .map { LoginProcessor.Result.Success }
      .onErrorReturnValue(LoginProcessor.Result.Error("Произошла ошибка"))

}