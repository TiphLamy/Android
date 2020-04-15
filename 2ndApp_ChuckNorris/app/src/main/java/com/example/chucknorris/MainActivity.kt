package com.example.chucknorris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.*
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val jokeList = mutableListOf<Joke>()
    private val jokeService = JokeApiServiceFactory.createJokeApiService()
    private val joke = jokeService.giveMeAJoke()
    private lateinit var jokeAdapter: JokeAdapter

    private var jokeState = "jokeList save"
    private lateinit var jokeCurrentSave: KSerializer<List<Joke>>

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.Recycler)
        progressBar = findViewById(R.id.progressBar)


        jokeAdapter = JokeAdapter {
            showJoke()
        }

        jokeCurrentSave = Joke.serializer().list

        if(savedInstanceState == null) {
            jokeAdapter.onBottomReached()
        }
        else {
            savedInstanceState.getString(jokeState)?.let {
                Json.parse(jokeCurrentSave, it)
            }?.let {
                jokeList.addAll(it)
            }
            jokeAdapter.setJokes(jokeList)
            // equivalent to:
            //  jokeList.addAll( Json.parse(jokeCurrentSave, savedInstanceState.getString(jokeState))) if none are null
        }

        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = jokeAdapter

    }

    private fun showJoke(){
        compositeDisposable.add(joke
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .repeat(10)
            .delay(1,TimeUnit.SECONDS,AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = VISIBLE }
            .doOnTerminate { progressBar.visibility = GONE }
            .subscribeBy (
                onError = { Log.e("TAG","couldn't print joke",it)},
                onNext = { newJoke: Joke -> jokeList.add(newJoke)},
                onComplete = { jokeAdapter.setJokes(jokeList)}
            )
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(jokeState,
                            Json.stringify(jokeCurrentSave,jokeList))
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}
