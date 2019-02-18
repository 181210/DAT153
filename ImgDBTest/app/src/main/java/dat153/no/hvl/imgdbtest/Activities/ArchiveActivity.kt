package dat153.no.hvl.imgdbtest.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dat153.no.hvl.imgdbtest.DBHelper.DBHelper
import dat153.no.hvl.imgdbtest.R
import kotlinx.android.synthetic.main.activity_archive.*

class ArchiveActivity : AppCompatActivity() {

    var dbHelper: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)

        dbHelper = DBHelper(this)


        btn_add_char_dbMenu.setOnClickListener {
            startActivity(Intent(this, SelectActivity::class.java))
        }

        if (dbHelper!!.getDBItemCount() > 0){
            btn_return_archive.isEnabled = true
            btn_view_db_menu.isEnabled = true
        } else {
            btn_return_archive.isEnabled = false
            btn_view_db_menu.isEnabled = false
        }

        btn_view_db_menu.setOnClickListener {
            startActivity(Intent(this, ShowActivity::class.java))
        }

        btn_return_archive.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }


    }
}
