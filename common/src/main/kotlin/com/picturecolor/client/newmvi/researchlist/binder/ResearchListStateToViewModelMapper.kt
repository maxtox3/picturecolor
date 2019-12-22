package com.picturecolor.client.newmvi.researchlist.binder

import com.picturecolor.client.newmvi.researchlist.store.ResearchListStore
import com.picturecolor.client.newmvi.researchlist.view.ResearchListView

internal object ResearchListStateToViewModelMapper {

  operator fun invoke(state: ResearchListStore.State): ResearchListView.ResearchListViewModel =
    ResearchListView.ResearchListViewModel(
      isLoading = state.isLoading,
      error = state.error,
      data = state.data
    )
}