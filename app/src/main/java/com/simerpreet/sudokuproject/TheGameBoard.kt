package com.simerpreet.sudokuproject


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.core.content.ContextCompat
import com.beust.klaxon.Klaxon

import com.github.kittinunf.fuel.httpGet

import com.github.kittinunf.result.Result;


class TheGameBoard : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_game_board)
        //sendGet()

        val board =  "board: [[0,0,1,0,0,0,0,0,0],[2,0,0,0,0,0,0,7,0],[0,7,0,0,0,0,0,0,0]]"
        val indx = board.indexOf(" ");
        val i  = board.substring(indx+1)

        val my = Klaxon().parseArray<Array<Int>>(i)
        Log.d("MyData",my.toString());
    }


    fun cellClicked(v: View?){
        v!!.background = ContextCompat.getDrawable(applicationContext,R.drawable.clicked)
    }
    fun sendGet() {
        "https://sugoku.herokuapp.com/board?difficulty=easy".httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    //Log.d("MyData",ex.toString())
                }
                is Result.Success -> {
                    val data = result.get()

                    //Log.d("MyData",myBoard?.get(0)!!.board.toString())
                }
            }
        }
    }


}
