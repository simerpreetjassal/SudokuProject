package com.simerpreet.sudokuproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game_options.*
class GameOptions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_options)
        val gameBoard= Intent(this,TheGameBoard::class.java)
        easyBtn.setOnClickListener{
            gameBoard.putExtra("gameMdode","easy")
            startActivity(gameBoard)
        }
        mediumBtn.setOnClickListener{
            gameBoard.putExtra("gameMdode","medium")
            startActivity(gameBoard)
        }
        hardbtn.setOnClickListener{
            gameBoard.putExtra("gameMdode","hard")
            startActivity(gameBoard)
        }
    }


}
