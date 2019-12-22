package com.picturecolor.client.newmvi.researchlist.store

import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.scheduler.computationScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.single.*
import com.badoo.reaktive.subject.behavior.behaviorSubject
import com.picturecolor.client.constants.END_ANGLE
import com.picturecolor.client.constants.INITIAL_ANGLE
import com.picturecolor.client.constants.STEP_ANGLE
import com.picturecolor.client.debugLog
import com.picturecolor.client.model.ImageModel
import com.picturecolor.client.model.TestModel
import com.picturecolor.client.newmvi.researchlist.store.TestStore.Intent
import com.picturecolor.client.newmvi.researchlist.store.TestStore.State

internal class TestStoreImpl(
  private val testLoader: TestLoader,
  private val imageLoader: ImageLoader
) : TestStore {

  private val _states = behaviorSubject(State())
  override val states: Observable<State> = _states
  private val state: State get() = _states.value

  private val disposables = CompositeDisposable()
  override val isDisposed: Boolean get() = disposables.isDisposed

  override fun dispose() {
    disposables.dispose()
    _states.onComplete()
  }

  override fun accept(intent: Intent) {
    execute(intent)?.also { disposables.add(it) }
  }

  private fun execute(intent: Intent): Disposable? =
    when (intent) {
      is Intent.DismissError -> {
        onResult(Effect.DismissErrorRequested)
        null
      }
      is Intent.Init -> load()
      Intent.NextImage -> checkNextImageIndeed()
      Intent.UpdatePic -> {
        val currentImage = state.currentImage
        if (currentImage?.angle == END_ANGLE) {
          checkNextImageIndeed()
        } else {
          if (currentImage != null) {
            loadNextImage(currentImage.name, currentImage.angle - STEP_ANGLE)
          } else {
            null
          }
        }
      }
    }

  private fun checkNextImageIndeed(): Disposable? {
    val name = state.currentImage?.name
    if (name != null) {
      state.testModel?.availableImages?.indexOf(name)?.let {
        val nextName = state.testModel?.availableImages?.get(it + 1)
        if (nextName == null) {
          onResult(Effect.TestPassed)
        } else {
          val nextAngle = INITIAL_ANGLE
          return loadNextImage(nextName, nextAngle)
        }
      }
    }
    return null
  }

  private fun init(effect: Effect): Single<Effect> {
    debugLog("inside init")
    return if (effect is Effect.LoadTestFailed) {
      debugLog("effect is Effect.LoadTestFailed")
      singleOf(effect)
    } else {
      val castEffect = effect as Effect.LoadTestSuccess
      val firstImage = castEffect.testModel.availableImages.first()
      debugLog("${effect::class}")
      debugLog("availableImagesSize =  ${castEffect.testModel.availableImages.size}")
      debugLog("firstImage = $firstImage")
      imageLoader
        .load(firstImage, INITIAL_ANGLE)
        .map {
          when (it) {
            is ImageLoader.Result.Success -> Effect.LoadInitialImageSuccess(
              it.data,
              effect.testModel
            )
            is ImageLoader.Result.Error -> Effect.LoadTestFailed(it.message)
          }
        }
    }
  }

  private fun load(): Disposable? =
    if (state.isLoading) {
      null
    } else {
      onResult(Effect.LoadingStarted)

      testLoader
        .load()
        .observeOn(computationScheduler)
        .map {
          when (it) {
            is TestLoader.Result.Success -> {
              debugLog("test loaded")
              Effect.LoadTestSuccess(it.data)
            }
            is TestLoader.Result.Error -> {
              debugLog("test load error")
              Effect.LoadTestFailed(it.message)
            }
          }
        }
        .flatMap { init(it) }
        .observeOn(mainScheduler)
        .subscribe(onSuccess = ::onResult)
    }

  private fun loadNextImage(name: String, angle: Int): Disposable? =
    if (state.isLoading) {
      null
    } else {
      onResult(Effect.LoadingStarted)

      imageLoader
        .load(name, angle)
        .observeOn(computationScheduler)
        .map {
          when (it) {
            is ImageLoader.Result.Success -> Effect.LoadImageSuccess(it.data)
            is ImageLoader.Result.Error -> Effect.LoadTestFailed(it.message)
          }
        }
        .observeOn(mainScheduler)
        .subscribe(onSuccess = ::onResult)
    }

  private fun onResult(effect: Effect) {
    _states.onNext(Reducer(effect, _states.value))
  }

  private sealed class Effect {
    object LoadingStarted : Effect()
    data class LoadTestSuccess(val testModel: TestModel) : Effect()
    data class LoadInitialImageSuccess(
      val imageModel: ImageModel,
      val testModel: TestModel
    ) : Effect()

    data class LoadImageSuccess(val image: ImageModel) : Effect()
    data class LoadTestFailed(val error: String) : Effect()
    object DismissErrorRequested : Effect()
    object TestPassed : Effect()
  }

  private object Reducer {
    operator fun invoke(effect: Effect, state: State): State =
      when (effect) {
        is Effect.LoadingStarted -> state.copy(isLoading = true, error = "")
        is Effect.LoadTestSuccess -> state.copy(
          isLoading = false,
          error = ""
        )
        is Effect.LoadInitialImageSuccess -> state.copy(
          isLoading = false,
          error = "",
          currentImage = effect.imageModel,
          testModel = effect.testModel
        )
        is Effect.LoadTestFailed -> state.copy(isLoading = false, error = effect.error)
        is Effect.DismissErrorRequested -> state.copy(error = "")
        is Effect.LoadImageSuccess -> state.copy(
          isLoading = false,
          error = "",
          currentImage = effect.image
        )
        Effect.TestPassed -> state.copy(isLoading = false, error = "", testDone = true)
      }
  }
}