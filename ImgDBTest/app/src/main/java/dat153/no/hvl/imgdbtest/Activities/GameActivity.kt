package dat153.no.hvl.imgdbtest.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dat153.no.hvl.imgdbtest.DBHelper.DBHelper
import dat153.no.hvl.imgdbtest.R

class GameActivity : AppCompatActivity() {

    var dbHelper : DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        dbHelper = DBHelper(this)
    }

    //
    fun startGameAct(){

    }

    fun game(){

    }

    fun randomChar(){

    }
}
