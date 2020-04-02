package com.simerpreet.sudokuproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_subm_solution_page.*

class subm_solution_page : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subm_solution_page)
        auth = FirebaseAuth.getInstance()
        displayMessage()


    }
    fun displayMessage(){
        var status = intent.getStringExtra("status").toString()
        val currentUser: FirebaseUser? = auth.getCurrentUser()
        if(currentUser!=null){
            val database = FirebaseDatabase.getInstance()
            var userId = currentUser.uid
            database!!.getReference()
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Log.e("MainAct","${p0.toException()}")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var highScore = p0.child("Users/${userId}/highScore").value.toString()
                        Log.d("DON2",highScore);
                        var myScore = highScore.toInt()
                        myScore+=100
                        database.getReference("Users/$userId/highScore").setValue(myScore)
                        Log.d("STTAS",status)
                        if(status.equals("true")) {
                            afterSubmitText.text =
                                "Game Saved. Your High Score updated. Thanks for playing"
                        }else{
                            afterSubmitText.text =
                                "Game Saved. Thanks for playing"
                        }
                        database.getReference("Users/$userId/lastPlay").setValue(intent.getStringExtra("lastPlay"))
                    }

                })

        }
        else{
            afterSubmitText.text = "Please Sign Up to Save The Game. The High Score "
        }
    }
}
