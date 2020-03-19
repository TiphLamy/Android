package com.example.chucknorris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var jokes = ListOfJokes.list
        //Log.i("logcat",list.toString())

        val recyclerView = findViewById<RecyclerView>(R.id.Recycler)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = JokeAdapter(jokes)

        fun setJokes(newJokes: List<String>) {
            jokes = newJokes
            recyclerView.adapter!!.notifyDataSetChanged()
        }


    }
}
