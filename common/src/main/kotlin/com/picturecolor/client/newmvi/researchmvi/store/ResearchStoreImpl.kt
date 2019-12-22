package com.picturecolor.client.newmvi.researchmvi.store

import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.scheduler.computationScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.single.*
import com.badoo.reaktive.subject.behavior.behaviorSubject
import com.picturecolor.client.model.*
import com.picturecolor.client.newmvi.researchmvi.store.ResearchStore.Intent
import com.picturecolor.client.newmvi.researchmvi.store.ResearchStore.State

internal class ResearchStoreImpl(
  private val researchDataLoader: ResearchDataLoader,
  private val researchMarksLoader: ResearchMarksLoader
) : ResearchStore {

  private val _states = behaviorSubject(State(data = null, gridModel = initialGridModel()))
  override val states: Observable<State> = _states
  private val state: State get() = _states.value

  private val disposables = CompositeDisposable()
  override val isDisposed: Boolean get() = disposables.isDisposed

  override fun dispose() {
    disposables.dispose()
    _states.onComplete()
  }

  override fun accept(intent: Intent) {
    execute(intent)?.also(disposables::add)
  }

  private fun execute(intent: Intent): Disposable? =
    when (intent) {
      is Intent.DismissError -> {
        onResult(Effect.DismissErrorRequested)
        null
      }
      is Intent.Init -> {
        loadData(intent.researchId)
      }
      is Intent.DeleteMark -> null
      is Intent.SaveMark -> {
        null
      }
      is Intent.UpdateMark -> null
      is Intent.DeleteCalled -> {
        null
      }
      is Intent.ChangeGrid -> {

        null
      }
    }

  private fun loadData(researchId: Int): Disposable? =
    if (state.isLoading) {
      null
    } else {
      onResult(Effect.LoadingStarted)

      researchDataLoader
        .load(researchId)
        .observeOn(computationScheduler)
        .map {
          when (it) {
            is ResearchDataLoader.Result.Success -> {
              loadMarks(researchId)
              Effect.DataLoaded(it.researchData, gridModel = state.gridModel)
            }
            is ResearchDataLoader.Result.Error -> Effect.LoadingFailed(it.message)
          }
        }
        .observeOn(mainScheduler)
        .subscribe(isThreadLocal = true, onSuccess = ::onResult)
    }

  private fun loadMarks(researchId: Int) {
    researchMarksLoader
      .load(researchId)
      .observeOn(computationScheduler)
      .map {
        when (it) {
          is ResearchMarksLoader.Result.Success -> {
            Effect.MarksLoaded
          }
          is ResearchMarksLoader.Result.Error -> Effect.LoadingFailed(it.message)
        }
      }
      .observeOn(mainScheduler)
      .subscribe(isThreadLocal = true, onSuccess = ::onResult)
  }

  private fun onResult(effect: Effect) {
    _states.onNext(Reducer(effect, _states.value))
  }

  private sealed class Effect {
    object LoadingStarted : Effect()
    class DataLoaded(val data: ResearchSlicesSizesData?, val gridModel: GridModel) : Effect()
    object MarksLoaded : Effect()
    data class LoadingFailed(val error: String) : Effect()
    object DismissErrorRequested : Effect()

    object AreaSaved : Effect()
    class AreaSaveError(val message: String) : Effect()

    object AreaDeleted : Effect()
    class AreaDeleteError(val message: String) : Effect()

    object AreaUpdated : Effect()
    class AreaUpdateError(val message: String) : Effect()
    class GridChanged(val newGridModel: GridModel) : Effect()
  }

  private object Reducer {
    operator fun invoke(effect: Effect, state: State): State =
      when (effect) {
        is Effect.LoadingStarted -> state.copy(isLoading = true, error = "")
        is Effect.DataLoaded -> state.copy(
          isLoading = false,
          error = "",
          data = effect.data,
          researchId = effect.data?.researchId ?: -1,
          gridModel = effect.gridModel
        )
        is Effect.LoadingFailed -> state.copy(isLoading = false, error = effect.error)
        is Effect.DismissErrorRequested -> state.copy(error = "")
        is Effect.MarksLoaded -> state
        is Effect.AreaDeleted -> state.copy(isLoading = false)
        is Effect.AreaDeleteError -> state.copy(isLoading = false, error = effect.message)
        is Effect.AreaSaved -> state.copy(isLoading = false)
        is Effect.AreaSaveError -> state.copy(isLoading = false, error = effect.message)
        is Effect.AreaUpdated -> state.copy(isLoading = false)
        is Effect.AreaUpdateError -> state.copy(isLoading = false, error = effect.message)
        is Effect.GridChanged -> state.copy(gridModel = effect.newGridModel)
      }
  }
}