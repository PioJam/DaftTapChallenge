package com.example.dafttapchallengeapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholde_highscore.view.*

class HighScoreAdapter: RecyclerView.Adapter<HighScoreViewHolder>() {

    companion object {
        var listOfStrings = HighScoreList.highScoreList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.viewholde_highscore,parent,false)
        return HighScoreViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return listOfStrings.size
    }

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        val strinngg = listOfStrings.get(position).name
        val score = listOfStrings.get(position).score
        val date = listOfStrings.get(position).date
        holder.view.nameInResults.text = strinngg
        holder.view.scoreInResults.text = score.toString()
        holder.view.dateInResults.text = date
    }
}