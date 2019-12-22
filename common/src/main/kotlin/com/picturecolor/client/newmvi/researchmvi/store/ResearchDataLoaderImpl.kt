package com.picturecolor.client.newmvi.researchmvi.store

import com.badoo.reaktive.single.*
import com.picturecolor.client.domain.repository.ResearchRepository
import com.picturecolor.client.coroutines.singleFromCoroutine

internal class ResearchDataLoaderImpl(
  private val repository: ResearchRepository
) : ResearchDataLoader {

  override fun load(researchId: Int): Single<ResearchDataLoader.Result> =
    singleFromCoroutine {
      repository.initResearch(researchId)
    }
      .map {
        val result = it.copy(researchId = researchId)
        result
      }
      .map(ResearchDataLoader.Result::Success)
      .onErrorReturnValue(ResearchDataLoader.Result.Error("Произошла ошибка"))
}