package com.picturecolor.client.newmvi.researchmvi.binder

import com.picturecolor.client.newmvi.researchmvi.store.ResearchStore
import com.picturecolor.client.newmvi.researchmvi.view.ResearchView

internal object ResearchViewEventToIntentMapper {

  operator fun invoke(event: ResearchView.Event): ResearchStore.Intent =
    when (event) {
      is ResearchView.Event.ErrorShown -> ResearchStore.Intent.DismissError
      is ResearchView.Event.Init -> ResearchStore.Intent.Init(event.researchId)
      is ResearchView.Event.Delete -> ResearchStore.Intent.DeleteCalled
      is ResearchView.Event.GridChanged -> ResearchStore.Intent.ChangeGrid(event.type)
    }
}