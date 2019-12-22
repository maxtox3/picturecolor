package com.picturecolor.client.newmvi.login.store

import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.scheduler.computationScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.single.map
import com.badoo.reaktive.single.observeOn
import com.badoo.reaktive.single.subscribe
import com.badoo.reaktive.subject.behavior.behaviorSubject
import com.picturecolor.client.newmvi.login.store.LoginStore.Intent
import com.picturecolor.client.newmvi.login.store.LoginStore.State

internal class LoginStoreImpl(
  private val loginProcessor: LoginProcessor
) : LoginStore {

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
      is Intent.Init -> checkAuth()

      is Intent.Auth -> auth(intent.firstName, intent.secondName)
    }

  private fun auth(firstName: String, secondName: String): Disposable? =
    if (state.isLoading) {
      null
    } else {
      onResult(Effect.LoadingStarted)

      loginProcessor
        .auth(firstName, secondName)
        .observeOn(computationScheduler)
        .map {
          when (it) {
            is LoginProcessor.Result.Success -> Effect.Authorized
            is LoginProcessor.Result.Error -> Effect.AuthorizationFailed(it.message)
          }
        }
        .observeOn(mainScheduler)
        .subscribe(isThreadLocal = true, onSuccess = ::onResult)
    }

  private fun checkAuth(): Disposable? =
    if (state.isLoading) {
      null
    } else {
      onResult(Effect.LoadingStarted)

      loginProcessor
        .tryToAuth()
        .observeOn(computationScheduler)
        .map {
          when (it) {
            is LoginProcessor.Result.Success -> Effect.Authorized
            is LoginProcessor.Result.Error -> Effect.AuthorizationFailed(it.message)
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
    object Authorized : Effect()
    data class AuthorizationFailed(val error: String) : Effect()
    object DismissErrorRequested : Effect()
  }

  private object Reducer {
    operator fun invoke(effect: Effect, state: State): State =
      when (effect) {
        is Effect.LoadingStarted -> state.copy(isLoading = true, error = "")
        is Effect.Authorized -> state.copy(isLoading = false, error = "", authorized = true)
        is Effect.AuthorizationFailed -> state.copy(isLoading = false, error = effect.error)
        is Effect.DismissErrorRequested -> state.copy(error = "")
      }
  }
}