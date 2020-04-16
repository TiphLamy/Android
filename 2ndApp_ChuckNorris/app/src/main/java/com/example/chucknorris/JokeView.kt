package com.example.chucknorris

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.joke_layout.view.*

class JokeView @JvmOverloads constructor(context: Context) :
    ConstraintLayout(context) {

    var stared = false

    init {
        val layoutInflater = LayoutInflater.from(context)
        val jokeView = layoutInflater.inflate(R.layout.joke_layout,this,true)

    }

    data class Model(val jokeText: String)

    fun setupView(model: Model){
        jokes_textView.text = model.jokeText
    }

    fun staring(stared: Boolean){
        if(!stared)
            saveButton.setImageResource(R.drawable.unfavorite)
        else
            saveButton.setImageResource(R.drawable.favorite)
    }

}