package com.example.dafttapchallengeapplication

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment


class GameAlertDialog(
    private val dialogVersion: TypeOfAlertDialog,
    private val score: Int,
    private val timeStamp: Long,
    private val timeOfGame: String
) : AppCompatDialogFragment() {

    private val KEY_CONSTANT = "saveBoolean"
    private lateinit var editTextUsername: EditText

    enum class TypeOfAlertDialog {
        APPLY_SCORE_DIALOG, PLAY_AGAIN_DIALOG
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        when (dialogVersion) {

            TypeOfAlertDialog.APPLY_SCORE_DIALOG -> {

                val builder = AlertDialog.Builder(activity)
                val inflater = activity!!.layoutInflater
                val view = inflater.inflate(R.layout.layout_dialog, null)

                builder.setView(view)
                    .setTitle("Congratulations")
                    .setMessage("Your score $score is in top 5!")
                    .setPositiveButton("ok") { _, _ ->
                        val userName = editTextUsername.text.toString()
                        HighScoreList.insertNewScoreAndDeleteLast(
                            HighScoreList.HighScoreElement(userName, score, timeStamp, timeOfGame)
                        )
                        startActivity(Intent(activity, MainActivity::class.java)
                            .putExtra(KEY_CONSTANT, true)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                editTextUsername = view.findViewById(R.id.username)
                val alertDialog = builder.create()
                isCancelable = false
                alertDialog.setCanceledOnTouchOutside(false)
                return alertDialog
            }
            TypeOfAlertDialog.PLAY_AGAIN_DIALOG -> {

                val builder = AlertDialog.Builder(activity)

                builder.setTitle("Great Job!")
                    .setMessage("You got $score points")
                    .setPositiveButton("Play Again?") { _, _ ->
                        startActivity(Intent(activity, GameActivity::class.java)) }
                    .setNegativeButton("Back To Menu"){ _, _ ->
                        startActivity(Intent(activity, MainActivity::class.java)
                            .putExtra(KEY_CONSTANT, true)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                val alertDialog = builder.create()
                isCancelable = false
                alertDialog.setCanceledOnTouchOutside(false)
                return alertDialog
            }
            else -> {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Shouldn't happen")
                    .setMessage("Error")
                    .setPositiveButton(
                        "Back To Menu"
                    ) { _, _ -> startActivity(Intent(activity, MainActivity::class.java)) }
                return builder.create()
            }
        }
    }
}