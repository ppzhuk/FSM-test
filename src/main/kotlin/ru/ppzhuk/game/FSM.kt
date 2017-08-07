package ru.ppzhuk.game

class FSM(
        var state: () -> Unit
) {
    fun update() = state()
}