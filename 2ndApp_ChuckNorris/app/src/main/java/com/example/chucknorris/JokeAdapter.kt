package com.example.chucknorris

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.joke_layout.view.*

class JokeAdapter(val onBottomReached: () -> Unit = {}): RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    private val listDeJoke = mutableListOf<Joke>()

    private fun setShareListener(viewId: String) {
        Log.i("TAG",viewId)
    }

    private fun setFavoriteListener(viewId: String, view: View){
        Log.i("TAG",viewId)

    }

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
        holder.jokeView.shareButton.setOnClickListener{ setShareListener(listDeJoke[position].id) }
        holder.jokeView.saveButton.setOnClickListener { setFavoriteListener(listDeJoke[position].id, holder.jokeView) }
        if(position == itemCount-1)
            onBottomReached()
    }

    fun setJokes(newJokes: MutableList<Joke>) {
        listDeJoke.clear()
        listDeJoke.addAll(newJokes)
        notifyDataSetChanged()
    }

}