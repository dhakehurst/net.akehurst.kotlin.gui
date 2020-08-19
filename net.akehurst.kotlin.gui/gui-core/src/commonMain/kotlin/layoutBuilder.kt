package net.akehurst.kotlin.gui.core

import net.akehurst.kotlin.gui.api.GuiLayout
import net.akehurst.kotlin.gui.api.GuiLayoutFactory
import net.akehurst.kotlin.gui.api.GuiLayoutRule

fun guiLayout(factory: GuiLayoutFactory, init: LayoutBuilder.() -> Unit): GuiLayout {
    val b = LayoutBuilder(factory)
    b.init()
    return b.build()
}

@DslMarker
annotation class GuiLayoutBuilderDsl

@GuiLayoutBuilderDsl
class LayoutBuilder(
        val factory: GuiLayoutFactory
) {
    val result = factory.createLayout()

    fun rule(selector: String, init: RuleBuilder.() -> Unit): GuiLayoutRule {
        val b = RuleBuilder(factory, result, selector)
        b.init()
        return b.build()
    }

    fun build(): GuiLayout {
        return result
    }
}

@GuiLayoutBuilderDsl
class RuleBuilder(
        val factory: GuiLayoutFactory,
        val layout: GuiLayout,
        val expression: String
) {
    val result = factory.createRule(layout,factory.createSelector(expression))

    fun declaration(property: String, value: String) {
        factory.createDeclaration(result, property, value)
    }

    fun build(): GuiLayoutRule {
        return result
    }
}