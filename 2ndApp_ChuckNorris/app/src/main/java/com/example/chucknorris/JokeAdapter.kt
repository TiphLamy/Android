package com.example.chucknorris

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter: RecyclerView.Adapter<JokeAdapter.JokeViewHolder> {
    var list = listOf("Chuck Norris wasn't born in a hospital. He made himself",
        "Hilter died the day after Chuck Norris was born. Coincidence... i think not.",
        "Bears have to put up 'Don't feed Chuck Norris' signs.",
        "While catfishing, Chuck Norris uses his own toe jam for stink bait.",
        "Chuck Norris recently won the World Horseshoe Pitching Tournament while they were still attached to a Clydesdale.",
        "Chuck Norris likes to scare the shit out of Saint Peter by killing people so hard they smash into the pearly gates.",
        "the only reason Mexican cartel doesn't come to united states is bacause they're afraid of Chuck Norris.",
        "Chuck Norris is the only person who can gang up on you.",
        "Physicists have replaced the term Big Bang with Chuck Norris.",
        "Chuck Norris knows exactly how many licks it takes to reach the center of a tootsipop.")

    class JokeViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}