package com.simerpreet.sudokuproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_game_options.*

class GameOptions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_options)
        val gameBoard= Intent(this,TheGameBoard::class.java)
        var userName1 = intent.getStringExtra("USER_NAME")
        if(userName1!=null){
            userName.text=("Player Name: ${userName1}")
            gameBoard.putExtra("USER_NAME",userName1)
        }

        if(intent.getStringExtra("LGO")!=null) {
            logout.setText("Logout")
            logout.visibility = View.VISIBLE
            gameBoard.putExtra("LGO","Logout")
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
            Toast.makeText(this,"Logout Successfully", Toast.LENGTH_SHORT)
            logout.visibility = View.INVISIBLE

        }
    }
}
