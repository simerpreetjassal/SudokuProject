package com.simerpreet.sudokuproject


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result


class TheGameBoard : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_game_board)
        val mode = intent.getStringExtra("gameMode")
        sendGet(mode)
    }


    fun cellClicked(v: View?){
        v!!.background = ContextCompat.getDrawable(applicationContext,R.drawable.clicked)
    }
    fun sendGet(gMode: String) {
        val myUrl =  "https://sugoku.herokuapp.com/board?difficulty=$gMode"
        myUrl.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    Log.d("MyData",ex.toString())
                }
                is Result.Success -> {
                    val data = result.get()
                    val indx = data.indexOf(":");
                    val onyBoard  = data.substring(indx+1,data.length-2)
                    val Borad2Array = Klaxon().parseArray<ArrayList<Int>>(onyBoard)
                    fillTheBoard(Borad2Array)

                }
            }
        }
    }
    fun fillTheBoard(my2DBoard: List<ArrayList<Int>>?){
        var i = 0
        var j = 0
        if (my2DBoard != null) {
            for(littleArray:ArrayList<Int> in my2DBoard){
                for (boardVal in littleArray){
                    val resID = resources.getIdentifier("r${i}c${j}", "id", packageName)
                   // Log.d("mydata","$resID")

                    val b = findViewById<Button>(resID)

                    if(boardVal!=0){
                      b.text = boardVal.toString();
                    }
                   j++
                }
                i++;
                j = 0;
            }
        }
    }


}
