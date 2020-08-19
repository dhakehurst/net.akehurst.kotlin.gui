package net.akehurst.kotlin.gui.jfx

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiWindow

class GuiWindowJfx : GuiContainerJfxAbstract(), GuiWindow {

    private lateinit var stage: Stage
    override lateinit var jfx: FlowPane
    private lateinit var scene: Scene

    init {
        runOnJfxThreadAndWait {
            stage = Stage()
            jfx = FlowPane()  //TODO: use Pane
            scene = Scene(jfx)
            stage.scene = scene
            stage.sizeToScene()
        }
    }

    override var title: String
        get() = stage.title
        set(value) {
            stage.title = value
        }

    override fun show() {
        Platform.runLater(Runnable {
            try {

                stage.showAndWait()

//					gc = canvas.getGraphicsContext2D();
//					gc.setStroke(Color.BLACK);
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }





    // --- GuiControl ---

    override var width: Double
        get() = jfx.width
        set(value) {jfx.prefWidth = value}
    override var height: Double
        get() = jfx.height
        set(value) {jfx.prefHeight = value}

}