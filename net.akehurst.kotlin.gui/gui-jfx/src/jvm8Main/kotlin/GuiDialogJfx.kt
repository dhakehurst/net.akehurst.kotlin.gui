package net.akehurst.kotlin.gui.jfx

import javafx.scene.Scene
import javafx.scene.control.Dialog
import javafx.scene.layout.FlowPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiDialog

class GuiDialogJfx : GuiDialog, GuiContainerJfxAbstract() {

    private lateinit var stage: Stage
    private lateinit var scene: Scene
    override lateinit var jfx: VBox

    override var title: String
        get() = stage.title
        set(value) {
            stage.title = value
        }

    init {
        runOnJfxThreadAndWait {
            stage = Stage()
            jfx = VBox()  //TODO: use Pane
            scene = Scene(jfx)
            stage.scene = scene
            stage.sizeToScene()
        }
    }

    // --- GuiDialog ---

    override fun show(modal: Boolean) {
        when (modal) {
            true -> stage.showAndWait()
            false -> stage.show()
        }
    }

    override fun close() {
        stage.close()
    }

}