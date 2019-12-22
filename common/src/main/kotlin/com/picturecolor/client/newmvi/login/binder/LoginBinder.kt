package com.picturecolor.client.newmvi.login.binder

import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.subscribe
import com.picturecolor.client.newmvi.login.store.LoginStore
import com.picturecolor.client.newmvi.login.view.LoginView

class LoginBinder(
  private val store: LoginStore
) {

  private var disposables = CompositeDisposable()
  private var view: LoginView? = null

  fun attachView(view: LoginView) {
    this.view = view
  }

  fun onStart() {
    disposables +=
      requireNotNull(view)
        .events
        .map(LoginViewEventToIntentMapper::invoke)
        .subscribe(isThreadLocal = true, onNext = store::accept)

    disposables +=
      store
        .states
        .map(LoginStateToViewModelMapper::invoke)
        .subscribe(isThreadLocal = true, onNext = { requireNotNull(view).show(it) })
  }

  fun onStop() {
    disposables.clear()
  }

  fun detachView() {
    view = null
  }

  fun onDestroy() {
    store.dispose()
  }
}