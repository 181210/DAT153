package dat153.no.hvl.myfirstapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class DisplayMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        // Get the Intent that started this activity and extract the String

        val message = intent.getStringExtra(EXTRA_MESSAGE)

        // Capture the layout's TextView and set the String as its text

        val textView = findViewById<TextView>(R.id.textView).apply {
            text = message
        }
    }
}