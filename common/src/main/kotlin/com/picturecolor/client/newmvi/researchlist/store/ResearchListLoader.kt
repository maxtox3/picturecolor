package com.picturecolor.client.newmvi.researchlist.store

import com.badoo.reaktive.annotations.EventsOnAnyScheduler
import com.badoo.reaktive.single.*
import com.picturecolor.client.domain.repository.ResearchRepository
import com.picturecolor.client.coroutines.singleFromCoroutine
import com.picturecolor.client.model.Research

interface ResearchListLoader {

  @EventsOnAnyScheduler
  fun load(): Single<Result>

  sealed class Result {
    data class Success(val data: List<Research>) : Result()
    data class Error(val message: String) : Result()
  }
}

internal class ResearchListLoaderImpl(
  private val repository: ResearchRepository
) : ResearchListLoader {

  override fun load(): Single<ResearchListLoader.Result> =
    singleFromCoroutine {
      repository.getResearches()
    }
      .map { ResearchListLoader.Result.Success(it) }
      .onErrorReturnValue(ResearchListLoader.Result.Error("Произошла ошибка"))

}