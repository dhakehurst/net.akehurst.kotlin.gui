package net.akehurst.kotlin.gui.jfx

import javafx.scene.Node
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiPanel

class GuiPanelJfx : GuiPanel, GuiContainerJfxAbstract() {

    override val jfx = VBox()


}