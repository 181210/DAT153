package dat153.no.hvl.imgdbtest.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import dat153.no.hvl.imgdbtest.DBHelper.DBHelper
import dat153.no.hvl.imgdbtest.Model.Character
import dat153.no.hvl.imgdbtest.R
import dat153.no.hvl.imgdbtest.Utils.Utils
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : AppCompatActivity() {

    var dbHelper : DBHelper? = null
    var counter: Int = 0
    var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        dbHelper = DBHelper(this)

        game()

    }


    fun game(){

        var char = randomChar()

        if (dbHelper!!.getBitmapByName(char.name.toString()) != null) {
            val bitmap = Utils.getImage(dbHelper!!.getBitmapByName(char.name.toString())!!)
            img_game.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "Error: Couldn't load image", Toast.LENGTH_SHORT).show()
        }

        btn_enter_game.setOnClickListener {
            var answer = txt_guess_game.text.toString()
            counter += 1
            if (answer.toLowerCase() == char.name!!.toLowerCase()) {
//                btn_enter_game.setBackgroundColor(Color.GREEN)
                score += 1
            }
//            } else {
//                btn_enter_game.setBackgroundColor(Color.RED)
//            }

            Log.d("Score", "$score")


            txt_score.text = "Score: $score correct of $counter tries!"

            txt_guess_game.text.clear()

            game()
        }

        btn_return_game.setOnClickListener {
            var Returnintent: Intent = this.intent
            Returnintent.putExtra("score", score)
            Log.d("Score from return Int", "$score")
            setResult(Activity.RESULT_OK, Returnintent)
            finish()
        }

    }

    fun randomChar(): Character{
        var list = dbHelper!!.readCharacterNames()
        var random = Random()

        return list[random.nextInt(list.size)]

    }
}
