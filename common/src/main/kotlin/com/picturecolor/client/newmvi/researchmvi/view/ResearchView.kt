package com.picturecolor.client.newmvi.researchmvi.view

import com.badoo.reaktive.subject.publish.PublishSubject
import com.picturecolor.client.constants.CutsGridType
import com.picturecolor.client.newmvi.researchmvi.BaseEvent
import com.picturecolor.client.newmvi.researchmvi.BaseView
import com.picturecolor.client.model.GridModel
import com.picturecolor.client.model.ResearchSlicesSizesData

interface ResearchView : BaseView<ResearchView.Event> {

  val events: PublishSubject<Event>

  fun show(model: ResearchViewModel)

  data class ResearchViewModel(
    val isLoading: Boolean,
    val error: String,
    val data: ResearchSlicesSizesData?,
    val gridModel: GridModel
  )

  sealed class Event: BaseEvent {
    data class Init(val researchId: Int): Event()
    class GridChanged(val type: CutsGridType) : Event()

    object ErrorShown: Event()
    object Delete : Event()
  }
}