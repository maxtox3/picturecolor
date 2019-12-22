package com.picturecolor.client.newmvi.researchmvi.binder

import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.observable.*
import com.picturecolor.client.newmvi.researchmvi.store.ResearchStore
import com.picturecolor.client.newmvi.researchmvi.view.ResearchView
import com.picturecolor.client.model.AreaToSave
import com.picturecolor.client.model.SelectedArea

class ResearchBinder(
  private val store: ResearchStore,
  private val deleteAreaObservable: Observable<Int>,
  private val newAreaObservable: Observable<AreaToSave>,
  private val areaToUpdateObservable: Observable<SelectedArea>
) {

  private var disposables = CompositeDisposable()
  private var view: ResearchView? = null

  fun attachView(view: ResearchView) {
    this.view = view
  }

  fun onStart() {
    disposables +=
      requireNotNull(view)
        .events
        .map (ResearchViewEventToIntentMapper::invoke)
        .subscribe(isThreadLocal = true, onNext = store::accept)

    disposables +=
      store
        .states
        .map(ResearchStateToViewModelMapper::invoke)
        .subscribe(isThreadLocal = true, onNext = { requireNotNull(view).show(it) })

    disposables +=
      deleteAreaObservable
        .map { ResearchStore.Intent.DeleteMark(it) }
        .subscribe(isThreadLocal = true, onNext = store::accept)

    disposables +=
      newAreaObservable
        .map { ResearchStore.Intent.SaveMark(it) }
        .subscribe(isThreadLocal = true, onNext = store::accept)

    disposables +=
      areaToUpdateObservable
        .map { ResearchStore.Intent.UpdateMark(it) }
        .subscribe(isThreadLocal = true, onNext = store::accept)
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