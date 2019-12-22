package com.picturecolor.client.newmvi.researchlist.binder

import com.picturecolor.client.newmvi.researchlist.store.TestStore
import com.picturecolor.client.newmvi.researchlist.view.TestView

internal object TestStateToViewModelMapper {

  operator fun invoke(state: TestStore.State): TestView.ResearchListViewModel =
    TestView.ResearchListViewModel(
      isLoading = state.isLoading,
      error = state.error,
      data = state.currentImage
    )
}