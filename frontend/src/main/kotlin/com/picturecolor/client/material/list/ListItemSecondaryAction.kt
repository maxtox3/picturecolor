package com.picturecolor.client.material.list

import com.picturecolor.client.material.button.MButtonBaseProps
import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler


@JsModule("@material-ui/core/ListItemSecondaryAction")
private external val listItemSecondaryActionModule: dynamic

@Suppress("UnsafeCastFromDynamic")
val listItemSecondaryActionComponent: RComponent<MButtonBaseProps, RState> = listItemSecondaryActionModule.default

fun RBuilder.mListItemSecondaryAction(
        className: String? = null,
        handler: StyledHandler<MButtonBaseProps>? = null) = createStyled(listItemSecondaryActionComponent) {
    setStyledPropsAndRunHandler(className, handler)
}
