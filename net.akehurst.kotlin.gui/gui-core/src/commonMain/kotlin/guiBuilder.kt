package net.akehurst.kotlin.gui.core

import net.akehurst.kotlin.gui.api.*

fun guiWindow(factory: GuiFactory, title: String, width: Double, height: Double, init: WindowBuilder.() -> Unit): GuiWindow {
    val b = WindowBuilder(factory, title, width, height)
    b.init()
    return b.build()
}

@DslMarker
annotation class GuiBuilderDsl

@GuiBuilderDsl
class WindowBuilder(
        val factory: GuiFactory,
        val title: String,
        val width: Double,
        val height: Double
) {

    val control = factory.createWindow()

    fun cube(init:GuiCube.()->Unit)  {
        val el = this.factory.createCube(this.control)
        el.init()
    }

    fun panel(init: PanelBuilder.() -> Unit = {}): GuiPanel {
        val b = PanelBuilder(factory, this.control)
        b.init()
        return b.build()
    }

    fun build(): GuiWindow {
        this.control.width = width
        this.control.height = height
        return this.control
    }
}

@GuiBuilderDsl
abstract class ControlBuilder(
        val factory: GuiFactory,
        val container: GuiContainer,
        var x:Double = 0.0,
        var y:Double = 0.0,
        var z:Double = 0.0
) {
    abstract val control: GuiControl

    abstract fun build(): GuiControl
}

@GuiBuilderDsl
abstract class ContainerBuilder(
        factory: GuiFactory,
        container: GuiContainer
) : ControlBuilder(factory, container) {

    abstract override val control: GuiContainer

    fun button(label: String, init: ButtonBuilder.() -> Unit = {}): GuiButton {
        val b = ButtonBuilder(factory, this.control, label)
        b.init()
        return b.build()
    }

    fun dialog(title: String, init: DialogBuilder.() -> Unit = {}): GuiDialog {
        val b = DialogBuilder(factory, this.control, title)
        b.init()
        return b.build()
    }

    fun progressBar(init: ProgressBarBuilder.() -> Unit = {}): GuiProgressBar {
        val b = ProgressBarBuilder(factory, this.control)
        b.init()
        return b.build()
    }

    fun text(text: String, init: TextBuilder.() -> Unit = {}): GuiText {
        val b = TextBuilder(factory, this.control, text)
        b.init()
        return b.build()
    }

    fun textEditor(text: String, init: TextEditorBuilder.() -> Unit = {}): GuiTextEditor {
        val b = TextEditorBuilder(factory, this.control, text)
        b.init()
        return b.build()
    }

    abstract override fun build(): GuiContainer
}

@GuiBuilderDsl
class ButtonBuilder(
        factory: GuiFactory,
        container: GuiContainer,
        val label: String,
        val width: Double = 50.0,
        val height: Double = 30.0
) : ControlBuilder(factory, container) {

    override val control = factory.createButton(container)

    var onPressed = {}
    var onReleased = {}

    fun onPressed(action: () -> Unit) {
        onPressed = action
    }

    fun onReleased(action: () -> Unit) {
        onReleased = action
    }

    override fun build(): GuiButton {
        control.label = label
        control.width = width
        control.height = height
        control.onPressed = onPressed
        control.onReleased = onReleased
        return control
    }

}

@GuiBuilderDsl
class DialogBuilder(
        factory: GuiFactory,
        container: GuiContainer,
        val title: String,
        val width: Double = 200.0,
        val height: Double = 150.0
) : ControlBuilder(factory, container) {

    override val control = factory.createDialog(container)

    fun panel(init: PanelBuilder.() -> Unit = {}): GuiPanel {
        val b = PanelBuilder(factory, this.control)
        b.init()
        return b.build()
    }

    override fun build(): GuiDialog {
        this.control.title = title
        control.width = width
        control.height = height
        return this.control
    }
}

@GuiBuilderDsl
class TextBuilder(
        factory: GuiFactory,
        container: GuiContainer,
        val text: String
) : ControlBuilder(factory, container) {

    override val control = factory.createText(container)

    override fun build(): GuiText {
        control.text = text
        control.x = x
        control.y = y
        control.z = z
        return control
    }

}

@GuiBuilderDsl
class TextEditorBuilder(
        factory: GuiFactory,
        container: GuiContainer,
        val text: String,
        val width: Double = 50.0,
        val height: Double = 30.0
) : ControlBuilder(factory, container) {

    override val control = factory.createTextEditor(container)

    override fun build(): GuiTextEditor {
        control.text = text
        control.width = width
        control.height = height
        return control
    }

}

@GuiBuilderDsl
class PanelBuilder(
        factory: GuiFactory,
        container: GuiContainer,
        var width: Double = 1.0,
        var height: Double = 1.0,
        var depth: Double =1.0
) : ContainerBuilder(factory, container) {

    override val control = factory.createPanel(container)

    override fun build(): GuiPanel {
        control.width = width
        control.height = height
        control.depth = depth
        return control
    }

}

@GuiBuilderDsl
class ProgressBarBuilder(
        factory: GuiFactory,
        container: GuiContainer,
        val width: Double = 50.0,
        val height: Double = 30.0
) : ControlBuilder(factory, container) {

    override val control = factory.createProgressBar(container)

    override fun build(): GuiProgressBar {
        control.width = width
        control.height = height
        return control
    }

}
