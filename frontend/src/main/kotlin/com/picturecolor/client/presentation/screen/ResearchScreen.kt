package com.picturecolor.client.presentation.screen

import com.badoo.reaktive.subject.publish.PublishSubject
import com.badoo.reaktive.subject.publish.publishSubject
import com.picturecolor.client.constants.CutsGridType
import com.picturecolor.client.material.*
import com.picturecolor.client.material.button.mIconButton
import com.picturecolor.client.model.GridModel
import com.picturecolor.client.model.ResearchSlicesSizesData
import com.picturecolor.client.newmvi.researchmvi.view.ResearchView
import com.picturecolor.client.presentation.di.injectNewResearch
import com.picturecolor.client.presentation.screen.ComponentStyles.appFrameContainerStyle
import com.picturecolor.client.presentation.screen.ComponentStyles.mainContentContainerStyle
import kotlinext.js.jsObject
import kotlinx.css.*
import kotlinx.html.DIV
import org.w3c.dom.events.KeyboardEvent
import react.*
import react.dom.div
import react.dom.jsStyle
import styled.*
import kotlin.browser.window

class ResearchScreen(props: ResearchProps) :
  RComponent<ResearchProps, ResearchState>(props), ResearchView {

  private val drawerWidth = 300
  private val binder = injectNewResearch()
  override val events: PublishSubject<ResearchView.Event> = publishSubject()

  override fun show(model: ResearchView.ResearchViewModel) {
    setState {
      loading = model.isLoading
      error = model.error
      data = model.data
      gridModel = model.gridModel
    }
  }

  override fun componentDidMount() {
    binder.attachView(this@ResearchScreen)
    binder.onStart()
    window.addEventListener(type = "keydown", callback = {
      val keyboardEvent = it as KeyboardEvent
      if (keyboardEvent.keyCode == 8 || keyboardEvent.keyCode == 46)
        dispatch(ResearchView.Event.Delete)
    })
  }

  override fun RBuilder.render() {
    mCssBaseline()
    themeContext.Consumer { _ ->
      //container for drawers and main content
      styledDiv {
        css(appFrameContainerStyle)


        //mainContent
        styledDiv {
          css(mainContentContainerStyle)
          attrs {
            css {
              marginLeft = if (state.leftDrawerOpen) drawerWidth.px else 7.spacingUnits
            }
          }
        }
        rightDrawer()
      }
    }
  }

  private fun StyledDOMBuilder<DIV>.rightDrawer() {
    val rightDrawerProps: MPaperProps = jsObject { }
    rightDrawerProps.asDynamic().style = kotlinext.js.js {
      position = "relative"
      height = 100.pct
      minHeight = 100.vh
      if (!state.rightDrawerOpen) {
        overflowX = "hidden"
        width = 7.spacingUnits.value
      } else {
        width = 100.pct
        minWidth = 350.px
      }
    }
    mDrawer(
      state.rightDrawerOpen,
      MDrawerAnchor.right,
      MDrawerVariant.permanent,
      paperProps = rightDrawerProps
    ) {

      div {
        attrs.jsStyle = kotlinext.js.js { display = "flex"; alignItems = "center"; justifyContent = "flex-start"; height = 64 }
        if (state.rightDrawerOpen) {
          mIconButton(
            "chevron_right",
            onClick = { setState { rightDrawerOpen = false } }
          )
        } else {
          mIconButton(
            "chevron_left",
            onClick = { setState { rightDrawerOpen = true } }
          )
        }
      }

      mDivider()
    }
  }

  override fun componentWillUnmount() {
    binder.detachView()
    binder.onStop()
  }

  override fun dispatch(event: ResearchView.Event) {
    events.onNext(event)
  }
}

object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
  val appFrameContainerStyle by css {
    flex(1.0)
    display = Display.flex
    flexDirection = FlexDirection.row
  }
  val mainContentContainerStyle by css {
    flex(1.0)
    display = Display.flex
    flexDirection = FlexDirection.row
  }

  val columnOfRowsStyle by css {
    flex(1.0)
    display = Display.flex
    flexDirection = FlexDirection.column
  }
  val rowOfColumnsStyle by css {
    flex(1.0)
    display = Display.flex
    flexDirection = FlexDirection.row
  }
  val cutContainerStyle by css {
    flex(1.0)
    display = Display.flex
    flexDirection = FlexDirection.column
    position = Position.relative
  }
  val blackContainerStyle by css {
    flex(1.0)
    display = Display.flex
    flexDirection = FlexDirection.column
    background = "#000"
    textAlign = TextAlign.center
    position = Position.relative
  }
}

class ResearchState : RState {
  var loading: Boolean = true
  var error: String = ""
  var data: ResearchSlicesSizesData? = null
  var leftDrawerOpen: Boolean = false
  var rightDrawerOpen: Boolean = false
  var gridModel: GridModel? = null
}


interface ResearchProps : RProps {
}

fun RBuilder.result() = child(ResearchScreen::class) {
}