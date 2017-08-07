package ru.ppzhuk.ui

import java.awt.Graphics
import javax.swing.JPanel

class GamePanel(private val game: GameElement) : JPanel() {

    override fun paint(g: Graphics) {
        super.paint(g)

        game.update()
        game.redraw(g)
    }
}