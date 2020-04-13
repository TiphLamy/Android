package com.example.chucknorris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.*
import android.widget.Button
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
        val button = findViewById<Button>(R.id.button)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val jokeService = JokeApiServiceFactory.createJokeApiService()
        val joke = jokeService.giveMeAJoke()
        val jokeAdapter = JokeAdapter(mutableListOf())


        compositeDisposable.add(joke
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onError = { Log.e("TAG","couldn't print joke",it)},
                onSuccess = {jokeAdapter.setJokes(it)}
            )
        )


        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = jokeAdapter


        button.setOnClickListener{
            compositeDisposable.add(joke
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeat(10)
                .delay(1,TimeUnit.SECONDS,AndroidSchedulers.mainThread())
                .doOnSubscribe { progressBar.visibility = VISIBLE }
                .doOnTerminate { progressBar.visibility = GONE }
                .subscribeBy (
                    onError = { Log.e("TAG","couldn't print joke",it)},
                    onNext = {jokeAdapter.setJokes(it)}
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}
