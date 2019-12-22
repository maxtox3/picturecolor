package com.picturecolor.client.material.dialog

import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler
import styled.StyledProps


@JsModule("@material-ui/core/DialogTitle")
private external val dialogTitleModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val dialogTitleComponent: RComponent<MDialogTitleProps, RState> = dialogTitleModule.default

interface MDialogTitleProps : StyledProps {
    var disableTypography: Boolean
}

fun RBuilder.mDialogTitle(
        text: String,
        disableTypography: Boolean = false,

        className: String? = null,
        handler: StyledHandler<MDialogTitleProps>? = null) = createStyled(dialogTitleComponent) {
    attrs.disableTypography = disableTypography

    childList.add(text)

    setStyledPropsAndRunHandler(className, handler)
}



