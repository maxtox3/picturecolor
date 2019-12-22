package com.picturecolor.client.material.list

import com.picturecolor.client.material.button.MButtonBaseProps
import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.mIcon
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler

@JsModule("@material-ui/core/ListItemIcon")
private external val listItemIconModule: dynamic

@Suppress("UnsafeCastFromDynamic")
val listItemIconComponent: RComponent<MButtonBaseProps, RState> = listItemIconModule.default

fun RBuilder.mListItemIcon(
        iconName: String? = null,
        className: String? = null,
        handler: StyledHandler<MButtonBaseProps>? = null) = createStyled(listItemIconComponent) {
    iconName?.let { mIcon(iconName) }
    setStyledPropsAndRunHandler(className, handler)
}
