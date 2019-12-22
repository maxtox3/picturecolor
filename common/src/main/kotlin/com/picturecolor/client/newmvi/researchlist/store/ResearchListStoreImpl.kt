package com.picturecolor.client.newmvi.researchlist.store

import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.scheduler.computationScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.single.map
import com.badoo.reaktive.single.observeOn
import com.badoo.reaktive.single.subscribe
import com.badoo.reaktive.subject.behavior.behaviorSubject
import com.picturecolor.client.newmvi.researchlist.store.ResearchListStore.Intent
import com.picturecolor.client.newmvi.researchlist.store.ResearchListStore.State
import com.picturecolor.client.model.Research

internal class ResearchListStoreImpl(
  private val researchListLoader: ResearchListLoader
) : ResearchListStore {

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
    execute(intent)?.also(disposables::add)
  }

  private fun execute(intent: Intent): Disposable? =
    when (intent) {
      is Intent.DismissError -> {
        onResult(Effect.DismissErrorRequested)
        null
      }
      is Intent.Init -> load()
    }

  private fun load(): Disposable? =
    if (state.isLoading) {
      null
    } else {
      onResult(Effect.LoadingStarted)

      researchListLoader
        .load()
        .observeOn(computationScheduler)
        .map {
          when (it) {
            is ResearchListLoader.Result.Success -> Effect.LoadResearchesSuccess(it.data)
            is ResearchListLoader.Result.Error -> Effect.AuthorizationFailed(it.message)
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
    data class LoadResearchesSuccess(val researches: List<Research>) : Effect()
    data class AuthorizationFailed(val error: String) : Effect()
    object DismissErrorRequested : Effect()
  }

  private object Reducer {
    operator fun invoke(effect: Effect, state: State): State =
      when (effect) {
        is Effect.LoadingStarted -> state.copy(isLoading = true, error = "")
        is Effect.LoadResearchesSuccess -> state.copy(
          isLoading = false,
          error = "",
          data = effect.researches
        )
        is Effect.AuthorizationFailed -> state.copy(isLoading = false, error = effect.error)
        is Effect.DismissErrorRequested -> state.copy(error = "")
      }
  }
}