package com.example.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.minesweeper.view.MinesweeperView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btnClear.setOnClickListener {
            mineView.clearGame()
        }

        btnFlag.setOnClickListener {
            mineView.flagClick = !mineView.flagClick

        }

    }


    fun showText(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()

    }
}
