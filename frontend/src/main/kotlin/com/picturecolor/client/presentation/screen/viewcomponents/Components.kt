package com.picturecolor.client.presentation.screen.viewcomponents

import com.picturecolor.client.material.list.mList
import com.picturecolor.client.material.list.mListItemWithIcon
import com.picturecolor.client.material.mDivider
import com.picturecolor.client.material.themeContext
import com.picturecolor.client.material.toolbarJsCssToPartialCss
import kotlinx.css.*
import kotlinx.html.Tag
import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import kotlinx.html.role
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.*
import styled.css
import styled.styledDiv

fun RBuilder.alert(message: String = "") = if (message.isNotEmpty()) {
  div("alert alert-danger") {
    +message
  }
} else {
  EmptyElement
}


fun RBuilder.loading(isLoading: Boolean) = if (isLoading) {
  div("spinner") {
    div("dot1") { }
    div("dot2") { }
  }
} else {
  EmptyElement
}

object EmptyElement : ReactElement {
  override val props = object : RProps {}
}

fun RDOMBuilder<Tag>.options() {
  select {
    attrs.size = "1"
    option {
      attrs.selected = true
      +"тип"
    }
    option { +"С" }
    option { +"П" }
    option { +"М" }
  }
}

fun RDOMBuilder<Tag>.mipMethodBtn(
  name: String,
  mipMethodOfState: Int,
  mipMethod: Int,
  changeListener: (Int) -> Unit
) {
  button(classes = "btn") {
    +name

    attrs.onClickFunction = { changeListener(mipMethod) }
    if (mipMethodOfState == mipMethod) {
      attrs.classes = setOf("btn btn-primary menu-button ")
    } else {
      attrs.classes = setOf("btn btn-outline-primary menu-button ")
    }
  }
}

fun RBuilder.spacer() {
  themeContext.Consumer { theme ->
    // This puts in a spacer to get below the AppBar.
    styledDiv {
      css {
        toolbarJsCssToPartialCss(theme.mixins.toolbar)
      }
    }
    mDivider { }
  }
}

fun RBuilder.listMenu(fullWidth: Boolean, drawerWidth: Int): ReactElement {
  return themeContext.Consumer { theme ->
    div {
      attrs {
        role = "button"
      }
    }
    mList {
      css {
        backgroundColor = Color(theme.palette.background.paper)
        width = if (fullWidth) LinearDimension.auto else drawerWidth.px
      }
      mListItemWithIcon("search", "Просмотренные", divider = false)
      mListItemWithIcon("done", "Оконченные", divider = false)
    }
  }
}
