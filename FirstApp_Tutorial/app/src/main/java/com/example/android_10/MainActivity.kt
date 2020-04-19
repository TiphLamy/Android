package com.example.android_10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val liste = listOf("Mélissa","Valentin","Jules","Alan","Léa").sortedBy { it.length }
        //Log.d("logcat",liste.toString())

        button_tryme.setOnClickListener {
            val randomName = liste[Random.nextInt(5)]
            hello_esiee.text = randomName
        }




    }
}
