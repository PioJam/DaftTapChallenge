package com.example.dafttapchallengeapplication

object HighScoreList {
    var highScoreList = mutableListOf<HighScoreElement>()

    init {
        HighScoreList.highScoreList.add(HighScoreList.HighScoreElement("Konrad",150,5L,"2019/05/11 17:08:01"))
        HighScoreList.highScoreList.add(HighScoreList.HighScoreElement("Tomasz",150,5L,"2019/05/11 18:08:01"))
        HighScoreList.highScoreList.add(HighScoreList.HighScoreElement("CCC",30,5L,"2019/05/11 19:08:01"))
    }

    fun insertNewScoreAndDeleteLast(newScore : HighScoreElement){
        highScoreList.add(newScore)
        highScoreList.sortWith(compareBy({-1* it.score }, {-1* it.time }))
        if(highScoreList.size>5) {
            highScoreList.removeAt(highScoreList.size - 1)
        }
    }

    data class HighScoreElement(val name:String, val score:Int, val time:Long, val date:String)
}