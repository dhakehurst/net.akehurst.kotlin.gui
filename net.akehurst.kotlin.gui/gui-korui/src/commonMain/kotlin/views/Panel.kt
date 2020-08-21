package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korma.geom.Vector3D

class Panel : UIContainer() {

    val shape = Shape3D {
        face(Vector3D(0.0, +0.5, 0.0)) //top
        face(Vector3D(0.0, -0.5, 0.0))  // bottom

        face(Vector3D(+0.5, 0.0, 0.0)) //left
        face(Vector3D(-0.5, 0.0, 0.0)) // right

        face(Vector3D(0.0, 0.0, +0.5)) //front
        face(Vector3D(0.0, 0.0, -0.5))  // back
    }

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

    init {
        width = 8.0
        height = 5.0
        depth = 2.0

        this.addChild(shape)
    }
}