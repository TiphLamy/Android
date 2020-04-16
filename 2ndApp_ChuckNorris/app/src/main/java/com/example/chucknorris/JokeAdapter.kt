package com.example.chucknorris

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(val onBottomReached: () -> Unit = {},
                  private val onShare: (jokeValue: String) -> Unit = {},
                  private val onSave: (jokeValue: String) -> Unit = {}
): RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    val listDeJoke = mutableListOf<Joke>()

    class JokeViewHolder(val jokeView: JokeView): RecyclerView.ViewHolder(jokeView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val jokeView = JokeView(parent.context)
        return JokeViewHolder(jokeView)
    }

    override fun getItemCount(): Int {
        return listDeJoke.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.jokeView.setupView(
            JokeView.Model(
                false,
                listDeJoke[position],
                onShare,
                onSave
            )
        )
        if(position == itemCount-1)
            onBottomReached()
    }

    fun setJokes(newJokes: MutableList<Joke>) {
        listDeJoke.addAll(newJokes)
        notifyDataSetChanged()
    }

    fun onItemMoved(oldPosition: Int, target: Int) {
        val joke = listDeJoke[oldPosition]
        listDeJoke.removeAt(oldPosition)
        listDeJoke.add(target,joke)
        notifyItemMoved(oldPosition,target)
    }

    fun onJokeRemoved(position: Int) {
        listDeJoke.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,itemCount)
    }

}