package com.example.chucknorris

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(var listDeJoke: List<String>): RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    class JokeViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val textView = TextView(parent.context)
        return JokeViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return listDeJoke.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.textView.text = listDeJoke[position]
    }

    fun setJokes(newJokes: List<String>) {
        listDeJoke = newJokes
        notifyDataSetChanged()
    }

}