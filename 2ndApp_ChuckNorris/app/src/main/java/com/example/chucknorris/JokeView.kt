package com.example.chucknorris

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.joke_layout.view.*

class JokeView constructor(context: Context) :
    ConstraintLayout(context) {

    var stared = false

    init {
        val layoutInflater = LayoutInflater.from(context)
        val jokeView = layoutInflater.inflate(R.layout.joke_layout,this,true)

    }

    data class Model(val joke: Joke,
                     val shareButton: (viewId: String) -> Unit = {})

    fun setupView(model: Model){
        jokes_textView.text = model.joke.value
        shareButton.setOnClickListener { model.shareButton(model.joke.id) }
    }

    fun staring(stared: Boolean){
        if(!stared)
            saveButton.setImageResource(R.drawable.unfavorite)
        else
            saveButton.setImageResource(R.drawable.favorite)
    }

}