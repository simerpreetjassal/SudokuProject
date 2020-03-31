package com.simerpreet.sudokuproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val myIntent= Intent(this,Login::class.java)

        loginLink.setOnClickListener{
            startActivity(myIntent)
        }
        registerBtn.setOnClickListener{
            validateTheform()
        }
    }
    fun validateTheform(){

        if(username.text.toString().isEmpty()){
            username.error = "Please enter user name";
            username.requestFocus()
            return
        }
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

        auth.createUserWithEmailAndPassword(myEmail.text.toString(), myPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var user = auth.currentUser;
                    var userId = user!!.uid
                    val database = FirebaseDatabase.getInstance()
                    var myRef = database.getReference("Users/$userId/email")
                    myRef.setValue(myEmail.text.toString())
                    myRef = database.getReference("Users/$userId/username")
                    myRef.setValue(username.text.toString())
                    val gameOptionIntent= Intent(this,GameOptions::class.java)
                    gameOptionIntent.putExtra("USER_NAME", username.text.toString());
                    startActivity(gameOptionIntent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("done", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}
