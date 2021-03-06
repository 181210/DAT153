package dat153.no.hvl.namequiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import java.util.concurrent.Delayed
private var names: ArrayList<String>? = null

class GameActivity : AppCompatActivity(), View.OnClickListener {

    var name: String = ""
    var fake1: String = ""
    var fake2: String = ""
    var img: Int = 0
    var isCorrect: Boolean = false

    override fun onClick(v: View?) {
        v as CheckBox
        var isChecked: Boolean = v.isChecked

        when(v.id){
            R.id.box_alt1 -> { if(isChecked) {
                box_alt2.isChecked = false
                box_alt3.isChecked = false
            }
            }
            R.id.box_alt2 -> { if(isChecked) {
                box_alt1.isChecked = false
                box_alt3.isChecked = false
            }
            }
            R.id.box_alt3 -> { if(isChecked) {
                box_alt1.isChecked = false
                box_alt2.isChecked = false
            }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        names = ArrayList()

        //Catch data from intent
        var data = intent.extras

        if(data != null){
            name = data.get("name").toString()
            fake1 = data.get("fake1").toString()
            fake2 = data.get("fake2").toString()
            img = data.getInt("img")

            img_game.setImageResource(img)

            //LOG ENTRIES
            Log.d("The name game activity", "$name")
            Log.d("The img game act: ", img.toString())



            //Randomize which checkbox that has the correct answer
            names!!.add(name)
            names!!.add(fake1)
            names!!.add(fake2)

            box_alt1.text = names!![randomNumber()]
            box_alt2.text = names!![randomNumber()]
            box_alt3.text = names!![randomNumber()]

            while (box_alt1.text == box_alt2.text){
                box_alt2.text = names!![randomNumber()]
            }

            while (box_alt3.text == box_alt2.text || box_alt3.text == box_alt1.text){
                box_alt3.text = names!![randomNumber()]
            }
        }

        /**
         * Adding the view to the buttons
         */
        box_alt1.setOnClickListener(this)
        box_alt2.setOnClickListener(this)
        box_alt3.setOnClickListener(this)
        btn_game_enter.setOnClickListener(this)


        btn_game_enter.setOnClickListener{
            var returnInt = this.intent

            if(box_alt1.isChecked && box_alt1.text == name){
                box_alt1.setBackgroundColor(Color.GREEN)
                txt_descr_game.text = "The force is strong!"
                isCorrect = true
            } else if (box_alt1.isChecked && box_alt1.text != name) {
                box_alt1.setBackgroundColor(Color.RED)
                txt_descr_game.text = "The force is weak.."
            }

            if(box_alt2.isChecked && box_alt2.text == name){
                box_alt2.setBackgroundColor(Color.GREEN)
                txt_descr_game.text = "The force is strong!"
                isCorrect = true
            } else if (box_alt2.isChecked && box_alt2.text != name) {
                box_alt2.setBackgroundColor(Color.RED)
                txt_descr_game.text = "The force is weak.."
            }

            if(box_alt3.isChecked && box_alt3.text == name){
                box_alt3.setBackgroundColor(Color.GREEN)
                txt_descr_game.text = "The force is strong!"
                isCorrect = true
            } else if (box_alt3.isChecked && box_alt3.text != name){
                box_alt3.setBackgroundColor(Color.RED)
                txt_descr_game.text = "The force is weak.."
            }

            returnInt.putExtra("correct", isCorrect)
            setResult(Activity.RESULT_OK, returnInt)
            finish()
        }

    }

    fun randomNumber() : Int{
        var random = Random()
        return random.nextInt(names!!.size)
    }
}
