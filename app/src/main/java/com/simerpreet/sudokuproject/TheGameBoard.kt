package com.simerpreet.sudokuproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.beust.klaxon.Klaxon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
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
        val myIntent = Intent(this, subm_solution_page::class.java)
        var bool = intent.getBooleanExtra("pullLastOne",false)
        getBoard(gameLevel!!,"SudokuPuzzles",bool)
        submitSln.setOnClickListener{
            if(checkTheBoard()) {
                myIntent.putExtra("status","true")
                myIntent.putExtra("lastPlay",saveBeforeClose())
                startActivity(myIntent)
            }
        }
        exit.setOnClickListener{
            myIntent.putExtra("status","false")
            myIntent.putExtra("lastPlay",saveBeforeClose())
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
    fun getBoard(gMode: String,path:String,lastPlay:Boolean=false) {
        val database = FirebaseDatabase.getInstance()
        database!!.getReference()
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("MainAct", "${p0.toException()}")
                }
                var puzzles : Any? = null
                override fun onDataChange(p0: DataSnapshot) {
                    if(!lastPlay)
                    {
                        puzzles = p0.child("${path}/${gMode}").value
                    }else{
                        val myUid = intent.getStringExtra("UID").toString()
                        puzzles = p0.child("Users/${myUid}/lastPlay").value
                    }
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

    fun checkTheBoard(): Boolean{
        var bool = false
        if(checkAllRow()){
            if(checkAllColumn()){
                if(checkTheSqures()){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Victory")
                    builder.setMessage("You won. Score 100")
                    builder.setPositiveButton("Ok"){dialog, which ->
                    }
                    val alertDialog = builder.create()
                    alertDialog.show()
                    bool = true

                }
            }
        }
        return bool
    }
    fun checkAllRow(): Boolean{
        var resultRowChecker = true
        var myButton: Button? = null
        var enteries = mutableSetOf("0")
        outer@ for(i in 0..8){
            inner@for(j in 0..8){
                val resID = resources.getIdentifier("r${i}c${j}", "id", packageName)
                myButton  = findViewById<Button>(resID)
                var text = myButton.text.toString()
                if(!text.isEmpty()){
                    var result = enteries.add(text)
                    if(result == false){
                        myButton.background = ContextCompat.getDrawable(applicationContext,R.drawable.error)
                        errorBox(i+1,j+1)
                        resultRowChecker = false
                        break@outer
                    }

                }else{
                    myButton.background = ContextCompat.getDrawable(applicationContext,R.drawable.error)
                    errorBox(i+1,j+1)
                    resultRowChecker = false
                    break@outer
                }
            }
            enteries = mutableSetOf("0")
        }
        return resultRowChecker

    }
    fun checkAllColumn(): Boolean{
        var resultColChecker = true
        var myButton: Button? = null
        var enteries = mutableSetOf("0")
        outer@ for(i in 0..8){
            inner@for(j in 0..8){
                val resID = resources.getIdentifier("r${j}c${i}", "id", packageName)
                myButton  = findViewById<Button>(resID)
                var text = myButton.text.toString()
                if(!text.isEmpty()){
                    var result = enteries.add(text)
                    if(result == false){
                        myButton.background = ContextCompat.getDrawable(applicationContext,R.drawable.error)
                        errorBox(j+1,i+1)
                        resultColChecker = false
                        break@outer
                    }

                }else{
                    myButton.background = ContextCompat.getDrawable(applicationContext,R.drawable.error)
                    errorBox(i+1,j+1)
                    resultColChecker = false
                    break@outer
                }
            }
            enteries = mutableSetOf("0")
        }
        return resultColChecker

    }

    fun checkTheSqures():Boolean{
        var resultSqureChecker = true
        var myButton: Button? = null
        outer@ for( myRow in  0 until 9 step 3) {
            for (myCol in 0 until 9 step 3) {
                var enteries = mutableSetOf("0")
                for (row in myRow until (myRow + 3)) {
                    for (col in myCol until (myCol+3)) {
                        val resID = resources.getIdentifier("r${row}c${col}", "id", packageName)
                        myButton  = findViewById<Button>(resID)
                        var text = myButton.text.toString()
                        if(!text.isEmpty()){
                            var result = enteries.add(text)
                            if(result == false){
                                myButton.background = ContextCompat.getDrawable(applicationContext,R.drawable.error)
                                errorBox(row+1,col+1)
                                resultSqureChecker = false
                                break@outer
                            }

                        }else{
                            myButton.background = ContextCompat.getDrawable(applicationContext,R.drawable.error)
                            errorBox(row+1,col+1)
                            resultSqureChecker = false
                            break@outer
                        }
                    }
                }
            }
        }
        return resultSqureChecker
    }

    fun errorBox(row: Int, col:Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialogText)
        builder.setMessage("ROW $row COLUMN $col is wrong")
        builder.setPositiveButton("Ok"){dialog, which ->
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
    fun saveBeforeClose():String{
        var bigArray = arrayOf<Array<Int>>()
        for(i in 0..8){
            var lilarray = arrayOf<Int>()
            for(j in 0..8){
                val resID = resources.getIdentifier("r${i}c${j}", "id", packageName)
                val b = findViewById<Button>(resID)
                if(b.text.isEmpty()){
                    lilarray+=0
                }else {
                    lilarray += b.text.toString().toInt()
                }
            }
            bigArray+=lilarray
        }
        var gson = Gson()
        return gson.toJson(bigArray)
    }


}
