package com.picturecolor.client.material.expansionpanel

import com.picturecolor.client.material.StyledPropsWithCommonAttributes
import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.*
import styled.StyledHandler

@JsModule("@material-ui/core/ExpansionPanelSummary")
private external val expansionPanelSummaryModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val expansionPanelSummaryComponent: RComponent<MExpansionPanelSummaryProps, RState> = expansionPanelSummaryModule.default

interface MExpansionPanelSummaryProps : StyledPropsWithCommonAttributes {
	var expandIcon: ReactElement
	var iconButtonProps: RProps
}

fun RBuilder.mExpansionPanelSummary(expandIcon: ReactElement? = null,
                                    iconButtonProps: RProps? = null,
                                    className: String? = null,
                                    handler: StyledHandler<MExpansionPanelSummaryProps>? = null) =
		createStyled(expansionPanelSummaryComponent) {
			expandIcon?.let { attrs.expandIcon = it }
			iconButtonProps?.let { attrs.iconButtonProps = it }
			setStyledPropsAndRunHandler(className, handler)
		}
