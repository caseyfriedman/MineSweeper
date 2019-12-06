package com.example.minesweeper.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.minesweeper.MainActivity
import com.example.minesweeper.R
import com.example.minesweeper.minesweeperModel.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var paintBackground: Paint = Paint()
    var paintLine: Paint = Paint()
    var paintText: Paint = Paint()
    var paintLineX: Paint = Paint()
    var flagClick = false
    var paintTextFlag: Paint = Paint()
    var gameOver = false

    init {
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 7f
        paintText.color = Color.WHITE
        paintText.textSize = 100f
        paintLineX.color = Color.RED
        paintLineX.strokeWidth = 15f
        paintTextFlag.color = Color.YELLOW
        paintTextFlag.textSize = 125f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)


        drawField(canvas)

        drawBoard(canvas)

    }

    private fun drawBoard(canvas: Canvas?) {
        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines

        for (i in 1..5) {
            canvas?.drawLine(
                0f, (i * height / 5).toFloat(), width.toFloat(), (i * height / 5).toFloat(),
                paintLine
            )
        }
        for (i in 1..5) {
            canvas?.drawLine(
                (i * width / 5).toFloat(), 0f, (i * width / 5).toFloat(), height.toFloat(),
                paintLine
            )
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (!gameOver) {
            if (event?.action == MotionEvent.ACTION_DOWN) {


                val tX = event.x.toInt() / (width / 5)
                val tY = event.y.toInt() / (height / 5)

                if (tX < 5 && tY < 5 && MinesweeperModel.getFieldContent(
                        tX,
                        tY
                    ).type == MinesweeperModel.BOMB && !flagClick
                ) {
                    MinesweeperModel.getFieldContent(tX, tY).wasClicked = true

                    invalidate()


                } else if (tX < 5 && tY < 5 && MinesweeperModel.getFieldContent(
                        tX,
                        tY
                    ).type == MinesweeperModel.EMPTY && !flagClick
                ) {
                    MinesweeperModel.getFieldContent(tX, tY).wasClicked = true
                    invalidate()
                } else if (tX < 5 && tY < 5 && !MinesweeperModel.getFieldContent(
                        tX,
                        tY
                    ).wasClicked && flagClick
                ) {

                    MinesweeperModel.getFieldContent(tX, tY).isFlagged =
                        !MinesweeperModel.getFieldContent(tX, tY).isFlagged
                    invalidate()
                }


            }
            checkGameOver(3)
        }
        return true
    }

    private fun drawField(canvas: Canvas?) {

        for (i in 0..4)
            for (j in 0..4)
                if (MinesweeperModel.getFieldContent(i, j).wasClicked
                    && MinesweeperModel.getFieldContent(i, j).type == MinesweeperModel.BOMB
                ) {
                    drawBomb(canvas, i, j)
                } else if (MinesweeperModel.getFieldContent(i, j).wasClicked
                    && MinesweeperModel.getFieldContent(i, j).type == MinesweeperModel.EMPTY
                ) {
                    drawNumber(canvas, i, j)
                } else if (MinesweeperModel.getFieldContent(i, j).isFlagged) {
                    drawFlag(canvas, i, j)
                }
    }


    private fun checkGameOver(bombs: Int) {

        var countCorrectFlags = 0
        for (i in 0..4)
            for (j in 0..4) {
                if (MinesweeperModel.getFieldContent(i, j).type == MinesweeperModel.BOMB &&
                    MinesweeperModel.getFieldContent(i, j).wasClicked

                ) {
                    (context as MainActivity).showText(context.getString(R.string.you_lost))
                    gameOver = true
                }
                if (MinesweeperModel.getFieldContent(
                        i,
                        j
                    ).type == MinesweeperModel.BOMB && MinesweeperModel.getFieldContent(
                        i,
                        j
                    ).isFlagged
                )
                    countCorrectFlags++
            }
        if (countCorrectFlags >= bombs) {
            (context as MainActivity).showText(context.getString(R.string.you_won))
            gameOver = true
        }


    }


    fun clearGame() {
        MinesweeperModel.resetModel()
        gameOver = false
        invalidate()
    }

    private fun drawBomb(canvas: Canvas?, i: Int, j: Int) {
        canvas?.drawLine(
            (i * width / 5).toFloat(), (j * height / 5).toFloat(),
            ((i + 1) * width / 5).toFloat(),
            ((j + 1) * height / 5).toFloat(), paintLineX
        )

        canvas?.drawLine(
            ((i + 1) * width / 5).toFloat(), (j * height / 5).toFloat(),
            (i * width / 5).toFloat(), ((j + 1) * height / 5).toFloat(), paintLineX
        )
    }


    private fun drawNumber(canvas: Canvas?, i: Int, j: Int) {
        canvas?.drawText(
            MinesweeperModel.getFieldContent(i, j).minesAround.toString(),
            ((i + .33) * width / 5).toFloat(), ((j + .75) * height / 5).toFloat(), paintText
        )

    }

    private fun drawFlag(canvas: Canvas?, i: Int, j: Int) {
        canvas?.drawText(
            context.getString(R.string.flag),
            ((i + .33) * width / 5).toFloat(),
            ((j + .75) * height / 5).toFloat(),
            paintTextFlag
        )
    }
}


