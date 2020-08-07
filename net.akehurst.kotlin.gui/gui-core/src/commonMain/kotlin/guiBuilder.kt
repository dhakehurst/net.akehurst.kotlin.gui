package net.akehurst.kotlin.gui.core

import net.akehurst.kotlin.gui.api.*

fun guiWindow(factory: GuiFactory, title: String, init: WindowBuilder.() -> Unit): GuiWindow {
    val b = WindowBuilder(factory, title)
    b.init()
    return b.build()
}

@DslMarker
annotation class GuiBuilderDsl

@GuiBuilderDsl
class WindowBuilder(
        factory: GuiFactory,
        val title: String
) : ContainerBuilder(factory) {

    override fun build(container: GuiContainer) : GuiWindow {
        error("Call build with no argument to build GuiWindow")
    }

    fun build() : GuiWindow {
        val result = factory.createWindow()
        result.title = title
        content.forEach {
            val control = it.build(result)
        }
        return result
    }
}

@GuiBuilderDsl
abstract class ControlBuilder(
        val factory: GuiFactory
) {
    abstract fun build(container: GuiContainer) : GuiControl
}

@GuiBuilderDsl
abstract class ContainerBuilder(
        factory: GuiFactory
) : ControlBuilder(factory) {

    val content = mutableListOf<ControlBuilder>()

    fun button(label: String, init: ButtonBuilder.() -> Unit = {}) {
        val b = ButtonBuilder(factory, label)
        b.init()
        content.add(b)
    }

    fun dialog(title: String, init: DialogBuilder.() -> Unit = {}) {
        val b = DialogBuilder(factory, title)
        b.init()
        content.add(b)
    }

    fun progressBar(init: ProgressBarBuilder.() -> Unit = {}) {
        val b = ProgressBarBuilder(factory)
        b.init()
        content.add(b)
    }

    fun text(text: String, init: TextBuilder.() -> Unit = {}) {
        val b = TextBuilder(factory, text)
        b.init()
        content.add(b)
    }

    fun textEditor(text: String, init: TextEditorBuilder.() -> Unit = {}) {
        val b = TextEditorBuilder(factory, text)
        b.init()
        content.add(b)
    }

    abstract override fun build(container: GuiContainer) : GuiContainer
}

@GuiBuilderDsl
class ButtonBuilder(
        factory: GuiFactory,
        val label: String
) : ControlBuilder(factory) {

    var onPress = {}

    fun onPress(action: () -> Unit) {
        onPress = action
    }

    override fun build(container: GuiContainer) : GuiButton {
        val result = factory.createButton(container)
        result.label = label
        result.onPress = onPress
        return result
    }

}

@GuiBuilderDsl
class DialogBuilder(
        factory: GuiFactory,
        val title: String
) : ContainerBuilder(factory) {

    override fun build(container: GuiContainer) : GuiDialog {
        val result = factory.createDialog(container)
        result.title = title
        content.forEach {
            val control = it.build(result)
        }
        return result
    }

}

@GuiBuilderDsl
class TextBuilder(
        factory: GuiFactory,
        val text: String
) : ControlBuilder(factory) {

    override fun build(container: GuiContainer) : GuiText {
        val result = factory.createText(container)
        result.text = text
        return result
    }

}

@GuiBuilderDsl
class TextEditorBuilder(
        factory: GuiFactory,
        val text: String
) : ControlBuilder(factory) {

    override fun build(container: GuiContainer) : GuiTextEditor {
        val result = factory.createTextEditor(container)
        result.text = text
        return result
    }

}

@GuiBuilderDsl
class ProgressBarBuilder(
        factory: GuiFactory
) : ControlBuilder(factory) {

    override fun build(container: GuiContainer) : GuiProgressBar {
        val result = factory.createProgressBar(container)
        return result
    }

}
