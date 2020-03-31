package com.simerpreet.sudokuproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.myEmail
import kotlinx.android.synthetic.main.activity_login.myPassword
import kotlinx.android.synthetic.main.activity_main.*


class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        val myIntent = Intent(this, MainActivity::class.java)


        registerLink.setOnClickListener {
            startActivity(myIntent)
        }
        loginBtn.setOnClickListener {
            sigInTheUser()

        }

    }

    fun sigInTheUser(){
        if (!Patterns.EMAIL_ADDRESS.matcher(myEmail.text.toString()).matches()){
            myEmail.error="Please enter valid email"
            myEmail.requestFocus()
            return
        }
        if(myPassword.text.toString().isEmpty()){
            myPassword.error = "Please enter password"
            myPassword.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(myEmail.text.toString(), myPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val gameOptionIntent = Intent(this, GameOptions::class.java)
                    var userId = auth.currentUser?.uid;
                    var userName = "Unknown"
                    val database = FirebaseDatabase.getInstance()
                    database!!.getReference()
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {
                                Log.e("MainAct","${p0.toException()}")
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                userName = p0.child("Users/${userId}/username").value.toString()
                                gameOptionIntent.putExtra("USER_NAME", userName);
                                startActivity(gameOptionIntent)
                            }

                        })


                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

            }
    }
}
