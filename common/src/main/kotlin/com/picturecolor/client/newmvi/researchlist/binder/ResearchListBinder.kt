package com.picturecolor.client.newmvi.researchlist.binder

import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.subscribe
import com.picturecolor.client.newmvi.researchlist.store.ResearchListStore
import com.picturecolor.client.newmvi.researchlist.view.ResearchListView

class ResearchListBinder(
  private val store: ResearchListStore
) {

  private var disposables = CompositeDisposable()
  private var view: ResearchListView? = null

  fun attachView(view: ResearchListView) {
    this.view = view
  }

  fun onStart() {
    disposables +=
      requireNotNull(view)
        .events
        .map(ResearchListViewEventToIntentMapper::invoke)
        .subscribe(isThreadLocal = true, onNext = store::accept)

    disposables +=
      store
        .states
        .map(ResearchListStateToViewModelMapper::invoke)
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