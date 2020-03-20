package com.simerpreet.sudokuproject

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_the_game_board.*
import kotlinx.android.synthetic.main.game_board.*
import java.util.*


class TheGameBoard : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_game_board)
        
    }


    fun cellClicked(v: View?){
        v!!.background = ContextCompat.getDrawable(applicationContext,R.drawable.clicked)
    }


}
