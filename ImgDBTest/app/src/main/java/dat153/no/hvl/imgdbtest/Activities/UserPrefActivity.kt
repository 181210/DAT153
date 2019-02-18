package dat153.no.hvl.imgdbtest.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import dat153.no.hvl.imgdbtest.Activities.UserPrefActivity.MyShareVar.PREFS_NAME
import dat153.no.hvl.imgdbtest.R
import kotlinx.android.synthetic.main.activity_user_pref.*

class UserPrefActivity : AppCompatActivity() {

    object MyShareVar {
        val PREFS_NAME = "myPrefs"
    }


    var myPref : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_pref)

        btn_userpref.setOnClickListener {

            myPref = getSharedPreferences(PREFS_NAME, 0)

            var editor : SharedPreferences.Editor = myPref!!.edit()

            //Checks if text input is empty
            if (!TextUtils.isEmpty(txt_username_sharedpref.text.toString())){
                var message = txt_username_sharedpref.text.toString()

                editor.putString("message", message)

                editor.commit()

                startActivity(Intent(this, ArchiveActivity::class.java))

            } else {
                Toast.makeText(this, "Please enter a username!", Toast.LENGTH_LONG).show()
            }

        }

    }
}
