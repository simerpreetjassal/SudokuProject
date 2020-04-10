package com.simerpreet.sudokuproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_game_options.*

class GameOptions : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_options)
        val gameBoard= Intent(this,TheGameBoard::class.java)
        auth = FirebaseAuth.getInstance()
        val userSignedIn = checkSigIn()
        var userName1 = intent.getStringExtra("USER_NAME")
        if(userSignedIn){
            if(userName1!=null) {
                playerName.text = ("Player Name: ${userName1}")
                gameBoard.putExtra("USER_NAME", userName1)
            }
        }

        if(userSignedIn) {
            if (intent.getStringExtra("LGO") != null) {
                logout.setText("Logout")
                logout.visibility = View.VISIBLE
                gameBoard.putExtra("LGO", "Logout")
            }
        }
        easyBtn.setOnClickListener{
            gameBoard.putExtra("gameMode","easy")
            startActivity(gameBoard)
        }
        mediumBtn.setOnClickListener{
            gameBoard.putExtra("gameMode","medium")
            startActivity(gameBoard)
        }
        hardbtn.setOnClickListener{
            gameBoard.putExtra("gameMode","hard")
            startActivity(gameBoard)
        }
        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this,"Logout Successfully", Toast.LENGTH_SHORT).show()
            logout.visibility = View.INVISIBLE
            playerName.setText("Player Name : UnKnown")

        }
    }
    fun checkSigIn():Boolean{
        val currentUser: FirebaseUser? = auth.getCurrentUser()
        return currentUser!=null
    }

}
