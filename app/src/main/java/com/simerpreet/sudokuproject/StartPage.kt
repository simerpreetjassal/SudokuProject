package com.simerpreet.sudokuproject

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.loginBtn
import kotlinx.android.synthetic.main.activity_start_page.*

class StartPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)
        val myIntent= Intent(this,Login::class.java)
        val myGameIntent = Intent(this,GameOptions::class.java)
        loginBtn.setOnClickListener{
            startActivity(myIntent)
        }
        resetBtn.setOnClickListener{
            startActivity(myGameIntent)
        }
        newGameBtn.setOnClickListener{
            startActivity(myGameIntent)
        }
        highScoreBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.dialogText)
            builder.setMessage("Your High Score is 100")
            builder.setPositiveButton("Ok"){dialog, which ->
            }
            val alertDialog = builder.create()

            // Set other dialog properties
            alertDialog.show()
        }

    }
}
