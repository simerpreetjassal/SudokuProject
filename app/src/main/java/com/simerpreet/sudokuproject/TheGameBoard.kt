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


class TheGameBoard : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_game_board)
        myGameBoard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val i = v!!.id

        val j = findViewById<View>(i)
        Log.d("Button",i.toString())
        Log.d("Button2",R.id.myGameBoard.toString())
        if (i === R.id.r0c0){
            Log.d("Button3","XX")
        }
    }

    fun cellClicked(v: View?){
        v!!.background = ContextCompat.getDrawable(applicationContext,R.drawable.clicked)
    }


}
