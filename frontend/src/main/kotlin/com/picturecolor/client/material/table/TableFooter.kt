package com.picturecolor.client.material.table

import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler
import styled.StyledProps


@JsModule("@material-ui/core/TableFooter")
private external val tableFooterModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val TableFooterComponent: RComponent<MTableFooterProps, RState> = tableFooterModule.default

interface MTableFooterProps : StyledProps {
    var component: String
}

fun RBuilder.mTableFooter(
        component: String = "tfoot",

        className: String? = null,
        handler: StyledHandler<MTableFooterProps>? = null) = createStyled(TableFooterComponent) {
    attrs.component = component
    setStyledPropsAndRunHandler(className, handler)
}
