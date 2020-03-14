package com.simerpreet.sudokuproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val myIntent= Intent(this,MainActivity::class.java)

        registerLink.setOnClickListener{
            startActivity(myIntent)
        }
    }
}
