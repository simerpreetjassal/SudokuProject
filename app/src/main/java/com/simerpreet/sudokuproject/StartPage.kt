package com.simerpreet.sudokuproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.loginBtn
import kotlinx.android.synthetic.main.activity_start_page.*


class StartPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)
        auth = FirebaseAuth.getInstance()
        val myIntent= Intent(this,Login::class.java)
        val myGameIntent = Intent(this,GameOptions::class.java)
        checkUserAlreadySignedIn(myGameIntent)
        loginBtn.setOnClickListener{
            startActivity(myIntent)
        }
        resetBtn.setOnClickListener{
            startActivity(myGameIntent)
        }
        newGameBtn.setOnClickListener{
            startActivity(myGameIntent)
        }
        continueGameBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Continue")
            builder.setMessage("This is under construction")
            builder.setPositiveButton("Ok"){dialog, which ->
            }
            val alertDialog = builder.create()

            // Set other dialog properties
            alertDialog.show()
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
        logout.setOnClickListener{
             FirebaseAuth.getInstance().signOut()
            Toast.makeText(this,"Logout Successfully",Toast.LENGTH_SHORT)
            logout.visibility = View.INVISIBLE

        }



    }

    fun checkUserAlreadySignedIn(myGameIntent: Intent){
        val currentUser: FirebaseUser? = auth.getCurrentUser()
        Log.d("USERDON",currentUser.toString())
        if(currentUser!=null){
           var userId = currentUser.uid
            val database = FirebaseDatabase.getInstance()
            var userName = "Unknown"
            database!!.getReference()
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Log.e("MainAct","${p0.toException()}")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        userName = p0.child("Users/${userId}/username").value.toString()
                        playerName.setText("Player Name: $userName")
                        logout.setText("Logout")
                        logout.visibility = View.VISIBLE
                        myGameIntent.putExtra("USER_NAME",userName)
                        myGameIntent.putExtra("LGO","Logout")
                    }

                })
        }

    }
}
