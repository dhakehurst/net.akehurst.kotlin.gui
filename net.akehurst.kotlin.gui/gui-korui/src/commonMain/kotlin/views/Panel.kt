package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge3d.shape3D
import com.soywiz.korma.geom.Vector3D

class Panel : UIContainer() {

    override var width: Double= 1.0
    set(value) {
        field = value
        shape.width = value
    }
    override var height: Double= 1.0
        set(value) {
            field = value
            shape.height = value
        }
    override var depth: Double= 1.0
        set(value) {
            field = value
            shape.depth = value
        }

    val shape = shape3D(width, height, depth) {
        cube()
    }

}