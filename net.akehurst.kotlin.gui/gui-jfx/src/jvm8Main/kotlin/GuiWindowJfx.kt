package net.akehurst.kotlin.gui.jfx

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiWindow

class GuiWindowJfx : GuiWindow {

    private lateinit var stage: Stage
    private lateinit var root: FlowPane
    private lateinit var scene: Scene

    init {
        runOnJfxThreadAndWait {
            stage = Stage()
            root = FlowPane()
            scene = Scene(root)
        }
    }

    override var title: String
        get() = stage.title
        set(value) {
            stage.title = value
        }

    override val content: MutableList<GuiControl> = mutableListOf()

    override fun addContent(element: GuiControl) {
        when (element) {
            is GuiControlJfx -> {
                root.children.add(element.jfx)
                content.add(element)
            }
            else -> error("$element is not a GuiControlJfx")
        }

    }

    override fun removeContent(element: GuiControl) {
        when (element) {
            is GuiControlJfx -> {
                root.children.remove(element.jfx)
                content.remove(element)
            }
            else -> error("$element is not a GuiControlJfx")
        }

    }

    override fun show() {
        Platform.runLater(Runnable {
            try {
                stage.setScene(scene)
                stage.sizeToScene()
                stage.showAndWait()

//					gc = canvas.getGraphicsContext2D();
//					gc.setStroke(Color.BLACK);
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

}