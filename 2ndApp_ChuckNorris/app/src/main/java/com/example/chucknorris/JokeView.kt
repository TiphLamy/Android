package com.example.chucknorris

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.joke_layout.view.*

class JokeView @JvmOverloads constructor(context: Context) :
    ConstraintLayout(context) {
    init {
        val layoutInflater = LayoutInflater.from(context)
        val jokeView = layoutInflater.inflate(R.layout.joke_layout,this,true)
    }

    data class Model(val jokeText: String)

    fun setupView(model: Model){
        jokes_textView.text = model.jokeText
    }

//    if() //Stared
//        imageView.setImageResource(R.drawable.favorite)
//    else
//        imageView.setImageresource(R.drawable.unfavorite)

}