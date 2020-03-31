package com.simerpreet.sudokuproject


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_the_game_board.*


class TheGameBoard : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_game_board)
        val mode = intent.getStringExtra("gameMode")
        getBoard(mode)
        submitSln.setOnClickListener{
            val myIntent= Intent(this,subm_solution_page::class.java)
            startActivity(myIntent)
        }
        var userName1 = intent.getStringExtra("USER_NAME")
        if(userName1!=null){
            userName.setText("Player Name: ${userName1}")

        }

        if(intent.getStringExtra("LGO")!=null) {
            logout.setText("Logout")
            logout.visibility = View.VISIBLE
        }
        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this,"Logout Successfully", Toast.LENGTH_SHORT)
            logout.visibility = View.INVISIBLE

        }
    }


    fun cellClicked(v: View?){
        v!!.background = ContextCompat.getDrawable(applicationContext,R.drawable.clicked)
    }
    fun getBoard(gMode: String) {
        val myUrl =  "https://sugoku.herokuapp.com/board?difficulty=$gMode"
        myUrl.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
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
