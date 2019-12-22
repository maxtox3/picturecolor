package com.picturecolor.client.newmvi.researchmvi.binder

import com.picturecolor.client.newmvi.researchmvi.store.ResearchStore
import com.picturecolor.client.newmvi.researchmvi.view.ResearchView

internal object ResearchStateToViewModelMapper {

  operator fun invoke(state: ResearchStore.State): ResearchView.ResearchViewModel =
    ResearchView.ResearchViewModel(
      isLoading = state.isLoading,
      error = state.error,
      data = state.data,
      gridModel = state.gridModel
    )
}