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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_the_game_board.*


class TheGameBoard : AppCompatActivity(){

    private var slelectedCell: Button? = null
    var gameLevel : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_game_board)
        val mode = intent.getStringExtra("gameMode")
        var number = (0..2).random()
        gameLevel = "${mode}${number}"
        getBoard(gameLevel!!,"SudokuPuzzles")
        submitSln.setOnClickListener{
            val myIntent= Intent(this,subm_solution_page::class.java)
            startActivity(myIntent)
        }
        var userName1 = intent.getStringExtra("USER_NAME")
        if(userName1!=null){
            playerName.setText("Player Name: ${userName1}")

        }

        if(intent.getStringExtra("LGO")!=null) {
            logout.setText("Logout")
            logout.visibility = View.VISIBLE
        }
        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this,"Logout Successfully", Toast.LENGTH_SHORT).show()
            logout.visibility = View.INVISIBLE
            playerName.setText("Player Name : UnKnown")

        }
        getTheSolution.setOnClickListener{
            getBoard(gameLevel!!,"SudokuSolution")
        }
    }


    fun cellClicked(v: View?){
        v!!.background = ContextCompat.getDrawable(applicationContext,R.drawable.clicked)
        slelectedCell = v as Button
    }
    fun userInputForBoard(v: View?){
        var btn = v as Button
        var text = btn.text.toString()
        if(slelectedCell!=null) {
            if (text == "1") {
                slelectedCell!!.text = "1"
            } else if (text == "2") {
                slelectedCell!!.text = "2"
            } else if (text == "3") {
                slelectedCell!!.text = "3"
            } else if (text == "4") {
                slelectedCell!!.text = "4"
            } else if (text == "5") {
                slelectedCell!!.text = "5"
            } else if (text == "6") {
                slelectedCell!!.text = "6"
            } else if (text == "7") {
                slelectedCell!!.text = "7"
            } else if (text == "8") {
                slelectedCell!!.text = "8"
            } else if (text == "9") {
                slelectedCell!!.text = "9"
            }
        }


    }
    fun getBoard(gMode: String,path:String) {


        val database = FirebaseDatabase.getInstance()
        database!!.getReference()
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("MainAct", "${p0.toException()}")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var puzzles = p0.child("${path}/${gMode}").value
                    val Borad2Array = Klaxon().parseArray<ArrayList<Int>>(puzzles.toString())
                    fillTheBoard(Borad2Array)
                }

            })
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
                        b.text = boardVal.toString()
                        b.setClickable(false)
                    }
                   j++
                }
                i++;
                j = 0;
            }
        }
    }


}
