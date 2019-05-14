package com.example.dafttapchallengeapplication

import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.dafttapchallengeapplication.GameAlertDialog.TypeOfAlertDialog.APPLY_SCORE_DIALOG
import com.example.dafttapchallengeapplication.GameAlertDialog.TypeOfAlertDialog.PLAY_AGAIN_DIALOG
import kotlinx.android.synthetic.main.activity_game.*
import java.text.SimpleDateFormat
import java.util.*

private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

class GameActivity : AppCompatActivity() {

    enum class TIMER_STATE{
        RUNNING, PAUSED
    }

    private val stringsList = listOf("3...","2...","1...","PLAY!")
    private var score: Int = 0
    private var time = 10000L
    private var timerState = TIMER_STATE.PAUSED
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        startTimer()
        clickSpace.setOnClickListener {
            score++
            scoreText.text = score.toString()
        }
        clickSpace.isClickable = false
    }

    private fun startTimer(){
        timerState = TIMER_STATE.RUNNING
        timer = object :CountDownTimer(time,1000){
            var i = 0
            override fun onFinish() {
                remainTimeCount.text = "0"
                if(score >= HighScoreList.highScoreList[HighScoreList.highScoreList.size-1].score||HighScoreList.highScoreList.size < 5){
                    openDialog(APPLY_SCORE_DIALOG)
                }else{
                    openDialog(PLAY_AGAIN_DIALOG)
                }
            }
            override fun onTick(timeLeft: Long) {
                time = timeLeft
                if(time>6000L&&i<stringsList.size){
                    countDown.text=stringsList[i]
                    i++
                    YoYo.with(Techniques.Bounce)
                        .duration(500)
                        .playOn(countDown)
                }
                if(time<6000L){
                    clickSpace.isClickable = true
                    countDown.visibility = View.INVISIBLE
                    remainTimeCount.text = (time/1000).toString()
                    YoYo.with(Techniques.ZoomIn)
                        .duration(500)
                        .playOn(remainTimeCount)
                }
            }
        }.start()
    }

    private fun openDialog(dialogVersion: GameAlertDialog.TypeOfAlertDialog) {
        val date = Calendar.getInstance().time
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
        val timeStamp = System.currentTimeMillis()
        val gameAlertDialog = GameAlertDialog(dialogVersion,score,timeStamp,dateInString)
        gameAlertDialog.show(supportFragmentManager,"gameAlertDialog")
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event != null) {
            if(event.action == KeyEvent.ACTION_DOWN){
                when (event.keyCode){
                    KeyEvent.KEYCODE_BACK -> return true
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
        timerState = TIMER_STATE.PAUSED
    }

    override fun onResume() {
        super.onResume()
        if (timerState==TIMER_STATE.PAUSED) {
            startTimer()
        }
    }
}
