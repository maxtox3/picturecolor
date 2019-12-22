package com.picturecolor.client.newmvi.researchlist.view

import com.badoo.reaktive.subject.publish.PublishSubject
import com.picturecolor.client.model.ImageModel
import com.picturecolor.client.newmvi.BaseEvent
import com.picturecolor.client.newmvi.BaseView

interface TestView : BaseView<TestView.Event> {

  val events: PublishSubject<Event>

  fun show(model: ResearchListViewModel)

  data class ResearchListViewModel(
    val isLoading: Boolean,
    val error: String,
    val data: ImageModel?
  )

  sealed class Event : BaseEvent {
    object Init : Event()
    object ErrorShown : Event()
    object OkClicked : Event()
    object CancelClicked: Event()
  }
}