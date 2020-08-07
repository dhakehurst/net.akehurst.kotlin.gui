package net.akehurst.kotlin.gui.api

interface GuiFactory {
    fun createWindow(): GuiWindow
    fun createDialog(container: GuiContainer) : GuiDialog

    fun createButton(container: GuiContainer): GuiButton
    fun createText(container: GuiContainer): GuiText
    fun createTextEditor(container: GuiContainer): GuiTextEditor
    fun createProgressBar(container: GuiContainer): GuiProgressBar


}

interface GuiWindow : GuiContainer {

    var title: String

    fun show()
}

interface GuiControl {

}

interface GuiContainer : GuiControl {

    val content: List<GuiControl>

    fun addContent(element: GuiControl)
    fun removeContent(element: GuiControl)
}

interface GuiDialog : GuiContainer {

    var title: String

    fun show(modal: Boolean = true)
}

interface GuiButton : GuiControl {
    var label : String
    var onPress : ()->Unit
}

interface GuiText : GuiControl {
    var text: String
}

interface GuiTextEditor : GuiControl {
    var text: String
}

interface GuiProgressBar : GuiControl {
    /**
     * a value between 0.0 and 1.0.
     * 1.0 indicates 100%
     */
    var progress: Double
}