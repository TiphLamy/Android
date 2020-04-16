package com.example.chucknorris

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.joke_layout.view.*

class JokeView constructor(context: Context) :
    ConstraintLayout(context) {

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.joke_layout,this,true)
    }

    data class Model(var stared: Boolean,
                     val joke: Joke,
                     val onShare: (jokeValue: String) -> Unit = {},
                     val onSave: (jokeValue:String) -> Unit = {})

    fun setupView(model: Model){
        jokes_textView.text = model.joke.value
        shareButton.setOnClickListener { model.onShare(model.joke.value) }
        saveButton.setOnClickListener {
                model.onSave(model.joke.value)
                model.stared = !model.stared
                staring(model.stared)
        }
    }

    private fun staring(stared: Boolean){
        if(!stared)
            saveButton.setImageResource(R.drawable.unfavorite)
        else
            saveButton.setImageResource(R.drawable.favorite)
    }

}