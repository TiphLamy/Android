package com.example.chucknorris

import android.content.Context
import android.content.Intent
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
    private var jokeCurrentFavorite = Joke.serializer().list
    private val jokesSaved = mutableListOf<Joke>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.Recycler)
        progressBar = findViewById(R.id.progressBar)

        jokeCurrentSave = Joke.serializer().list

        jokeAdapter = JokeAdapter(
            {showJoke()},
            { jokeValue: String -> share(jokeValue)},
            { jokeValue: Joke, stared: Boolean -> save(jokeValue, stared) }
        )

        if(savedInstanceState == null)
            jokeAdapter.onBottomReached()
        else {
            savedInstanceState.getString(jokeState)?.let {
                Json.parse(jokeCurrentSave, it)
            }?.let {
                jokeList.addAll(it)
            }
            jokeAdapter.setJokes(jokeList)
            jokeList.clear()
        }

        val sharedPreferences = getSharedPreferences("Saved Jokes", Context.MODE_PRIVATE)
        val savedJoke = sharedPreferences.getString("savedJoke","")
        if(sharedPreferences.contains("savedJoke")){
            savedJoke?.let {
                Json.parse(jokeCurrentFavorite, it)
            }?.let {
                jokesSaved.addAll(it)
            }
            jokeList.addAll(jokesSaved)
        }

        val jokeTouch = JokeTouchHelper(
            {position: Int -> jokeAdapter.onJokeRemoved(position)},
            {oldPosition: Int, target: Int -> jokeAdapter.onItemMoved(oldPosition,target)}
        )
        jokeTouch.attachToRecyclerView(recyclerView)


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
                onComplete = {
                    jokeAdapter.setJokes(jokeList)
                }
            )
        )
        jokeList.clear()
    }

    private fun share(jokeValue: String){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, jokeValue)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun save(jokeValue: Joke, stared: Boolean){
        val sharedPreferences = getSharedPreferences("Saved Jokes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if(stared) {
            if(!jokesSaved.contains(jokeValue))
                jokesSaved.add(jokeValue)
            else
                jokeList.remove(jokeValue)
        }
        else {
            jokesSaved.remove(jokeValue)
        }
        editor.putString("savedJoke",Json.stringify(jokeCurrentFavorite, jokesSaved))
        editor.apply()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(
            jokeState,
            Json.stringify(jokeCurrentSave,jokeAdapter.listDeJoke)
        )
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}
