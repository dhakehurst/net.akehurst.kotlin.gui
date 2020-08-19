package net.akehurst.kotlin.gui.api

interface GuiLayoutFactory {

    fun createLayout(): GuiLayout
    fun createRule(layout: GuiLayout, selector: GuiLayoutSelector): GuiLayoutRule
    fun createSelector(expression: String): GuiLayoutSelector
    fun createDeclaration(rule: GuiLayoutRule, property: String, value: String): GuiLayoutDeclaration
}

interface GuiLayout {

    val rules: List<GuiLayoutRule>

}

interface GuiLayoutRule {
    val layout: GuiLayout
    val selector: GuiLayoutSelector
    val declarations: List<GuiLayoutDeclaration>
}

interface GuiLayoutSelector

interface GuiLayoutDeclaration {
    val rule: GuiLayoutRule
    var property: String
    var value: String
}