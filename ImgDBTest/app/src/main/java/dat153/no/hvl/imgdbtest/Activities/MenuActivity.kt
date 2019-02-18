package dat153.no.hvl.imgdbtest.Activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dat153.no.hvl.imgdbtest.Model.Character
import dat153.no.hvl.imgdbtest.R
import kotlinx.android.synthetic.main.activity_menu.*
import dat153.no.hvl.imgdbtest.DBHelper.DBHelper
import kotlinx.android.synthetic.main.activity_splash.*

class MenuActivity : AppCompatActivity() {

    val REQUEST_CODE_GAME: Int = 1
    var userName: String? = null
    var lastScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)



        btn_db_menu.setOnClickListener {
            startActivity(Intent(this, ArchiveActivity::class.java))
        }

        var dbHelper = DBHelper(this)

        var list = dbHelper.readCharacterNames()

        var dataBack: SharedPreferences = getSharedPreferences(UserPrefActivity.MyShareVar.PREFS_NAME, 0)

        if (dataBack.contains("message")) {
            userName = dataBack.getString("message", "")
        } else {

        }

        btn_play_menu.setOnClickListener {
            var intent = Intent(this, GameActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_GAME)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lastScore = data!!.extras.getInt("score")
        Log.d("Last score", lastScore.toString())
        txt_score_main.text =  "Last score for $userName: " + lastScore

    }

}
