package com.picturecolor.client.newmvi.researchlist.store

import com.badoo.reaktive.annotations.EventsOnAnyScheduler
import com.badoo.reaktive.single.*
import com.picturecolor.client.domain.repository.TestRepository
import com.picturecolor.client.coroutines.singleFromCoroutine
import com.picturecolor.client.model.TestModel

interface TestLoader {

  @EventsOnAnyScheduler
  fun load(): Single<Result>

  sealed class Result {
    data class Success(val data: TestModel) : Result()
    data class Error(val message: String) : Result()
  }
}

internal class TestLoaderImpl(
  private val repository: TestRepository
) : TestLoader {

  override fun load(): Single<TestLoader.Result> =
    singleFromCoroutine {
      repository.getTest()
    }
      .map { TestLoader.Result.Success(it) }
      .onErrorReturnValue(TestLoader.Result.Error("Произошла ошибка"))

}