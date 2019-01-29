package dat153.no.hvl.namequiz

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), View.OnClickListener {


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

        var data = intent.extras

        if(data != null){
            var name: String = data.get("name").toString()
            var img = data.getInt("img")

            Log.d("The name game activity", "$name")
            Log.d("The img game act: ", img.toString())

            img_game.setImageResource(img)
            box_alt1.text = name.toString()


        }

        /**
         * Adding the view to the buttons
         */
        box_alt1.setOnClickListener(this)
        box_alt2.setOnClickListener(this)
        box_alt3.setOnClickListener(this)
        btn_game_enter.setOnClickListener(this)


        btn_game_enter.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
