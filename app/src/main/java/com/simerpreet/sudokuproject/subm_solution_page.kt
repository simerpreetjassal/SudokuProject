package com.simerpreet.sudokuproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_subm_solution_page.*

class subm_solution_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subm_solution_page)

        afterSubmitText.setText("We are prcoessing your solution. App is under construction please wait.")
    }
}
