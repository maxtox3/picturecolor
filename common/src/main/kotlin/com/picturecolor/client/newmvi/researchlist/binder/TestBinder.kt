package com.picturecolor.client.newmvi.researchlist.binder

import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.subscribe
import com.picturecolor.client.newmvi.researchlist.store.TestStore
import com.picturecolor.client.newmvi.researchlist.view.TestView

class TestBinder(
  private val store: TestStore
) {

  private var disposables = CompositeDisposable()
  private var view: TestView? = null

  fun attachView(view: TestView) {
    this.view = view
  }

  fun onStart() {
    disposables.add(
      requireNotNull(view)
        .events
        .map(TestViewEventToIntentMapper::invoke)
        .subscribe(isThreadLocal = true, onNext = store::accept))

    disposables.add(
      store
        .states
        .map(TestStateToViewModelMapper::invoke)
        .subscribe(isThreadLocal = true, onNext = { requireNotNull(view).show(it) }))
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