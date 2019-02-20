package dat153.no.hvl.imgdbtest.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import dat153.no.hvl.imgdbtest.Activities.UserPrefActivity.MyShareVar.PREFS_NAME
import dat153.no.hvl.imgdbtest.DBHelper.DBHelper
import dat153.no.hvl.imgdbtest.Model.Character
import dat153.no.hvl.imgdbtest.R
import dat153.no.hvl.imgdbtest.Utils.Utils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    var dbHelper : DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        dbHelper = DBHelper(this)

        var dataBack : SharedPreferences = getSharedPreferences(PREFS_NAME, 0)

        var char = Character()

        var name = "Default character"
        char.name = name

        val icon = BitmapFactory.decodeResource(
            this.getResources(),
            android.R.drawable.ic_menu_compass
        )

        var byteArray = Utils.getBytes(icon)
        dbHelper!!.addBitmap(name, byteArray )

        if(dataBack.contains("message")){
            var userName = dataBack.getString("message", "")
            txt_splash.text = "Welcome back $userName!"

            if (dbHelper!!.getDBItemCount() > 0){
                Handler().postDelayed({
                    startActivity(Intent(this@SplashActivity, MenuActivity::class.java))
                    Toast.makeText(this, "Ready to play", Toast.LENGTH_SHORT).show()
                    finish()
                }, 5000)
            } else {
                Handler().postDelayed({
                    startActivity(Intent(this@SplashActivity, ArchiveActivity::class.java))
                    Toast.makeText(this, "Empty archive, add characters to play!", Toast.LENGTH_SHORT).show()
                    finish()
                }, 5000)
            }

        } else {
            Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, UserPrefActivity::class.java))
                Toast.makeText(this, "New player detected", Toast.LENGTH_SHORT).show()
                finish()
            }, 5000)
        }
    }
}
