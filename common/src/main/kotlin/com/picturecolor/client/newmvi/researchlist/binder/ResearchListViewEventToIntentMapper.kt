package com.picturecolor.client.newmvi.researchlist.binder

import com.picturecolor.client.newmvi.researchlist.store.ResearchListStore
import com.picturecolor.client.newmvi.researchlist.view.ResearchListView

internal object ResearchListViewEventToIntentMapper {

  operator fun invoke(event: ResearchListView.Event): ResearchListStore.Intent =
    when (event) {
      is ResearchListView.Event.ErrorShown -> ResearchListStore.Intent.DismissError
      is ResearchListView.Event.Init -> ResearchListStore.Intent.Init
    }
}