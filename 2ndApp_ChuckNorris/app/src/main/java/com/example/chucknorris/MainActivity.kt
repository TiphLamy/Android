package com.example.chucknorris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


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

        val jokeService = JokeApiServiceFactory.createJokeApiService()
        val joke = jokeService.giveMeAJoke()
            .subscribeOn(Schedulers.io())
            .subscribeBy (
                onError = { error("couldn't print joke")},
                onSuccess = { joke: Joke -> TODO("What a joke  $joke")}
            )
        //Log.i("logcat",joke.toString())
    }
}
