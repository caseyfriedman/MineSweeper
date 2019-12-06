package com.example.minesweeper.minesweeperModel

import kotlin.random.Random


object MinesweeperModel {


    val EMPTY: Int = 0
    val FLAG: Int = 1
    val BOMB: Int = 2


    var fieldMatrix: Array<Array<Field>> = emptyArray()


    init {
        createBoard()
    }


    fun fillBoard() {
        fieldMatrix = Array(5) {
            Array(5) {
                Field(0, 0, false, false)
            }
        }
    }

    fun getFieldContent(x: Int, y: Int) = fieldMatrix[x][y]


    fun createBoard() {
        fillBoard()
        for (i in 0..2) {
            var randi = Random.nextInt(4)
            var randj = Random.nextInt(4)
            while (fieldMatrix[randi][randj].type == BOMB) {
                randi = Random.nextInt(4)
                randj = Random.nextInt(4)
            }
            fieldMatrix[randi][randj].type = BOMB
            calcMines(randi, randj)
        }


    }

    fun resetModel() {
        for (i in 0..4) {
            for (j in 0..4) {
                fieldMatrix[i][j].type = 0
                fieldMatrix[i][j].isFlagged = false
                fieldMatrix[i][j].minesAround = 0
                fieldMatrix[i][j].wasClicked = false
            }
        }
        createBoard()
    }


    fun calcMines(randi: Int, randj: Int) {


        if (randi == 0 && randj == 0) {
            fieldMatrix[randi][randj + 1].minesAround++
            fieldMatrix[randi + 1][randj].minesAround++
            fieldMatrix[randi + 1][randj + 1].minesAround++
        } else if (randi == 0 && randj == 4) {
            fieldMatrix[randi][randj - 1].minesAround++
            fieldMatrix[randi + 1][randj - 1].minesAround++
            fieldMatrix[randi + 1][randj].minesAround++
        } else if (randi == 0) {
            fieldMatrix[randi + 1][randj].minesAround++
            fieldMatrix[randi + 1][randj + 1].minesAround++
            fieldMatrix[randi + 1][randj - 1].minesAround++
            fieldMatrix[randi][randj - 1].minesAround++
            fieldMatrix[randi][randj + 1].minesAround++

        } else if (randi == 4 && randj == 0) {
            fieldMatrix[randi - 1][randj].minesAround++
            fieldMatrix[randi][randj + 1].minesAround++
            fieldMatrix[randi - 1][randj + 1].minesAround++
        } else if (randi == 4 && randj == 4) {
            fieldMatrix[randi - 1][randj - 1].minesAround++
            fieldMatrix[randi - 1][randj].minesAround++
            fieldMatrix[randi][randj - 1].minesAround++
        } else if (randj == 0) {
            fieldMatrix[randi + 1][randj].minesAround++
            fieldMatrix[randi + 1][randj + 1].minesAround++
            fieldMatrix[randi - 1][randj].minesAround++
            fieldMatrix[randi - 1][randj + 1].minesAround++
            fieldMatrix[randi][randj + 1].minesAround++

        } else if (randj == 4) {
            fieldMatrix[randi - 1][randj].minesAround++
            fieldMatrix[randi + 1][randj].minesAround++
            fieldMatrix[randi - 1][randj - 1].minesAround++
            fieldMatrix[randi][randj - 1].minesAround++
            fieldMatrix[randi + 1][randj - 1].minesAround++
        } else {
            fieldMatrix[randi + 1][randj].minesAround++
            fieldMatrix[randi + 1][randj + 1].minesAround++
            fieldMatrix[randi + 1][randj - 1].minesAround++
            fieldMatrix[randi - 1][randj].minesAround++
            fieldMatrix[randi - 1][randj + 1].minesAround++
            fieldMatrix[randi - 1][randj - 1].minesAround++
            fieldMatrix[randi][randj - 1].minesAround++
            fieldMatrix[randi][randj + 1].minesAround++
        }


    }

}