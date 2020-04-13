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
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.Recycler)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val jokeList = mutableListOf<Joke>()
        val jokeService = JokeApiServiceFactory.createJokeApiService()
        val joke = jokeService.giveMeAJoke()
        val jokeAdapter = JokeAdapter(jokeList) {
            compositeDisposable
            if((recyclerView.computeVerticalScrollOffset()+recyclerView.computeHorizontalScrollExtent()) == it.itemCount-1)
                compositeDisposable
        }

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


        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = jokeAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}
