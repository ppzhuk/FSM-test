package ru.ppzhuk.game

import ru.ppzhuk.ui.GameElement
import java.awt.Graphics
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Mouse(x: Double, y: Double) : GameElement {
    private var position: Vector = Vector(x, y)
    private var desiredVelocity: Vector = Vector()
    private var steeringForce: Vector = Vector()
    private var velocity: Vector = Vector()
    private val brain: FSM = FSM(this::findPoint3)

    private val img: BufferedImage = ImageIO.read(
            ClassLoader.getSystemResourceAsStream("mouse.png")
    )

    companion object {
        @JvmStatic private val MAX_VELOCITY = 3.0
        @JvmStatic private val MAX_FORCE = 0.75
        @JvmStatic private val POINT_MIN_DISTANCE = 10.0
        @JvmStatic private val CURSOR_MIN_DISTANCE = 100.0
    }

    constructor() : this(300.0, 300.0)

    private fun findPoint1(): Unit = findPoint(Game.point1, this::findPoint2)
    private fun findPoint2(): Unit = findPoint(Game.point2, this::findPoint3)
    private fun findPoint3(): Unit = findPoint(Game.point3, this::findPoint1)

    private fun findPoint(point: Vector, secondState: FSMState) {
        evaluateVelocityVectors(point)

        if (position.distance(point) < POINT_MIN_DISTANCE) {
            brain.popState()
            brain.pushState(secondState)
        }

        if (position.distance(Game.cursor.position) < CURSOR_MIN_DISTANCE) {
            brain.pushState(this::runAway)
        }
    }

    private fun runAway() {
        evaluateVelocityVectors(Game.cursor.position, isRunningAway = true, maxVelocity = 10.0)
        if (position.distance(Game.cursor.position) > CURSOR_MIN_DISTANCE) {
            brain.popState()
        }
    }

    private fun evaluateVelocityVectors(
            point: Vector,
            isRunningAway: Boolean = false,
            maxVelocity: Double = MAX_VELOCITY,
            maxForce: Double = MAX_FORCE
    ) {
        val velocityVector = if (isRunningAway) {
            Vector(position.x - point.x, position.y - point.y)
        } else {
            Vector(point.x - position.x, point.y - position.y)
        }
        desiredVelocity = Vector.normalize(velocityVector, maxVelocity)
        steeringForce = Vector.normalize(desiredVelocity - velocity, maxForce)

        val diff = desiredVelocity - velocity
        velocity = if (Math.abs(diff.x) < 0.5 && Math.abs(diff.y) < 0.5) {
            desiredVelocity
        } else {
            Vector.normalize(velocity + steeringForce, MAX_VELOCITY)
        }
    }

    override fun redraw(g: Graphics) {
        val imgCenterX = img.getWidth(null) / 2.0
        val imgCenterY = img.getHeight(null) / 2.0
        val angle = getAngle()
        val rotateOperation = AffineTransformOp(
                AffineTransform.getRotateInstance(angle, imgCenterX, imgCenterY),
                AffineTransformOp.TYPE_BILINEAR
        )
        val x = this.position.x - imgCenterX
        val y = this.position.y - imgCenterY
        g.drawImage(rotateOperation.filter(img, null), x.toInt(), y.toInt(), null)
    }

    private fun getAngle(): Double {
        val vec = velocity
        val imgVec = Vector(0.0, 10.0)

        return Math.atan2(imgVec.x * vec.y - imgVec.y * vec.x, imgVec.x * vec.x + imgVec.y * vec.y)
    }

    override fun update() {
        brain.update()
        move()
    }

    fun move() {
        position += velocity
    }
}