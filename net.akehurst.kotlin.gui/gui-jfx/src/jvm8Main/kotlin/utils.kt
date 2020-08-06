package net.akehurst.kotlin.gui.jfx

import javafx.application.Platform
import java.util.concurrent.FutureTask

fun <T> runOnJfxThreadAndWait(block: () -> T): T {
    val task = FutureTask(block)
    Platform.runLater(task)
    return task.get() as T
}