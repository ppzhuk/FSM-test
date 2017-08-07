package ru.ppzhuk.game

data class Vector(val x: Int, val y: Int) {
    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y)

    fun distance(other: Vector): Double {
        val xdiff = x - other.x
        val ydiff = y - other.y

        return Math.sqrt(xdiff*xdiff + (ydiff*ydiff).toDouble())
    }
}