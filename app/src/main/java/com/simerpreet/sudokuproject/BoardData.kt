package com.simerpreet.sudokuproject

class BoardData (val board: Array<Array<Int>>) {
    override fun toString(): String {
        return "BoardData(board=${board.contentToString()})"
    }
}