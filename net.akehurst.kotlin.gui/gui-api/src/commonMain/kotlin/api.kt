package net.akehurst.kotlin.gui.api

interface GuiFactory {
    fun createWindow(): GuiWindow
    fun createText(container: GuiContainer): GuiText

}

interface GuiWindow : GuiContainer {
    var title: String

    fun show()
}

interface GuiControl {

}

interface GuiContainer {
    val content: MutableList<GuiControl>
}

interface GuiText : GuiControl {
    var text: String
}