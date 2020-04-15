package com.example.chucknorris

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(val onBottomReached: () -> Unit = {}): RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    private val listDeJoke = mutableListOf<Joke>()

    class JokeViewHolder(val jokeView: JokeView): RecyclerView.ViewHolder(jokeView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val jokeView = JokeView(parent.context)
        return JokeViewHolder(jokeView)
    }

    override fun getItemCount(): Int {
        return listDeJoke.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.jokeView.setupView(JokeView.Model(listDeJoke[position].value))
        if(position == itemCount-1)
            onBottomReached()
    }

    fun setJokes(newJokes: MutableList<Joke>) {
        listDeJoke.clear()
        listDeJoke.addAll(newJokes)
        notifyDataSetChanged()
    }

}