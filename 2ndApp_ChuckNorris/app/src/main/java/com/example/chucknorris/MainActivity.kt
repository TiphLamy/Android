package com.example.chucknorris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.json.Json


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun List<String>.string2Joke(): List<Joke>{
            val joke = Joke(categories = emptyList(),
                createdAt= "", iconUrl= "", id= "", updatedAt= "", url= "",
                value=this.toString())
            return listOf(joke)
        }
        
        var listOfJokes = ListOfJokes.list.string2Joke()

        val recyclerView = findViewById<RecyclerView>(R.id.Recycler)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = JokeAdapter(listOfJokes)

        fun setJokes(newJokes: List<Joke>) {
            listOfJokes = newJokes
            recyclerView.adapter!!.notifyDataSetChanged()
        }


    }
}
