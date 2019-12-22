package com.picturecolor.client.presentation.app

import com.picturecolor.client.material.*
import com.picturecolor.client.material.styles.ThemeOptions
import com.picturecolor.client.material.styles.createMuiTheme
import com.picturecolor.client.presentation.navigator.Screen
import com.picturecolor.client.presentation.screen.*
import debugLog
import kotlinx.css.*
import react.*
import styled.css
import styled.styledDiv

abstract class App : RComponent<RProps, AppState>() {

  private var themeColor = "dark"

  init {
    state = AppState()
  }

  override fun RBuilder.render() {
    mCssBaseline()
    @Suppress("UnsafeCastFromDynamic")
    val themeOptions: ThemeOptions = js("({palette: { type: 'placeholder', primary: {main: 'placeholder'}}})")
    themeOptions.palette?.type = themeColor
    themeOptions.palette?.primary.main = Colors.Pink.shade500.toString()
    themeOptions.spacing = 1
    mThemeProvider(createMuiTheme(themeOptions)) {
      mContainer {
        attrs {
          component = "main"
          maxWidth = "xs"
        }
        mCssBaseline()
        styledDiv {
          css {
            marginTop = 9.spacingUnits
            display = Display.flex
            flexDirection = FlexDirection.column
            alignItems = Align.center
          }
          debugLog("state: ${state.screen}")
          when (state.screen) {
            Screen.AUTH -> auth {
              setState { screen = Screen.QUESTION }
            }
            Screen.QUESTION -> question {
              setState { screen = Screen.RESULT }
            }
            Screen.RESULT -> result()
          }
        }
      }
    }
  }
}

class AppState : RState {
  var screen: Screen = Screen.AUTH
  var researchId: Int = -1
}

fun RBuilder.app() = child(App::class) {}