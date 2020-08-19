package net.akehurst.kotlin.gui.core

import net.akehurst.kotlin.gui.api.*

class GuiLayoutFactorySimple : GuiLayoutFactory {
    override fun createLayout(): GuiLayout {
        return GuiLayoutSimple()
    }

    override fun createRule(layout: GuiLayout, selector: GuiLayoutSelector): GuiLayoutRule {
        return GuiLayoutRuleSimple(layout, selector)
    }

    override fun createSelector(expression: String): GuiLayoutSelector {
        return GuiLayoutSelectorSimple(expression) //TODO: parse complex selector expressions
    }

    override fun createDeclaration(rule: GuiLayoutRule, property: String, value: String) : GuiLayoutDeclaration {
        return GuiLayoutDeclarationSimple(rule, property, value)
    }

}

class GuiLayoutSimple() : GuiLayout {
    override val rules: List<GuiLayoutRule> = mutableListOf()
}

class GuiLayoutRuleSimple(
        override val layout: GuiLayout,
        override val selector: GuiLayoutSelector
) : GuiLayoutRule {
    override val declarations: List<GuiLayoutDeclaration> = mutableListOf()
}

class GuiLayoutSelectorSimple(
        val expression: String
) : GuiLayoutSelector

class GuiLayoutDeclarationSimple(
        override val rule: GuiLayoutRule,
        override var property: String,
        override var value: String
) : GuiLayoutDeclaration