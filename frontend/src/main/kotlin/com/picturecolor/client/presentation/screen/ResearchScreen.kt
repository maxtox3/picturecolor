package com.picturecolor.client.presentation.screen

import com.picturecolor.client.material.*
import com.picturecolor.client.material.button.mIconButton
import com.picturecolor.client.presentation.screen.ComponentStyles.appFrameContainerStyle
import com.picturecolor.client.presentation.screen.ComponentStyles.mainContentContainerStyle
import kotlinext.js.jsObject
import kotlinx.css.*
import kotlinx.html.DIV
import react.*
import react.dom.div
import react.dom.jsStyle
import styled.StyleSheet
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

class ResearchScreen(props: ResearchProps) :
  RComponent<ResearchProps, ResearchState>(props) {

  private val drawerWidth = 300

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
  var leftDrawerOpen: Boolean = false
  var rightDrawerOpen: Boolean = false
}


interface ResearchProps : RProps {
}

fun RBuilder.result() = child(ResearchScreen::class) {
}