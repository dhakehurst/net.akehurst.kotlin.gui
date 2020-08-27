package net.akehurst.kotlin.gui.api

interface GuiFactory {
    fun createWindow(): GuiWindow
    fun createDialog(container: GuiContainer): GuiDialog

    fun createButton(container: GuiContainer): GuiButton
    fun createText(container: GuiContainer): GuiText
    fun createTextEditor(container: GuiContainer): GuiTextEditor
    fun createPanel(container: GuiContainer): GuiPanel
    fun createProgressBar(container: GuiContainer): GuiProgressBar

    fun createCube(container: GuiContainer) : GuiCube

}

interface GuiWindow : GuiContainer {

    var title: String

    fun show()

}

interface GuiCube {
    var x:Double
    var y:Double
    var z:Double
}

interface GuiControl {
    var width: Double
    var height: Double
    var depth: Double

    fun applyLayout(guiLayout: GuiLayout)
}

interface GuiContainer : GuiControl {

    val content: List<GuiControl>

    fun addContent(element: GuiControl)
    fun removeContent(element: GuiControl)
}

interface GuiDialog : GuiContainer {

    var title: String

    fun show(modal: Boolean = true)
    fun close()
}

interface GuiButton : GuiControl {
    var label: String
    var onPressed: () -> Unit
    var onReleased: () -> Unit
}

interface GuiText : GuiControl {
    var text: String
}

interface GuiTextEditor : GuiControl {
    var text: String
}

interface GuiPanel : GuiContainer {

}

interface GuiProgressBar : GuiControl {
    /**
     * a value between 0.0 and 1.0.
     * 1.0 indicates 100%
     */
    var progress: Double
}