package com.example.dafttapchallengeapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView_main)}
    private var saving : Boolean = false
    private val KEY_CONSTANT = "saveBoolean"
    private val SHARED_PREF = "shared preferences"
    private val JSON_KEY = "scoreList"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saving = intent.getBooleanExtra(KEY_CONSTANT,false)
        if(saving){
            saveData()
            saving = false
        }else { loadData() }
        buildRecyclerView()

        playButton.setOnClickListener {
            startActivity(Intent(this,GameActivity::class.java))
        }
    }
    private fun saveData() {
        val sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(HighScoreList.highScoreList)
        editor.putString(JSON_KEY, json)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(JSON_KEY, null) ?: return
        val type = object : TypeToken<ArrayList<HighScoreList.HighScoreElement>>() {}.type
        HighScoreList.highScoreList = gson.fromJson(json, type)
    }

    private fun buildRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = HighScoreAdapter()
    }
}
