package com.picturecolor.client.newmvi.researchlist.store

import com.badoo.reaktive.annotations.EventsOnAnyScheduler
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.map
import com.badoo.reaktive.single.onErrorReturnValue
import com.picturecolor.client.coroutines.singleFromCoroutine
import com.picturecolor.client.domain.repository.TestRepository
import com.picturecolor.client.model.ImageModel

interface ImageLoader {

  @EventsOnAnyScheduler
  fun load(imageName: String, angle: Int): Single<Result>

  sealed class Result {
    data class Success(val data: ImageModel) : Result()
    data class Error(val message: String) : Result()
  }
}

internal class ImageLoaderImpl(val repository: TestRepository) : ImageLoader {

  override fun load(imageName: String, angle: Int): Single<ImageLoader.Result> {
    return singleFromCoroutine {
      repository.getTestImage(imageName, angle)
    }
      .map { ImageLoader.Result.Success(it) }
      .onErrorReturnValue(ImageLoader.Result.Error("Произошла ошибка"))
  }
}