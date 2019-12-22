package com.picturecolor.client.newmvi.researchmvi.store

import com.badoo.reaktive.single.*
import com.picturecolor.client.domain.repository.ResearchRepository
import com.picturecolor.client.coroutines.singleFromCoroutine

internal class ResearchMarksLoaderImpl(
  private val repository: ResearchRepository
) : ResearchMarksLoader {

  override fun load(researchId: Int): Single<ResearchMarksLoader.Result> =
    singleFromCoroutine {
      repository.getMarks(researchId)
    }
      .map(ResearchMarksLoader.Result::Success)
      .onErrorReturnValue(ResearchMarksLoader.Result.Error("Произошла ошибка"))
}