package com.picturecolor.client.newmvi.researchlist.binder

import com.picturecolor.client.newmvi.researchlist.store.TestStore
import com.picturecolor.client.newmvi.researchlist.view.TestView

internal object TestViewEventToIntentMapper {

  operator fun invoke(event: TestView.Event): TestStore.Intent =
    when (event) {
      is TestView.Event.ErrorShown -> TestStore.Intent.DismissError
      is TestView.Event.Init -> TestStore.Intent.Init
      TestView.Event.OkClicked -> TestStore.Intent.NextImage
      TestView.Event.CancelClicked -> TestStore.Intent.UpdatePic
    }
}