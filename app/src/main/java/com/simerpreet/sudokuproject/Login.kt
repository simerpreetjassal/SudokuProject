package com.simerpreet.sudokuproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val myIntent = Intent(this, MainActivity::class.java)
        val gameOptionIntent = Intent(this, GameOptions::class.java)

        registerLink.setOnClickListener {
            startActivity(myIntent)
        }
        loginBtn.setOnClickListener {
            startActivity(gameOptionIntent)
        }
    }
}
