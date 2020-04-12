package com.example.chucknorris

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(var listDeJoke: MutableList<Joke>): RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    class JokeViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val textView = layoutInflater.inflate(R.layout.joke_layout,parent,false) as TextView
        return JokeViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return listDeJoke.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.textView.text = listDeJoke[position].value
    }

    fun setJokes(newJokes: Joke) {
        listDeJoke.add(newJokes)
        notifyDataSetChanged()
    }

}