package ru.ppzhuk.ui

import java.awt.Graphics

interface GameElement {
    fun redraw(g: Graphics)
    fun update()
}