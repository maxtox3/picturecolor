package com.picturecolor.client.presentation.screen

import com.badoo.reaktive.subject.publish.PublishSubject
import com.badoo.reaktive.subject.publish.publishSubject
import com.picturecolor.client.constants.ANGLE_PARAM
import com.picturecolor.client.constants.END_POINT
import com.picturecolor.client.constants.IMAGE_ROUTE
import com.picturecolor.client.constants.NAME_PARAM
import com.picturecolor.client.material.button.mButton
import com.picturecolor.client.material.mTypography
import com.picturecolor.client.material.themeContext
import com.picturecolor.client.model.ImageModel
import com.picturecolor.client.newmvi.researchlist.view.TestView
import com.picturecolor.client.presentation.di.injectResearchList
import debugLog
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.display
import kotlinx.css.flexDirection
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg

class TestScreen(props: ResearchListProps) :
  RComponent<ResearchListProps, TestState>(props), TestView {

  init {
    state = TestState()
  }

  private val binder = injectResearchList()
  override val events: PublishSubject<TestView.Event> = publishSubject()

  override fun show(model: TestView.ResearchListViewModel) {
    setState {
      loading = model.isLoading
      error = model.error
      image = model.data
    }
  }

  override fun componentDidMount() {
    binder.attachView(this)
    binder.onStart()
    dispatch(TestView.Event.Init)
  }

  override fun RBuilder.render() {
    debugLog("new state income ${state.image?.name}")
    themeContext.Consumer { theme ->
      state.image?.let {
        mTypography("Реальны ли цвета на картинке?")
        styledImg(src = "$END_POINT/$IMAGE_ROUTE?$NAME_PARAM=${it.name}&$ANGLE_PARAM=${it.angle}") {
        }
        styledDiv {
          css {
            display = Display.flex
            flexDirection = FlexDirection.row
          }
          mButton("Да", onClick = { dispatch(TestView.Event.OkClicked) })
          mButton("Нет", onClick = { dispatch(TestView.Event.CancelClicked) })
        }
      }
    }

  }

  override fun componentWillUnmount() {
    binder.detachView()
    binder.onStop()
  }

  override fun dispatch(event: TestView.Event) {
    events.onNext(event)
  }
}

class TestState(
  var loading: Boolean = true,
  var error: String = "",
  var image: ImageModel? = null
) : RState


interface ResearchListProps : RProps {
  var onTestEnd: () -> Unit
}

fun RBuilder.question(onTestEnd: () -> Unit) = child(TestScreen::class) {
  attrs.onTestEnd = onTestEnd
}