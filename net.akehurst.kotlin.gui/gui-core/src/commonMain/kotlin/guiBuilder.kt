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
        val factory: GuiFactory,
        val title: String
) {

    private val content = mutableListOf<ControlBuilder>()

    fun text(text: String, init:TextBuilder.()->Unit = {}) {
        val b = TextBuilder(factory,text)
        b.init()
        content.add(b)
    }


    fun build(): GuiWindow {
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
    abstract fun build(container: GuiContainer)
}

@GuiBuilderDsl
class TextBuilder(
        factory: GuiFactory,
        val text: String
) : ControlBuilder(factory) {

    override fun build(container: GuiContainer) {
        val result = factory.createText(container)
        result.text = text
    }

}