package com.example.chucknorris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.SingleObserver
import io.reactivex.internal.operators.single.SingleInternalHelper.toObservable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
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

        val jokeService = JokeApiServiceFactory.createJokeApiService()
        val joke = jokeService.giveMeAJoke()
            .subscribeBy (
                onError = { error("couldn't print joke")},
                onSuccess = { TODO("What a joke  $jokeService")}
            )
    }
}
