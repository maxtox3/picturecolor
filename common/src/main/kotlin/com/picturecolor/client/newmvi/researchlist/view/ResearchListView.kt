package com.picturecolor.client.newmvi.researchlist.view

import com.badoo.reaktive.subject.publish.PublishSubject
import com.picturecolor.client.newmvi.researchmvi.BaseEvent
import com.picturecolor.client.newmvi.researchmvi.BaseView
import com.picturecolor.client.model.Research

interface ResearchListView : BaseView<ResearchListView.Event> {

  val events: PublishSubject<Event>

  fun show(model: ResearchListViewModel)

  data class ResearchListViewModel(
    val isLoading: Boolean,
    val error: String,
    val data: List<Research>
  )

  sealed class Event : BaseEvent {
    object Init : Event()
    object ErrorShown : Event()

  }
}