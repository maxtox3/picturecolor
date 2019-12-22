package com.picturecolor.client.newmvi.researchlist.store

import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.observable.Observable
import com.picturecolor.client.model.ImageModel
import com.picturecolor.client.model.TestModel

interface TestStore : Disposable {

  val states: Observable<State>

  fun accept(intent: Intent)

  data class State(
    val isLoading: Boolean = false,
    val error: String = "",
    val currentImage: ImageModel? = null,
    val testModel: TestModel? = null,
    val testDone: Boolean = false
  )

  sealed class Intent {
    object Init : Intent()
    object DismissError : Intent()
    object NextImage : Intent()
    object UpdatePic : Intent()
  }
}