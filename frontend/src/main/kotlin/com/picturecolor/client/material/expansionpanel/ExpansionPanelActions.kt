package com.picturecolor.client.material.expansionpanel

import com.picturecolor.client.material.StyledPropsWithCommonAttributes
import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler

@JsModule("@material-ui/core/ExpansionPanelActions")
private external val expansionPanelActionsModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val expansionPanelActionsComponent: RComponent<MExpansionPanelActionsProps, RState> = expansionPanelActionsModule.default

interface MExpansionPanelActionsProps : StyledPropsWithCommonAttributes {
	var disableSpacing: Boolean
}

fun RBuilder.mExpansionPanelActions(disableSpacing: Boolean? = null,
                                    className: String? = null,
                                    handler: StyledHandler<MExpansionPanelActionsProps>? = null) =
		createStyled(expansionPanelActionsComponent) {
			disableSpacing?.let { attrs.disableSpacing = it }
			setStyledPropsAndRunHandler(className, handler)
		}
